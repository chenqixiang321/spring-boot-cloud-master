package com.opay.invite.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opay.invite.mapper.InviteMapper;
import com.opay.invite.mapper.OpayActiveCashbackMapper;
import com.opay.invite.mapper.OpayCashbackTixianMapper;
import com.opay.invite.mapper.OpayInviteCodeMapper;
import com.opay.invite.model.*;
import com.opay.invite.model.request.WithdrawalApproval;
import com.opay.invite.model.request.WithdrawalListRequest;
import com.opay.invite.service.ActiveService;
import com.opay.invite.service.InviteService;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.utils.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class InviteServiceImpl implements InviteService {

    @Autowired
    private InviteMapper inviteMapper;

    @Autowired
    private OpayActiveCashbackMapper opayactiveCashbackMapper;

    @Autowired
    private RewardConfig rewardConfig;

    @Autowired
    private OpayInviteCodeMapper opayInviteCodeMapper;

    @Autowired
    private OpayCashbackTixianMapper opayCashbackTixianMapper;

    @Autowired
    private ActiveService activeService;

    @Override
    public int checkRelation(String masterId, String pupilId) {
        return inviteMapper.selectRelationCount(masterId,pupilId);
    }

    @Override
    public OpayInviteRelation selectRelationMasterByMasterId(String masterId) {
        return inviteMapper.selectRelationMasterByMasterId(masterId);
    }

    @Transactional
    @Override
    public void saveInviteRelationAndReward(OpayInviteRelation relation, List<OpayMasterPupilAward> list) {
        //保存关系
        inviteMapper.saveInviteRelation(relation);
        inviteMapper.saveInviteReward(list);

    }

    @Override
    public List<OpayInviteRelationVo> selectRelationByMasterId(String opayId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return inviteMapper.selectRelationByMasterId(opayId);
    }

    @Override
    public List<OpayInviteRankVo> getRankList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return inviteMapper.getRankList();
    }

    @Override
    public List<OpayMasterPupilAwardVo> getDetailList(String opayId, int pageNum, int pageSize) {
        Date endTime = new Date();
        Date startTime = DateFormatter.getDateBefore(endTime,30);
        String end_ymd = DateFormatter.formatShortYMDDate(endTime);
        Integer endYmd = Integer.valueOf(end_ymd);
        String start_ymd = DateFormatter.formatShortYMDDate(startTime);
        Integer startYmd = Integer.valueOf(start_ymd);
        PageHelper.startPage(pageNum,pageSize);
        return inviteMapper.getDetailList(opayId,startYmd,endYmd);
    }

    @Override
    public int getRelationCount(String opayId) {
        return inviteMapper.getRelationCount(opayId);
    }

    @Override
    public Integer getCurrentRelationCount(String masterId, String pupilId) {
        return inviteMapper.getCurrentRelationCount(masterId,pupilId);
    }

    @Override
    public List<OpayMasterPupilAwardVo> getTaskByOpayId(String opayId) {
        return inviteMapper.getTaskByOpayId(opayId);
    }

    @Override
    public OpayInviteRankVo getInviteInfoByOpayId(String opayId) {
        return inviteMapper.getInviteInfoByOpayId(opayId);
    }

    @Override
    public OpayInviteRankVo getTotalRewardByDetail(String opayId) {
        return inviteMapper.getTotalRewardByDetail(opayId);
    }

    @Override
    public OpayInviteCode getInviteCode(String opayId) {
        return opayInviteCodeMapper.getInviteCode(opayId);
    }

    @Override
    public void saveInviteCode(String opayId, String code,String phone,Date time) {
        opayInviteCodeMapper.saveInviteCode(opayId,code,phone,time);
    }

    @Override
    public OpayInviteCode getOpayIdByInviteCode(String inviteCode) {
        return opayInviteCodeMapper.getOpayIdByInviteCode(inviteCode);
    }

    @Override
    public OpayActiveCashback getActivityCashbackByOpayId(String opayId) {
        return opayactiveCashbackMapper.getActivityCashbackByOpayId(opayId);
    }

    @Override
    public void saveCashback(String opayId, String phone,Date time) {
        opayactiveCashbackMapper.saveCashback(opayId,phone,time);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void updateCashback(List<OpayActiveCashback> cashbacklist) throws Exception{
        int lock = activeService.isLockedActive(rewardConfig.getActiveId());
        if (lock > 0) {
            return;
        }

        BigDecimal sumCashBackAmount = activeService.sumCashBackAmount(rewardConfig.getActiveId());
        for(OpayActiveCashback cashback:cashbacklist){
            cashback.setActiveId(rewardConfig.getActiveId());
            if ((cashback.getAmount().add(sumCashBackAmount)).compareTo(rewardConfig.getMaxActiveAmount()) > 0) {
                // 金额查过奖励总额，活动加锁
                activeService.lockActive(rewardConfig.getActiveId());
                break;
            }
            int num = opayactiveCashbackMapper.updateCashback(cashback);
            if(num<=0){
                log.warn("更新失败:{}", JSON.toJSONString(cashbacklist));
                throw new Exception("cash update error");
            }
        }
    }

    @Override
    public int deductionCashback(OpayActiveCashback cashback) {
        return opayactiveCashbackMapper.deductionCashback(cashback);
    }

    @Override
    public void updateCashback(OpayActiveCashback cashback){
        int num = opayactiveCashbackMapper.updateCashback(cashback);
    }

    @Override
    public void saveReward(List<OpayMasterPupilAward> list) {
        inviteMapper.saveInviteReward(list);
    }

    @Override
    public void updateRollbackCashback(OpayActiveCashback cashback) {
        opayactiveCashbackMapper.updateRollbackCashback(cashback);
    }

}
