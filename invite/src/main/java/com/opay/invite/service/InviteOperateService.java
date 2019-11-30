package com.opay.invite.service;

import com.alibaba.fastjson.JSON;
import com.opay.invite.model.*;
import com.opay.invite.stateconfig.*;
import com.opay.invite.transferconfig.OrderType;
import com.opay.invite.transferconfig.TransferConfig;
import com.opay.invite.utils.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InviteOperateService {

    @Autowired
    private RewardConfig rewardConfig;

    @Autowired
    private InviteService inviteService;

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private RpcService rpcService;

    @Autowired
    private TransferConfig transferConfig;

    public OpayInviteRelation getInviteRelation(String masterId, String pupilId, String masterPhone, String pupilPhone, OpayInviteRelation vr, int markType) {
        OpayInviteRelation relation = new OpayInviteRelation();
        relation.setMasterId(masterId);
        relation.setPupilId(pupilId);
        relation.setCreateAt(new Date());
        relation.setMonth(Integer.valueOf(DateFormatter.formatShortYMDate(new Date())));
        relation.setDay(Integer.valueOf(DateFormatter.formatShortYMDDate(new Date())));
        relation.setPupilPhone(pupilPhone);
        relation.setMasterPhone(masterPhone);
        if(vr!=null){
            relation.setMasterParentId(vr.getMasterId());
        }
        relation.setMarkType(markType);
        return relation;
    }

    public List<OpayMasterPupilAward> getRegisterMasterPupilAward(String masterId, String pupilId, int markType) {
        List<OpayMasterPupilAward> list = new ArrayList<>();
        Date date = new Date();
        int month = Integer.valueOf(DateFormatter.formatShortYMDate(new Date()));
        int day= Integer.valueOf(DateFormatter.formatShortYMDDate(new Date()));
        OpayMasterPupilAward master = new OpayMasterPupilAward(masterId,rewardConfig.getMasterReward(),
                date,1, ActionOperate.operate_register.getOperate(), BigDecimal.ZERO,markType,null,
                BigDecimal.ZERO,1,month,day);//师傅奖励
        OpayMasterPupilAward pupil = new OpayMasterPupilAward(pupilId,rewardConfig.getRegisterReward(),
                date,1, ActionOperate.operate_register.getOperate(), BigDecimal.ZERO,markType,null,
                rewardConfig.getMasterReward(),0,month,day);//徒弟奖励
        list.add(master);
        list.add(pupil);
        return list;
    }
    public List<OpayMasterPupilAward> getRechargeMasterPupilAward(String masterId, String pupilId, StepReward stepReward, int markType) {
        List<OpayMasterPupilAward> list = new ArrayList<>();
        Date date = new Date();
        int month = Integer.valueOf(DateFormatter.formatShortYMDate(new Date()));
        int day= Integer.valueOf(DateFormatter.formatShortYMDDate(new Date()));
        OpayMasterPupilAward master = new OpayMasterPupilAward(masterId,stepReward.getWalletReward(),
                date,1, ActionOperate.operate_recharge.getOperate(), BigDecimal.ZERO,markType,JSON.toJSONString(stepReward),
                BigDecimal.ZERO,1,month,day);//师傅奖励
        OpayMasterPupilAward pupil = new OpayMasterPupilAward(pupilId,rewardConfig.getRechargeReward(),
                date,1, ActionOperate.operate_recharge.getOperate(), BigDecimal.ZERO,markType,JSON.toJSONString(stepReward),
                stepReward.getWalletReward(),0,month,day);//徒弟奖励
        list.add(master);
        list.add(pupil);
        return list;
    }

    public List<StepReward> getStepList(int count) {
        List<Reward> list = rewardConfig.getRewardList();
        Collections.sort(list, Comparator.comparing(Reward::getOrderId));
        List<StepReward> slist = new ArrayList<>();
        StepReward tmp_stepReward=null;
        for(int i=0;i<list.size();i++){
            Reward reward =list.get(i);
            StepReward stepReward = new StepReward();
            BeanUtils.copyProperties(reward,stepReward);
            if(stepReward.getMin()<=count && count<=stepReward.getMax()){
                stepReward.setStep(1);
            }
            if(i==0){
                if(count==0){
                    stepReward.setStep(1);
                }
            }else if(i==list.size()-1){
                if(stepReward.getMin()<=count){
                    stepReward.setStep(1);
                }
            }
            slist.add(stepReward);
        }
        return slist;
    }
    public StepReward getStepReward(int count) {
        List<Reward> list = rewardConfig.getRewardList();
        Collections.sort(list, Comparator.comparing(Reward::getOrderId));
        List<StepReward> slist = new ArrayList<>();
        StepReward tmp_stepReward=null;
        for(int i=0;i<list.size();i++){
            Reward reward =list.get(i);
            StepReward stepReward = new StepReward();
            BeanUtils.copyProperties(reward,stepReward);
            if(stepReward.getMin()<=count && count<=stepReward.getMax()){
                stepReward.setStep(1);
            }
            if(i==0){
                if(count==0){
                    stepReward.setStep(1);
                }
            }else if(i==list.size()-1){
                if(stepReward.getMin()<=count){
                    stepReward.setStep(1);
                }
            }
            if(stepReward.getStep()==1){
                tmp_stepReward = stepReward;
                break;
            }
        }
        return tmp_stepReward;
    }

    public List<OpayMasterPupilAwardVo> getActivityTask(List<OpayMasterPupilAwardVo> task, OpayInviteRelation ir,int isF7) {
        if(task!=null && task.size()>0){
            List<OpayMasterPupilAwardVo> list = new ArrayList<>();
            if(task.size()==1){
                OpayMasterPupilAwardVo vo = new OpayMasterPupilAwardVo();
                vo.setAction(2);
                vo.setReward(rewardConfig.getRechargeReward());
                list.add(vo);
            }
            //区分当前用户师傅是否是代理
           if(rewardConfig.getAgentOpen()==1) {
               if (ir != null) {
                   if (ir.getMarkType() == 2) {//如果代理存在降级或是不存在情况?
                       for (AgentRoyaltyReward rr : rewardConfig.getRoyList()) {
                           OpayMasterPupilAwardVo vo = new OpayMasterPupilAwardVo();
                           vo.setAction(rr.getAction());
                           vo.setReward(rr.getPupilReward());
                           list.add(vo);
                       }
                   }
               }
           }
            return list;
        }else{
            if(isF7==1){//用户已经超过七天，
                return null;
            }
            List<OpayMasterPupilAwardVo> list = new ArrayList<>();
            OpayMasterPupilAwardVo vo = new OpayMasterPupilAwardVo();
            vo.setAction(1);
            vo.setReward(rewardConfig.getRegisterReward());
            list.add(vo);
            OpayMasterPupilAwardVo vo2 = new OpayMasterPupilAwardVo();
            vo2.setAction(2);
            vo2.setReward(rewardConfig.getRechargeReward());
            list.add(vo2);
            return list;
        }
    }

    public List<AgentRoyaltyReward> getAgentRule() {
        List<AgentRoyaltyReward> list = rewardConfig.getRoyList();
        for(int i=0;i<list.size();i++){
            AgentRoyaltyReward ar = list.get(i);
            if(ar.getAction()==3){//充值
                ar.setActionName(MsgConst.airtime);
            }else if(ar.getAction()==4){//博彩
                ar.setActionName(MsgConst.betting);
            }else if(ar.getAction()==5){//打车
                ar.setActionName(MsgConst.oride);
            }
        }
        return list;
    }

    //填充规则和文本信息
    public void getAgentTask(OpayActivity activity) {
        List<AgentRoyaltyReward> list =rewardConfig.getRoyList();
        Collections.sort(list, Comparator.comparing(AgentRoyaltyReward::getMasterReward).reversed());
        activity.setAgentTaskReward(list.get(0).getMasterReward());
    }

    //填充行为名称
    public List<OpayMasterPupilAwardVo> getDetailList(List<OpayMasterPupilAwardVo> list) {
        List<OpayMasterPupilAwardVo> nlist = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            OpayMasterPupilAwardVo vo = list.get(i);
            String msg = ActionOperate.getMsgEn(vo.getAction());
            vo.setActionName(msg);
            vo.setCreateTime(vo.getCreateAt().getTime());
            nlist.add(vo);
        }
        return nlist;
    }

    public LoginUser getOpayInfo(HttpServletRequest request){
        LoginUser user = new LoginUser();
        Object opayId = request.getAttribute("opayId");
        if(opayId !=null){
            user.setOpayId((String)opayId);
        }
        Object phoneNumber = request.getAttribute("phoneNumber");
        if(phoneNumber !=null){
            user.setPhoneNumber((String)phoneNumber);
        }
        return user;
    }
    //组拼钱包数据
    public List<OpayActiveCashback> getOpayCashback(List<OpayMasterPupilAward> list, List<OpayActiveCashback> cashbacklist) {
        Map<String, OpayMasterPupilAward> map = list.stream().collect(Collectors.toMap(OpayMasterPupilAward::getOpayId, Function.identity()));
        List<OpayActiveCashback> nlist = new ArrayList<>();
        for(int i=0;i<cashbacklist.size();i++){
            OpayActiveCashback cashback = cashbacklist.get(i);
            if(map.get(cashback.getOpayId())!=null){
                OpayMasterPupilAward ward = map.get(cashback.getOpayId());
                cashback.setAmount(ward.getReward());
                cashback.setTotalAmount(ward.getReward());
                cashback.setUpdateAt(new Date());
                nlist.add(cashback);
            }
        }
        return nlist;
    }

    //保存邀请关系、各自收益、钱包更新
    @Transactional(rollbackFor = Exception.class)
    public void saveRelationAndRewardAndCashback(OpayInviteRelation relation, List<OpayMasterPupilAward> list, List<OpayActiveCashback> cashbacklist) throws Exception{
        inviteService.saveInviteRelationAndReward(relation, list);
        inviteService.updateCashback(cashbacklist);
    }
    //保存提现内容和日志
    @Transactional(rollbackFor=Exception.class)
    public boolean saveTixianAndLog(OpayActiveTixian saveTixian, OpayActiveTixianLog saveTixianLog, OpayActiveCashback cashback) throws Exception {
        cashback.setAmount(saveTixian.getAmount());//扣件金额
        cashback.setUpdateAt(new Date());
        int count = inviteService.deductionCashback(cashback);
        if(count<=0){
            log.warn("saveTixianAndLog 提现异常saveTixian:{},cashback:{}", JSON.toJSONString(saveTixian),JSON.toJSONString(cashback));
            throw new Exception("cash out error");
        }
        withdrawalService.saveTixian(saveTixian);
        saveTixianLog.setTixianId(saveTixian.getId());
        saveTixianLog.setMark(0);
        withdrawalService.saveTixianLog(saveTixianLog);
        String orderType = OrderType.bonusOffer.getOrderType();
        if(saveTixian.getType()==1){
            orderType = OrderType.MUAATransfer.getOrderType();
        }
        String reference = transferConfig.getReference()+""+String.format("%10d", saveTixian.getId()).replace(" ", "0");;
        Map<String,String> map = rpcService.transfer(reference,cashback.getOpayId(),saveTixian.getAmount().toString(),reference,orderType,"BalancePayment");
        if(map==null || map.size()==0){
            log.error("transfer err {}",JSON.toJSONString(saveTixian));
            throw new Exception("transfer error");
        }
        if(map!=null && map.size()>0){
            if("504".equals(map.get("code"))){
                log.error("timeout err {}",JSON.toJSONString(saveTixian));
            }else if("00000".equals(map.get("code")) && "SUCCESS".equals(map.get("status"))){
                try {
                    withdrawalService.updateTixian(saveTixian.getId(), saveTixian.getOpayId(), reference, map.get("orderNo"),3);
                }catch (Exception e){
                    log.error("updateTixian err:{},map:{},status:{}",JSON.toJSONString(saveTixian),JSON.toJSONString(map),3);
                }
            }else{
                log.warn("transter err {},map:{}",JSON.toJSONString(saveTixian),JSON.toJSONString(map));
                try {
                    withdrawalService.updateTixian(saveTixian.getId(), saveTixian.getOpayId(), reference, map.get("orderNo"),4);
                    saveTixianLog.setMark(1);//异常
                    withdrawalService.saveTixianLog(saveTixianLog);
                    OpayActiveCashback cashback2 = inviteService.getActivityCashbackByOpayId(saveTixian.getOpayId());
                    cashback2.setAmount(saveTixian.getAmount());//扣件金额
                    cashback2.setUpdateAt(new Date());
                    inviteService.updateCashback(cashback2);
                }catch (Exception e){
                    log.warn("transter err {},map:{},status:{},err:{}",JSON.toJSONString(saveTixian),JSON.toJSONString(map),4,e.getMessage());
                }
                return false;
            }
        }
        return true;
    }

    //保存邀请关系、各自收益、钱包更新
    @Transactional(rollbackFor = Exception.class)
    public void saveRewardAndCashback(List<OpayMasterPupilAward> list, List<OpayActiveCashback> cashbacklist) throws Exception {
        inviteService.saveReward(list);
        inviteService.updateCashback(cashbacklist);
    }
}
