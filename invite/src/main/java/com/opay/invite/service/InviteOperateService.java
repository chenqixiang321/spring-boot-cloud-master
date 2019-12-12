package com.opay.invite.service;

import com.alibaba.fastjson.JSON;
import com.opay.invite.model.*;
import com.opay.invite.resp.CodeMsg;
import com.opay.invite.resp.Result;
import com.opay.invite.stateconfig.*;
import com.opay.invite.transferconfig.OrderType;
import com.opay.invite.transferconfig.TransferConfig;
import com.opay.invite.utils.DateFormatter;
import com.opay.invite.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private InviteCountService inviteCountService;

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
        master.setPupilId(pupilId);
        OpayMasterPupilAward pupil = new OpayMasterPupilAward(pupilId,rewardConfig.getRegisterReward(),
                date,1, ActionOperate.operate_register.getOperate(), BigDecimal.ZERO,markType,null,
                rewardConfig.getMasterReward(),0,month,day);//徒弟奖励
        pupil.setPupilId(pupilId);
        list.add(master);
        list.add(pupil);
        return list;
    }
    public List<OpayMasterPupilAward> getRechargeMasterPupilAward(String masterId, String pupilId, StepReward stepReward, int markType) {
        List<OpayMasterPupilAward> list = new ArrayList<>();
        Date date = new Date();
        int month = Integer.valueOf(DateFormatter.formatShortYMDate(new Date()));
        int day= Integer.valueOf(DateFormatter.formatShortYMDDate(new Date()));
        if(masterId!=null && !"".equals(masterId)) {
            OpayMasterPupilAward master = new OpayMasterPupilAward(masterId, stepReward.getWalletReward(),
                    date, 1, ActionOperate.operate_recharge.getOperate(), BigDecimal.ZERO, markType, JSON.toJSONString(stepReward),
                    BigDecimal.ZERO, 1, month, day);//师傅奖励
            master.setPupilId(pupilId);
            list.add(master);
        }
        OpayMasterPupilAward pupil = new OpayMasterPupilAward(pupilId,rewardConfig.getRechargeReward(),
                date,1, ActionOperate.operate_recharge.getOperate(), BigDecimal.ZERO,markType,JSON.toJSONString(stepReward),
                stepReward.getWalletReward(),0,month,day);//徒弟奖励
        pupil.setPupilId(pupilId);
        list.add(pupil);
        return list;
    }

    public List<StepReward> getStepList(int count) {
        List<Reward> list = rewardConfig.getRewardList();
        Collections.sort(list, Comparator.comparing(Reward::getOrderId));
        List<StepReward> slist = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Reward reward =list.get(i);
            StepReward stepReward = new StepReward();
            BeanUtils.copyProperties(reward,stepReward);
            stepReward.setWalletReward(stepReward.getWalletReward().add(rewardConfig.getMasterReward()));
            if(stepReward.getMin()<=count && count<=stepReward.getMax()){
                stepReward.setStep(1);
            }else if(i==0 && count==0){
                stepReward.setStep(1);
            }else if(i==list.size()-1 && count>=stepReward.getMin()){
                stepReward.setStep(1);
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
            }else if(i==0 && count==0){
                stepReward.setStep(1);
            }else if(i==list.size()-1 && count>=stepReward.getMin()){
                stepReward.setStep(1);
            }
            if(stepReward.getStep()==1){
                tmp_stepReward = stepReward;
                break;
            }
        }
        return tmp_stepReward;
    }

    public List<OpayMasterPupilAwardVo> getActivityTask(List<OpayMasterPupilAwardVo> task, OpayInviteRelation ir,int isF7,int isAgent,String phone) throws Exception {
        List<OpayMasterPupilAwardVo> list = new ArrayList<>();
        if (task == null || task.size() == 0) {
            task = new ArrayList<>();
        }
        Map<Integer, OpayMasterPupilAwardVo> map = task.stream().collect(Collectors.toMap(OpayMasterPupilAwardVo::getAction, Function.identity()));
        if(map.get(ActionOperate.operate_register.getOperate())==null){
            if (isF7 == 0) {//已经过了七天，新用户，老用户自动过滤
                OpayMasterPupilAwardVo vo = new OpayMasterPupilAwardVo();
                vo.setAction(1);
                vo.setReward(rewardConfig.getRegisterReward());
                list.add(vo);
            }
        }
        if(map.get(ActionOperate.operate_recharge.getOperate())==null){
            long mills = System.currentTimeMillis();
            Map<String,String> exsitMap = rpcService.queryUserRecordByPhone(phone,rewardConfig.getStartTime(),String.valueOf(mills),null,"TopupWithCard");
            String beforeIsExistOrder =exsitMap.get("beforeIsExistOrder");
            if(!"Y".equals(beforeIsExistOrder)){//活动开始前已有充值
                OpayMasterPupilAwardVo vo = new OpayMasterPupilAwardVo();
                vo.setAction(2);
                vo.setReward(rewardConfig.getRechargeReward());
                list.add(vo);
            }
        }
//        if (task == null || task.size() == 0) {
//            if (isF7 == 0) {//已经过了七天，新用户，老用户自动过滤
//                OpayMasterPupilAwardVo vo = new OpayMasterPupilAwardVo();
//                vo.setAction(1);
//                vo.setReward(rewardConfig.getRegisterReward());
//            }
//            OpayMasterPupilAwardVo vo = new OpayMasterPupilAwardVo();
//            vo.setAction(2);
//            vo.setReward(rewardConfig.getRechargeReward());
//            list.add(vo);
//        } else {
//
//        }
        //区分当前用户师傅是否是代理
        if(rewardConfig.getAgentOpen()==1) {
            if ((ir != null && ir.getMarkType() == 1)) {
                for (AgentRoyaltyReward rr : rewardConfig.getRoyList()) {
                    OpayMasterPupilAwardVo vo = new OpayMasterPupilAwardVo();
                    vo.setAction(rr.getAction());
                    vo.setReward(rr.getPupilReward());
                    list.add(vo);
                }
            }else{
                List<AgentRoyaltyReward> rr = rewardConfig.getRoyList();
                rr.sort(Comparator.comparing(AgentRoyaltyReward::getMasterReward).reversed());
                if(isAgent==1){
                    OpayMasterPupilAwardVo vo = new OpayMasterPupilAwardVo();
                    vo.setAction(6);
                    vo.setReward(rr.get(0).getMasterReward());
                    list.add(vo);
                }
            }
        }
        return list;
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
    public List<OpayMasterPupilAwardVo> getDetailList(List<OpayMasterPupilAwardVo> list) throws Exception {
        List<OpayMasterPupilAwardVo> nlist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            OpayMasterPupilAwardVo vo = list.get(i);
            if (vo.getMasterType() == 1) {//作为师傅
                if (vo.getPupilPhone() != null && !"".equals(vo.getPupilPhone())) {
                    String nameJson = stringRedisTemplate.opsForValue().get(vo.getPupilPhone());
                if (nameJson == null || "".equals(nameJson)) {
                    long mlis = System.currentTimeMillis();
                    Map<String, String> map = rpcService.getOpayUser(vo.getPupilPhone(), String.valueOf(mlis), transferConfig.getMerchantId());
                    if (map != null && map.size() > 0) {
                        vo.setName(map.get("firstName") + " " + map.get("middleName") + " " + map.get("surname"));
                        vo.setGender(map.get("gender"));
                        stringRedisTemplate.opsForValue().set(vo.getPupilPhone(), JSON.toJSONString(map), 1, TimeUnit.DAYS);
                    }

                } else {
                     Map<String, String> jsonMap = JSON.parseObject(nameJson, Map.class);
                     vo.setName(jsonMap.get("firstName") + " " + jsonMap.get("middleName") + " " + jsonMap.get("surname"));
                     vo.setGender(jsonMap.get("gender"));
                 }
                }
            }
            vo.setCreateTime(vo.getCreateAt().getTime());
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
        if (opayId != null) {
            user.setOpayId((String) opayId);
        }
        Object phoneNumber = request.getAttribute("phoneNumber");
        if (phoneNumber != null) {
            user.setPhoneNumber(mobileHandler((String) phoneNumber));
        }
        Object firstName = request.getAttribute("firstName");
        if (firstName != null) {
            user.setFirstName((String) firstName);
        }
        return user;
    }
    private String mobileHandler(String mobile) {
        if (org.apache.commons.lang3.StringUtils.startsWith(mobile, "0")) {
            return "+234" + org.apache.commons.lang3.StringUtils.substringAfter(mobile, "0");
        } else if (org.apache.commons.lang3.StringUtils.startsWith(mobile, "234")) {
            return "+" + mobile;
        } else if (org.apache.commons.lang3.StringUtils.startsWith(mobile, "+234")) {
            return mobile;
        } else {
            return "+234" + mobile;
        }
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
        return true;
    }

    public void rollbackTixian(OpayActiveTixian saveTixian){
        try {
            OpayActiveCashback cashback2 = inviteService.getActivityCashbackByOpayId(saveTixian.getOpayId());
            cashback2.setAmount(saveTixian.getAmount());//扣件金额
            cashback2.setUpdateAt(new Date());
            inviteService.updateRollbackCashback(cashback2);
            log.info("rollbackTixian {},status:{},cashback2:{}",JSON.toJSONString(saveTixian),2,JSON.toJSONString(cashback2));

            OpayActiveTixianLog saveTixianLog = new OpayActiveTixianLog();
            saveTixianLog.setTixianId(saveTixian.getId());
            saveTixianLog.setAmount(saveTixian.getAmount());
            saveTixianLog.setOpayId(saveTixian.getOpayId());
            saveTixianLog.setType(saveTixian.getType());
            saveTixianLog.setCreateAt(new Date());
            saveTixianLog.setDeviceId(saveTixian.getDeviceId());
            saveTixianLog.setMonth(saveTixian.getMonth());
            saveTixianLog.setDay(saveTixian.getDay());
            saveTixianLog.setMark(1);//异常,退回提现金额日志
            withdrawalService.saveTixianLog(saveTixianLog);
            log.info("rollbackTixian {},status:{},cashback2:{}",JSON.toJSONString(saveTixian),4,JSON.toJSONString(cashback2));
        }catch (Exception e){
            log.warn("transter err {},status:{},err:{}",JSON.toJSONString(saveTixian),4,e.getMessage());
        }
    }

    public boolean transfer(OpayActiveTixian saveTixian) throws Exception {
        String orderType = OrderType.bonusOffer.getOrderType();
        if(saveTixian.getType()==1){
            orderType = OrderType.MUAATransfer.getOrderType();
        }
        String reference = transferConfig.getReference()+""+String.format("%10d", saveTixian.getId()).replace(" ", "0");
        Map<String,String> map = rpcService.transfer(reference,saveTixian.getOpayId(),saveTixian.getAmount().toString(),reference,orderType,"BalancePayment");
        if(map==null || map.size()==0){
            log.error("transfer err {}",JSON.toJSONString(saveTixian));
            return false;
        }
        log.info("transfer::::{}",JSON.toJSONString(map));
        if(map!=null && map.size()>0){
            if("504".equals(map.get("code"))){
                log.error("timeout err {}",JSON.toJSONString(saveTixian));
            }else if("00000".equals(map.get("code")) &&
                    ("SUCCESS".equals(map.get("status"))||"PENDING".equals(map.get("status")))
            ){
                try {
                    withdrawalService.updateTixian(saveTixian.getId(), saveTixian.getOpayId(), reference, map.get("orderNo"),3);
                }catch (Exception e){
                    log.error("updateTixian err:{},map:{},status:{}",JSON.toJSONString(saveTixian),JSON.toJSONString(map),3);
                }
            }else{
                log.warn("transter err {},map:{}",JSON.toJSONString(saveTixian),JSON.toJSONString(map));
                withdrawalService.updateTixian(saveTixian.getId(), saveTixian.getOpayId(), reference, map.get("orderNo"), 4);
                rollbackTixian(saveTixian);
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

    public boolean isExpired(String zone1,String createDate){
        LocalDateTime date = LocalDateTime.parse(createDate);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant =date.atZone(zone).toInstant();
        Date regDate = Date.from(instant);
        Date date7 = DateFormatter.getDateAfter(regDate, rewardConfig.getEffectiveDay());
        Date nDate =  new Date();
        String ntime = DateFormatter.formatDatetimeByZone(nDate,zone1);
        Date formatDate = DateFormatter.parseDatetime(ntime);
        if (date7.getTime() < formatDate.getTime()) {
           return true;
        }
        Date startTime = DateFormatter.parseDatetime(rewardConfig.getStartTime());
        if(regDate.getTime()<startTime.getTime()){
            return true;
        }
        return false;
    }

    public boolean checkTime(String zone) {
        Date startTime = DateFormatter.parseDatetime(rewardConfig.getStartTime());
        Date endTime = DateFormatter.parseDatetime(rewardConfig.getEndTime());
        String ntime = DateFormatter.formatDatetimeByZone(new Date(),zone);
        Date nowTime = DateFormatter.parseDatetime(ntime);
        if(nowTime.getTime()<startTime.getTime() || nowTime.getTime()>endTime.getTime()){
            return true;
        }
        return false;
    }

    public int checkActiveStatusTime(String zone,String start,String end) {
        Date startTime = DateFormatter.parseDatetime(start);
        Date endTime = DateFormatter.parseDatetime(end);
        String ntime = DateFormatter.formatDatetimeByZone(new Date(),zone);
        Date nowTime = DateFormatter.parseDatetime(ntime);
        if(nowTime.getTime()<startTime.getTime()){
            return 0;//活动未开始
        }else if(nowTime.getTime()>endTime.getTime()){
            return 2;//活动已开始
        }
        return 1;
    }
    public int checkActiveStatusTime(Date nowTime,String start,String end) {
        Date startTime = DateFormatter.parseDatetime(start);
        Date endTime = DateFormatter.parseDatetime(end);
        if(nowTime.getTime()<startTime.getTime()){
            return 0;//活动未开始
        }else if(nowTime.getTime()>endTime.getTime()){
            return 2;//活动已开始
        }
        return 1;
    }

    @Async
    public void updateInviteCount(String masterId){
        try {
            inviteCountService.updateInviteCount(masterId,null,null);
        } catch (Exception e) {
            log.error("updateInviteCount masterId:{}",masterId);
        }
    }
}
