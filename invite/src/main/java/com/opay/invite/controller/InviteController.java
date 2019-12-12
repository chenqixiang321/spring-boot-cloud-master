package com.opay.invite.controller;

import com.alibaba.fastjson.JSON;
import com.opay.invite.model.*;
import com.opay.invite.model.request.InviteRequest;
import com.opay.invite.resp.CodeMsg;
import com.opay.invite.resp.Result;
import com.opay.invite.service.InviteOperateService;
import com.opay.invite.service.InviteService;
import com.opay.invite.service.RpcService;
import com.opay.invite.stateconfig.ActionOperate;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.transferconfig.TransferConfig;
import com.opay.invite.utils.DateFormatter;
import com.opay.invite.utils.InviteCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/invite")
@Api(value = "邀请API")
public class InviteController {

    @Autowired
    private InviteService inviteService;

    @Autowired
    private InviteOperateService inviteOperateService;

    @Value("${invite.productKey:''}")
    private String key;

    @Autowired
    private RpcService rpcService;

    @Autowired
    private TransferConfig transferConfig;

    @Autowired
    private RewardConfig rewardConfig;

    @Value("${spring.jackson.time-zone:''}")
    private String zone;

    @ApiOperation(value = "生成邀请码", notes = "生成邀请码")
    @PostMapping("/getInviteCode")
    public Result<String> getInviteCode(HttpServletRequest request) {
        //更登录用户生成code
        LoginUser user = inviteOperateService.getOpayInfo(request);
        OpayInviteCode inviteCode = inviteService.getInviteCode(user.getOpayId());
        if(inviteCode==null || "".equals(inviteCode)) {
            String phone = user.getPhoneNumber().substring(user.getPhoneNumber().length()-10,user.getPhoneNumber().length());
            String code = InviteCode.createCode(Long.valueOf(phone));
            inviteService.saveInviteCode(user.getOpayId(),code,user.getPhoneNumber(),new Date());
            return Result.success(code);
        }
        return Result.success(inviteCode.getInviteCode());
    }


    @ApiOperation(value = "插入邀请关系信息", notes = "插入邀请关系信息,data返回收益金额")
    @PostMapping("/save")
    public Result<Map> save(HttpServletRequest request, @RequestBody InviteRequest inviteRequest) throws Exception{
        //判断邀请码是否合法,和反作弊，不能建立师徒关系,当前用户已经过了7天不能填写邀请码
        boolean eff = inviteOperateService.checkTime(zone);
        if(eff){
            log.warn("活动未开始或已结束,inviteRequest info{},"+ JSON.toJSONString(inviteRequest));
            return Result.error(CodeMsg.ILLEGAL_CODE_ACTIVE);
        }
        OpayInviteCode inviteCode = inviteService.getOpayIdByInviteCode(inviteRequest.getInviteCode());
        //获取code用户类型
        if(inviteCode==null){
            return Result.error(CodeMsg.ILLEGAL_CODE);
        }
        //解析登录用户ID和邀请码用户ID
        LoginUser user = inviteOperateService.getOpayInfo(request);
        String masterId=inviteCode.getOpayId();
        if(masterId.equals(user.getOpayId())){
            return Result.error(CodeMsg.ILLEGAL_CODE);
        }
        //当前用户是代理不能存在师傅
        long mlis = System.currentTimeMillis();
        Map<String,String> map = rpcService.getOpayUser(user.getPhoneNumber(),String.valueOf(mlis),transferConfig.getMerchantId());

        String dateStr = map.get("createDate");
        boolean f = inviteOperateService.isExpired(zone,dateStr);
        if(f){
            return Result.error(CodeMsg.ILLEGAL_CODE_DAY);
        }
        //校验用户是否已经建立关系，或是不能互相邀请关系
       int count = inviteService.checkRelation(masterId,user.getOpayId());
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
        OpayActiveCashback pupilcashback = inviteService.getActivityCashbackByOpayId(user.getOpayId());//查询徒弟存在钱包
        if(pupilcashback ==null){
            inviteService.saveCashback(user.getOpayId(),user.getPhoneNumber(),new Date());
            pupilcashback = new OpayActiveCashback();
            pupilcashback.setOpayId(user.getOpayId());
            pupilcashback.setVersion(0);
        }
        cashbacklist.add(pupilcashback);
        // 建立关系，增加金额奖励,同时充入账户，需要判断角色和用户邀请人数所属阶梯，奖励不同
        OpayInviteRelation vr = inviteService.selectRelationMasterByMasterId(masterId);
        //TODO 查询邀请账号，判断所属类型 mark_type
        Map<String, String> userMap = rpcService.getOpayUser(inviteCode.getPhone(), String.valueOf(mlis), transferConfig.getMerchantId());
        int markType=0;
        String role = userMap.get("role");
        if(role!=null && "agent".equals(role)){
            markType=1;
        }
        OpayInviteRelation relation = inviteOperateService.getInviteRelation(masterId,user.getOpayId(),inviteCode.getPhone(),user.getPhoneNumber(),vr,markType);
        List<OpayMasterPupilAward> list =inviteOperateService.getRegisterMasterPupilAward(masterId,user.getOpayId(),markType);
        cashbacklist = inviteOperateService.getOpayCashback(list,cashbacklist);
        inviteOperateService.saveRelationAndRewardAndCashback(relation, list,cashbacklist);
        Map<String,Object> rmap = new HashMap<>();
        rmap.put("reward",rewardConfig.getRegisterReward());
        inviteOperateService.updateInviteCount(masterId);
        return Result.success(rmap);
    }


    //@ApiOperation(value = "My cashback获取奖励总金额", notes = "My cashback获取奖励总金额")
    @PostMapping("/getTotalReward")
    public Result getTotalReward(HttpServletRequest request, @RequestBody InviteRequest inviteRequest) {

        //奖励总金额
        String opayId="";
        OpayActiveCashback cashback = inviteService.getActivityCashbackByOpayId(opayId);
        if(cashback==null){
            return Result.success(0);
        }
        return Result.success(cashback.getAmount());
    }



    @ApiOperation(value = "任务弹窗引导,未做任务", notes = "任务弹窗引导,未做任务，没有任务结果为空，存在结果type:1没有师徒关系，2：没有充值")
    @PostMapping("/noTask")
    public Result<FinishTask> getNoTask(HttpServletRequest request) throws Exception {
        //登录用户是普通用户，如果是普通用户，有师徒关系，查看执行内容，如果没有提示一个
        boolean activef = inviteOperateService.checkTime(zone);
        if(activef) {
            return Result.success();
        }
        LoginUser user = inviteOperateService.getOpayInfo(request);
        long mlis = System.currentTimeMillis();
        Map<String,String> map =rpcService.getOpayUser(user.getPhoneNumber(),String.valueOf(mlis),transferConfig.getMerchantId());
        //如果是代理不提示任何
        if(map==null){
            return Result.success();
        }
        String dateStr = map.get("createDate");
        List<OpayMasterPupilAwardVo> list = inviteService.getTaskByOpayId(user.getOpayId());
        if(list==null || list.size()==0) {
            list = new ArrayList<>();
        }
        Map<Integer, OpayMasterPupilAwardVo> map2 = list.stream().collect(Collectors.toMap(OpayMasterPupilAwardVo::getAction, Function.identity()));
        if(map2.get(ActionOperate.operate_register.getOperate())==null){
            boolean f = inviteOperateService.isExpired(zone,dateStr);
            if (!f) {//已经过了七天，新用户，老用户自动过滤
                FinishTask task = new FinishTask();
                task.setType(ActionOperate.operate_register.getOperate());
                task.setReward(rewardConfig.getRegisterReward());
                return Result.success(task);
            }
        }
        if(map.get(ActionOperate.operate_recharge.getOperate())==null){
            long mills = System.currentTimeMillis();
            Map<String,String> exsitMap = rpcService.queryUserRecordByPhone(user.getPhoneNumber(),rewardConfig.getStartTime(),String.valueOf(mills),null,"TopupWithCard");
            String beforeIsExistOrder =exsitMap.get("beforeIsExistOrder");
            if("Y".equals(beforeIsExistOrder)){//活动开始前已有充值
                return Result.success();
            }
            FinishTask task = new FinishTask();
            task.setType(ActionOperate.operate_recharge.getOperate());
            task.setReward(rewardConfig.getRechargeReward());
            return Result.success(task);
        }
        return Result.success();
    }

}
