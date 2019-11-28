package com.opay.invite.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.opay.invite.mapper.InviteMapper;
import com.opay.invite.mapper.OpayInviteCodeMapper;
import com.opay.invite.mapper.OpayActiveCashbackMapper;
import com.opay.invite.model.*;
import com.opay.invite.service.InviteService;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.utils.DateFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<OpayInviteRelationVo> selectRelationByMasterId(String opayId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return inviteMapper.selectRelationByMasterId(opayId);
    }

    @Override
    public List<OpayInviteRankVo> getRankList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return inviteMapper.getRankList();
    }

    @Override
    public List<OpayMasterPupilAwardVo> getDetailList(String opayId,int pageNum, int pageSize) {
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
    public String getInviteCode(String opayId) {
        return opayInviteCodeMapper.getInviteCode(opayId);
    }

    @Override
    public void saveInviteCode(String opayId, String code) {
        opayInviteCodeMapper.saveInviteCode(opayId,code);
    }

    @Override
    public String getOpayIdByInviteCode(String inviteCode) {
        return opayInviteCodeMapper.getOpayIdByInviteCode(inviteCode);
    }

    @Override
    public OpayActiveCashback getActivityCashbackByOpayId(String opayId) {
        return opayactiveCashbackMapper.getActivityCashbackByOpayId(opayId);
    }

    @Override
    public void saveCashback(String opayId, String phone) {
        opayactiveCashbackMapper.saveCashback(opayId,phone);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void updateCashback(List<OpayActiveCashback> cashbacklist) throws Exception{
        for(OpayActiveCashback cashback:cashbacklist){
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

    public void updateCashback(OpayActiveCashback cashback){
        int num = opayactiveCashbackMapper.updateCashback(cashback);
    }
}
