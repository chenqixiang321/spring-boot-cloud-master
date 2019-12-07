package com.opay.invite.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.opay.invite.model.*;
import com.opay.invite.model.request.WithdrawalApproval;
import com.opay.invite.model.request.WithdrawalListRequest;
import com.opay.invite.resp.CodeMsg;
import com.opay.invite.resp.Result;
import com.opay.invite.service.InviteOperateService;
import com.opay.invite.service.InviteService;
import com.opay.invite.service.RpcService;
import com.opay.invite.service.WithdrawalService;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.transferconfig.TransferConfig;
import com.opay.invite.utils.DateFormatter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mockito.cglib.beans.BeanMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@Api(value = "填入邀请码API")
public class ApiController {

    @Autowired
    private InviteService inviteService;

    @Autowired
    private InviteOperateService inviteOperateService;

    @Value("${invite.code:''}")
    private String key;

    @Autowired
    private RpcService rpcService;

    @Autowired
    private TransferConfig transferConfig;

    @Value("${spring.jackson.time-zone:''}")
    private String zone;

    @Autowired
    private RewardConfig rewardConfig;

    @Autowired
    private WithdrawalService withdrawalService;

    @ApiOperation(value = "用户填入邀请码回调", notes = "用户填入邀请码回调")
    @PostMapping("/notifyInvite")
    public Result notifyInvite(HttpServletRequest request, @RequestBody NotifyInvite notifyInvite) throws Exception {
        if(notifyInvite.getOpayId()==null || "".equals(notifyInvite.getOpayId())){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(notifyInvite.getCreateTime()==null || "".equals(notifyInvite.getCreateTime())){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(notifyInvite.getPhone()==null || "".equals(notifyInvite.getPhone())){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(notifyInvite.getInviteCode()==null || "".equals(notifyInvite.getInviteCode())){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(notifyInvite.getSign()==null || "".equals(notifyInvite.getSign())){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        boolean f = inviteOperateService.checkTime(zone);
        if(f){
            log.warn("活动未开始或已结束,notifyInvite info{},"+JSON.toJSONString(notifyInvite));
            return Result.success();
        }
        try {
            Date regDate = DateFormatter.parseDate(notifyInvite.getCreateTime());
            Date date = DateFormatter.getDateAfter(regDate,rewardConfig.getEffectiveDay());
            Date nDate =  new Date();
            String ntime = DateFormatter.formatDatetimeByZone(nDate,zone);
            Date formatDate = DateFormatter.parseDatetime(ntime);
            if(date.getTime()<formatDate.getTime()){
                return Result.error(CodeMsg.ILLEGAL_CODE_DAY);
            }
        }catch (Exception e){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        Map<String,Object> map = beanToMap(notifyInvite);
        String beforeStr = getBeforeStr(map)+"&key="+key;
        String afterMd5Str = DigestUtils.md5DigestAsHex(beforeStr.getBytes());
        if (!afterMd5Str.equalsIgnoreCase(notifyInvite.getSign())){
            return Result.error(CodeMsg.ILLEGAL_CODE_SIGN);
        }

        //判断邀请码是否合法,和反作弊，不能建立师徒关系,当前用户已经过了7天不能填写邀请码
        OpayInviteCode inviteCode = inviteService.getOpayIdByInviteCode(notifyInvite.getInviteCode());
        //获取code用户类型
        if(inviteCode==null){
            return Result.error(CodeMsg.ILLEGAL_CODE);
        }
        //解析登录用户ID和邀请码用户ID
        String masterId = inviteCode.getOpayId();
        if(masterId.equals(notifyInvite.getOpayId())){
            return Result.error(CodeMsg.ILLEGAL_CODE);
        }
        //当前用户是代理不能存在师傅

        //校验用户是否已经建立关系，或是不能互相邀请关系
        int count = inviteService.checkRelation(masterId,notifyInvite.getOpayId());
        if(count>0){
            return Result.error(CodeMsg.ILLEGAL_CODE_RELATION);
        }

        //建立钱包
        List<OpayActiveCashback> cashbacklist = new ArrayList<>();
        OpayActiveCashback mastercashback = inviteService.getActivityCashbackByOpayId(masterId);//查询师傅存在钱包
        if(mastercashback ==null){
            inviteService.saveCashback(masterId,inviteCode.getPhone(),new Date());
            mastercashback = new OpayActiveCashback();
            mastercashback.setOpayId(masterId);
            mastercashback.setVersion(0);
        }
        cashbacklist.add(mastercashback);
        OpayActiveCashback pupilcashback = inviteService.getActivityCashbackByOpayId(notifyInvite.getOpayId());//查询徒弟存在钱包
        if(pupilcashback ==null){
            inviteService.saveCashback(notifyInvite.getOpayId(),notifyInvite.getPhone(),new Date());
            pupilcashback = new OpayActiveCashback();
            pupilcashback.setOpayId(notifyInvite.getOpayId());
            pupilcashback.setVersion(0);
        }
        cashbacklist.add(pupilcashback);
        // 建立关系，增加金额奖励,同时充入账户，需要判断角色和用户邀请人数所属阶梯，奖励不同
        OpayInviteRelation vr = inviteService.selectRelationMasterByMasterId(masterId);
        long mlis = System.currentTimeMillis();
        //TODO 查询邀请账号，判断所属类型 mark_type
        Map<String, String> userMap = rpcService.getOpayUser(inviteCode.getPhone(), String.valueOf(mlis), transferConfig.getMerchantId());
        int markType=0;//
        String role = userMap.get("role");
        if(role!=null && "agent".equals(role)){
            markType=1;
        }
        OpayInviteRelation relation = inviteOperateService.getInviteRelation(masterId,notifyInvite.getOpayId(),inviteCode.getPhone(),notifyInvite.getPhone(),vr,markType);
        List<OpayMasterPupilAward> list =inviteOperateService.getRegisterMasterPupilAward(masterId,notifyInvite.getOpayId(),markType);
        cashbacklist = inviteOperateService.getOpayCashback(list,cashbacklist);
        try {
            log.info("save invite:{}", JSON.toJSONString(relation));
            inviteOperateService.saveRelationAndRewardAndCashback(relation, list, cashbacklist);
        }catch (Exception e){
            return Result.error(CodeMsg.CustomCodeMsg(500,"system error"));
        }
        Map<String,Object> rmap = new HashMap<>();
        rmap.put("reward",rewardConfig.getRegisterReward());
        return Result.success(rmap);
    }

    @ApiOperation(value = "提现列表", notes = "提现列表")
    @PostMapping("/withdrawalList")
    public Result<PageInfo> withdrawalList(HttpServletRequest request, @RequestBody WithdrawalListRequest withdrawalListRequest) throws Exception {
       if(withdrawalListRequest.getTime()==null || "".equals(withdrawalListRequest.getTime())){
           return Result.error(CodeMsg.ILLEGAL_PARAMETER);
       }
        if(withdrawalListRequest.getSign()==null || "".equals(withdrawalListRequest.getSign())){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        Map<String,Object> map = beanToMap(withdrawalListRequest);
        String beforeStr = getBeforeStr(map)+"&key="+key;
        String afterMd5Str = DigestUtils.md5DigestAsHex(beforeStr.getBytes());
        if (!afterMd5Str.equalsIgnoreCase(withdrawalListRequest.getSign())){
            return Result.error(CodeMsg.ILLEGAL_CODE_SIGN);
        }
        PageInfo<List<OpayActiveTixian>> pageInfo = withdrawalService.withdrawalList(withdrawalListRequest);
       return Result.success(pageInfo);
    }

    @ApiOperation(value = "提现列表", notes = "提现列表")
    @PostMapping("/withdrawalTransfer")
    public Result withdrawalTransfer(HttpServletRequest request, @RequestBody WithdrawalApproval withdrawalApproval) throws Exception {
        if(withdrawalApproval.getId()==null){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(withdrawalApproval.getOpayId()==null || "".equals(withdrawalApproval.getOpayId())){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(withdrawalApproval.getTime()==null || "".equals(withdrawalApproval.getTime())){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(withdrawalApproval.getStatus()==null || withdrawalApproval.getStatus()==0){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(withdrawalApproval.getStatus()<0 || withdrawalApproval.getStatus()>2){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        Map<String,Object> map = beanToMap(withdrawalApproval);
        String beforeStr = getBeforeStr(map)+"&key="+key;
        String afterMd5Str = DigestUtils.md5DigestAsHex(beforeStr.getBytes());
        if (!afterMd5Str.equalsIgnoreCase(withdrawalApproval.getSign())){
            return Result.error(CodeMsg.ILLEGAL_CODE_SIGN);
        }
        OpayActiveTixian tixian = withdrawalService.getWithdrawal(withdrawalApproval);
        if(tixian==null){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(!tixian.getOpayId().equals(withdrawalApproval.getOpayId())){
            return Result.error(CodeMsg.ILLEGAL_PARAMETER);
        }
        if(withdrawalApproval.getStatus()==2 && tixian.getStatus()==0){
            withdrawalService.updateTixian(tixian.getId(),tixian.getOpayId(),null,null,withdrawalApproval.getStatus());
            inviteOperateService.rollbackTixian(tixian);
        }else if(withdrawalApproval.getStatus()==1 && tixian.getStatus()==0){
            withdrawalService.updateTixian(tixian.getId(),tixian.getOpayId(),null,null,withdrawalApproval.getStatus());
            inviteOperateService.transfer(tixian);
        }
        return Result.success();
    }


    public <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newTreeMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                if(beanMap.get(key)!=null) {
                    map.put(key + "", beanMap.get(key));
                }
            }
        }
        return map;
    }

    private String getBeforeStr(Map<String, Object> map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null && !"".equals(entry.getValue()) && !"sign".equals(entry.getKey())) {
                sb.append(entry.getKey() + "=" + entry.getValue());
                sb.append("&");
            }
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }
    @PostMapping("/transferNotify")
    public void transferNotify(@RequestBody Map map){
        log.warn("transferNotify:{}",JSON.toJSONString(map));
    }

}
