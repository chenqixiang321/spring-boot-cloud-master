package com.opay.invite.controller;

import com.alibaba.fastjson.JSON;
import com.google.inject.internal.cglib.core.$CodeEmitter;
import com.opay.invite.model.*;
import com.opay.invite.model.request.InviteRequest;
import com.opay.invite.resp.CodeMsg;
import com.opay.invite.resp.Result;
import com.opay.invite.service.InviteOperateService;
import com.opay.invite.service.InviteService;
import com.opay.invite.service.RpcService;
import com.opay.invite.stateconfig.ActionOperate;
import com.opay.invite.stateconfig.AgentRoyaltyReward;
import com.opay.invite.stateconfig.MsgConst;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.transferconfig.TransferConfig;
import com.opay.invite.utils.DateFormatter;
import com.opay.invite.utils.InviteCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping(value = "/activity")
@Api(value = "活动API")
public class ActivityController {

    @Autowired
    private InviteService inviteService;

    @Autowired
    private InviteOperateService inviteOperateService;

    @Autowired
    private RewardConfig rewardConfig;

    @Autowired
    private RpcService rpcService;

    @Autowired
    private TransferConfig transferConfig;

    @Value("${spring.jackson.time-zone:''}")
    private String zone;

    @ApiOperation(value = "获取活动页内容", notes = "获取活动页内容")
    @PostMapping("/getActivity")
    public Result<OpayActivity> getActivity(HttpServletRequest request) throws Exception {
        LoginUser user = inviteOperateService.getOpayInfo(request);
        OpayActivity activity = new OpayActivity();
        activity.setIsAgent(0);//是否为代理
        long mlis = System.currentTimeMillis();
        Map<String,String> map = rpcService.getOpayUser(user.getPhoneNumber(),String.valueOf(mlis),transferConfig.getMerchantId());
        int isF7 =0;//未过七天
        if(map!=null && map.size()>0){
            String role = map.get("role");
            if(role!=null && "agent".equals(role)){
                activity.setIsAgent(1);//是否为代理
            }
            if(activity.getIsAgent()==1) {
                String dateStr = map.get("createDate");
                boolean f = inviteOperateService.isExpired(zone,dateStr);
                if(f){
                    isF7 =1;
                }
            }
        }

        OpayInviteCode inviteCode = inviteService.getInviteCode(user.getOpayId());
        if(inviteCode==null || "".equals(inviteCode)) {
            String phone = user.getPhoneNumber().substring(user.getPhoneNumber().length()-10,user.getPhoneNumber().length());
            String code = InviteCode.createCode(Long.valueOf(phone));
            inviteService.saveInviteCode(user.getOpayId(),code,user.getPhoneNumber(),new Date());
            activity.setInviteCode(code);
        }else{
            activity.setInviteCode(inviteCode.getInviteCode());
        }

        OpayActiveCashback cashback = inviteService.getActivityCashbackByOpayId(user.getOpayId());
        if(cashback==null){
            cashback = new OpayActiveCashback();
            activity.setAmount(BigDecimal.ZERO);
        }
        int count =inviteService.getRelationCount(user.getOpayId());

        //计算用户所属阶段
        List<StepReward> stepList = inviteOperateService.getStepList(count);
        activity.setStep(stepList);
        //邀请人数
        if(count==0){
            activity.setIsFriend(0);
            StepReward stepReward =stepList.get(0);
            activity.setFriendText(MsgConst.noFriend+rewardConfig.getRegisterReward().add(stepReward.getWalletReward()));
        }else {//有徒弟，并且有对应阶段
           BigDecimal rd = BigDecimal.ZERO;
            for(int i=0;i<stepList.size();i++){
                StepReward stepReward = stepList.get(i);
                if(stepReward.getStep()==1){
                    rd = stepReward.getWalletReward();
                    break;
                }
            }
            activity.setFriendText(MsgConst.friend+rd.toString());
        }
        if(activity.getIsAgent()==0){//普通用户
            List<OpayMasterPupilAwardVo> task = inviteService.getTaskByOpayId(user.getOpayId());//获取注册任务和充值任务
            OpayInviteRelation ir = inviteService.selectRelationMasterByMasterId(user.getOpayId());
            List<OpayMasterPupilAwardVo> noTask =inviteOperateService.getActivityTask(task,ir,isF7);
            activity.setTask(noTask);
        }else if(activity.getIsAgent()==1){//代理
            inviteOperateService.getAgentTask(activity);
        }

        return Result.success(activity);
    }

    @ApiOperation(value = "获取活动页历史用户前30名数据", notes = "获取活动页历史用户前30名数据")
    @PostMapping("/rank")
    public Result<List<OpayInviteRankVo>> getRankList(HttpServletRequest request) throws Exception {

        List<OpayInviteRankVo> list = inviteService.getRankList(1,30);
        //获取列表,名称和奖励金额，邀请人数
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                OpayInviteRankVo vo = list.get(i);
                long mlis = System.currentTimeMillis();
                Map<String,String> map = rpcService.getOpayUser(vo.getPhone(),String.valueOf(mlis),transferConfig.getMerchantId());
                if(map!=null && map.size()>0){
                   String firstName = map.get("firstName");
                    vo.setName(firstName+"***");
                }
            }
        }
        //缓存一定时间

        return Result.success(list);
    }


    @ApiOperation(value = "获取当前登录人邀请列表", notes = "获取当前登录人邀请列表")
    @PostMapping("/list")
    public Result<InviteList> getInviteList(HttpServletRequest request, @RequestBody InviteRequest inviteRequest) throws Exception {
        //获取列表关系列表和奖励金额
        LoginUser user = inviteOperateService.getOpayInfo(request);
        List<OpayInviteRelationVo> list= inviteService.selectRelationByMasterId(user.getOpayId(),inviteRequest.getPageNum(),inviteRequest.getPageSize());
        //调用opay 获取用户头像信息
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                OpayInviteRelationVo vo = list.get(i);
                long mlis = System.currentTimeMillis();
                Map<String,String> map = rpcService.getOpayUser(vo.getPupilPhone(),String.valueOf(mlis),transferConfig.getMerchantId());
                if(map!=null && map.size()>0){
                    vo.setName(map.get("firstName")+map.get("middleName")+map.get("surname"));
                }
                vo.setCreateTime(vo.getCreateAt().getTime());
            }
        }
        InviteList map = new InviteList();
        map.setList(list);
        if(list!=null && list.size()>0){//有好友
            OpayInviteRankVo rankVo = inviteService.getInviteInfoByOpayId(user.getOpayId());
            map.setMsg(MsgConst.inviteFriendlist.replaceFirst("d%",rankVo.getTotalReward().toString()));

        }else{
            map.setMsg(MsgConst.inviteNoFriendlist+rewardConfig.getRegisterReward().add(rewardConfig.getRewardList().get(0).getWalletReward()));
        }
        return Result.success(map);
    }

    @ApiOperation(value = "My cashback获取奖励明细列表", notes = "获取奖励明细列表")
    @PostMapping("/detailList")
    public Result<CashbackDetailList> getDetailList(HttpServletRequest request, @RequestBody InviteRequest inviteRequest) {

        //获取列表,默认最近30天
        LoginUser user = inviteOperateService.getOpayInfo(request);
        List<OpayMasterPupilAwardVo> list = inviteService.getDetailList(user.getOpayId(),inviteRequest.getPageNum(),inviteRequest.getPageSize());
        OpayActiveCashback cashback = inviteService.getActivityCashbackByOpayId(user.getOpayId());
        if(cashback==null){
            cashback = new OpayActiveCashback();
            cashback.setAmount(BigDecimal.ZERO);
            cashback.setTotalAmount(BigDecimal.ZERO);
        }
        CashbackDetailList map = new CashbackDetailList();
        map.setTotalReward(cashback.getTotalAmount());//所有总收益
        map.setCashReward(cashback.getAmount());//账户金额
        list = inviteOperateService.getDetailList(list);
        map.setList(list);
        return Result.success(map);
    }

    @ApiOperation(value = "获取代理任务规则", notes = "获取代理任务规则")
    @PostMapping("/getAgentRule")
    public Result<List<AgentRoyaltyReward>> getAgentRule(HttpServletRequest request) {

        List<AgentRoyaltyReward> list = inviteOperateService.getAgentRule();

        return Result.success(list);
    }

    @ApiOperation(value = "获取充值返利", notes = "获取充值返利")
    @PostMapping("/saveRecharge")
    public Result saveRecharge(HttpServletRequest request) throws Exception {
        LoginUser user = inviteOperateService.getOpayInfo(request);

        //活动未开始结束不在执行
        boolean f = checkTime();
        if(f){
            log.warn("活动未开始或已结束,info{},"+JSON.toJSONString(user));
            return Result.success();
        }
//        long mlis = System.currentTimeMillis();
//        Map<String,String> umap = rpcService.getOpayUser(user.getPhoneNumber(),String.valueOf(mlis),transferConfig.getMerchantId());
//        String role = umap.get("role");
//        if(role!=null && "agent".equals(role)){
//            log.warn("当前用户是代理info:{},map:{}", JSON.toJSONString(user),JSON.toJSONString(umap));
//            return Result.success();
//        }

        OpayInviteRelation relation = inviteService.selectRelationMasterByMasterId(user.getOpayId());
        if(relation==null){//没有师徒关系
            log.warn("当前用户没有师徒关系info:{}", JSON.toJSONString(user));
        }
        List<OpayMasterPupilAwardVo> list = inviteService.getTaskByOpayId(user.getOpayId());
        if(list.size()==2){
            return Result.error(CodeMsg.ILLEGAL_CODE_FIRST);
        }
        Map<Integer, OpayMasterPupilAwardVo> map = list.stream().collect(Collectors.toMap(OpayMasterPupilAwardVo::getAction, Function.identity()));
        if(map.get(ActionOperate.operate_recharge.getOperate())!=null){
            return Result.error(CodeMsg.ILLEGAL_CODE_FIRST);
        }
        //查询是否有首次充值
        Map<String,String> exsitMap = rpcService.queryUserRecordByPhone(user.getPhoneNumber(),rewardConfig.getStartTime());
        String exists =exsitMap.get("exists");
        if(!"Y".equals(exists)){
            return Result.success();
        }
        int count =inviteService.getRelationCount(user.getOpayId());
        //计算用户所属阶段
        StepReward stepReward = inviteOperateService.getStepReward(count);
        List<OpayMasterPupilAward> list2 = null;
        if(relation !=null) {
            list2 = inviteOperateService.getRechargeMasterPupilAward(relation.getMasterId(), user.getOpayId(), stepReward, relation.getMarkType());
        }else{
            list2 = inviteOperateService.getRechargeMasterPupilAward(null, user.getOpayId(), stepReward,0);//默认普通
        }
        OpayActiveCashback cashback = inviteService.getActivityCashbackByOpayId(user.getOpayId());
        if(cashback ==null){
            inviteService.saveCashback(user.getOpayId(),user.getPhoneNumber(),new Date());
            cashback = new OpayActiveCashback();
            cashback.setOpayId(user.getOpayId());
            cashback.setVersion(0);
        }
        List<OpayActiveCashback> cashbacklist = new ArrayList<>();
        cashbacklist.add(cashback);
        if(relation !=null) {
            OpayActiveCashback masterCashback = inviteService.getActivityCashbackByOpayId(relation.getMasterId());
            cashbacklist.add(masterCashback);
        }
        cashbacklist = inviteOperateService.getOpayCashback(list2,cashbacklist);
        inviteOperateService.saveRewardAndCashback(list2,cashbacklist);
        return Result.success();
    }

    private boolean checkTime() {
        Date startTime = DateFormatter.parseDatetime(rewardConfig.getStartTime());
        Date endTime = DateFormatter.parseDatetime(rewardConfig.getEndTime());
        String ntime = DateFormatter.formatDatetimeByZone(new Date(),zone);
        Date nowTime = DateFormatter.parseDatetime(ntime);
        if(nowTime.getTime()<startTime.getTime() || nowTime.getTime()>endTime.getTime()){
            return true;
        }
       return false;
    }

}
