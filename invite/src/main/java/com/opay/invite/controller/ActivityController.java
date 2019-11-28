package com.opay.invite.controller;

import com.opay.invite.model.*;
import com.opay.invite.model.InviteRequest;
import com.opay.invite.resp.Result;
import com.opay.invite.service.InviteOperateService;
import com.opay.invite.service.InviteService;
import com.opay.invite.service.RpcService;
import com.opay.invite.stateconfig.AgentRoyaltyReward;
import com.opay.invite.stateconfig.MsgConst;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.transferconfig.TransferConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ApiOperation(value = "获取活动页内容", notes = "获取活动页内容")
    @PostMapping("/getActivity")
    public Result getActivity(HttpServletRequest request) {
        LoginUser user = inviteOperateService.getOpayInfo(request);
        OpayActivity activity = new OpayActivity();
        activity.setIsAgent(0);//是否为代理

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
            List<OpayMasterPupilAwardVo> noTask =inviteOperateService.getActivityTask(task,ir);
            activity.setTask(noTask);
        }else if(activity.getIsAgent()==1){//代理
            inviteOperateService.getAgentTask(activity);
        }

        return Result.success(activity);
    }

    @ApiOperation(value = "获取活动页历史用户前30名数据", notes = "获取活动页历史用户前30名数据")
    @PostMapping("/rank")
    public Result getRankList(HttpServletRequest request) throws Exception {

        List<OpayInviteRankVo> list = inviteService.getRankList(1,30);
        //获取列表,名称和奖励金额，邀请人数
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                OpayInviteRankVo vo = list.get(i);
                Map<String,String> map = rpcService.getOpayUser(vo.getMasterId(),vo.getMasterPhone(),transferConfig.getMerchantId());
                if(map!=null && map.size()>0){
                   String firstName = map.get("firstName");
                    vo.setName(firstName+"***");
                }
                list.add(i,vo);
            }
        }
        //缓存一定时间

        return Result.success(list);
    }


    @ApiOperation(value = "获取当前登录人邀请列表", notes = "获取当前登录人邀请列表")
    @PostMapping("/list")
    public Result getInviteList(HttpServletRequest request, @RequestBody InviteRequest inviteRequest) throws Exception {
        //获取列表关系列表和奖励金额
        LoginUser user = inviteOperateService.getOpayInfo(request);
        List<OpayInviteRelationVo> list= inviteService.selectRelationByMasterId(user.getOpayId(),inviteRequest.getPageNum(),inviteRequest.getPageSize());
        //调用opay 获取用户头像信息
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                OpayInviteRelationVo vo = list.get(i);
                Map<String,String> map = rpcService.getOpayUser(vo.getPupilId(),vo.getPupilPhone(),transferConfig.getMerchantId());
                if(map!=null && map.size()>0){
                    String firstName = map.get("firstName");
                    vo.setName(map.get("firstName")+map.get("middleName")+map.get("surname"));
                }
                list.add(i,vo);
            }
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list",list);
        if(list!=null && list.size()>0){//有好友
            OpayInviteRankVo rankVo = inviteService.getInviteInfoByOpayId(user.getOpayId());
            map.put("msg",MsgConst.inviteFriendlist.replaceFirst("d%",rankVo.getTotalReward().toString()));
        }else{
            map.put("msg",MsgConst.inviteNoFriendlist+rewardConfig.getRegisterReward().add(rewardConfig.getRewardList().get(0).getWalletReward()));
        }
        return Result.success(map);
    }

    @ApiOperation(value = "My cashback获取奖励明细列表", notes = "获取奖励明细列表")
    @PostMapping("/detailList")
    public Result getDetailList(HttpServletRequest request, @RequestBody InviteRequest inviteRequest) {

        //获取列表,默认最近30天
        LoginUser user = inviteOperateService.getOpayInfo(request);
        List<OpayMasterPupilAwardVo> list = inviteService.getDetailList(user.getOpayId(),inviteRequest.getPageNum(),inviteRequest.getPageSize());
        OpayActiveCashback cashback = inviteService.getActivityCashbackByOpayId(user.getOpayId());
        Map<String,Object> map = new HashMap<String,Object>();
        if(cashback==null){
            cashback = new OpayActiveCashback();
            cashback.setAmount(BigDecimal.ZERO);
            cashback.setTotalAmount(BigDecimal.ZERO);
        }
        map.put("totalReward",cashback.getTotalAmount());//所有总收益
        map.put("cashReward",cashback.getAmount());//账户金额
        list = inviteOperateService.getDetailList(list);
        map.put("list",list);
        return Result.success(map);
    }

    @ApiOperation(value = "获取代理任务规则", notes = "获取代理任务规则")
    @PostMapping("/getAgentRule")
    public Result getAgentRule(HttpServletRequest request) {

        List<AgentRoyaltyReward> list = inviteOperateService.getAgentRule();

        return Result.success(list);
    }

}
