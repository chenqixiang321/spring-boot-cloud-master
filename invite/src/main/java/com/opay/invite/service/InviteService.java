package com.opay.invite.service;

import com.opay.invite.model.*;

import java.util.Date;
import java.util.List;

public interface InviteService {

    int checkRelation(String masterId, String pupilId);

    OpayInviteRelation selectRelationMasterByMasterId(String masterId);

    void saveInviteRelationAndReward(OpayInviteRelation relation, List<OpayMasterPupilAward> list);

    List<OpayInviteRelationVo> selectRelationByMasterId(String opayId, int pageNum, int pageSize);

    List<OpayInviteRankVo> getRankList(int pageNum, int pageSize);

    List<OpayMasterPupilAwardVo> getDetailList(String opayId, int pageNum, int pageSize);

    int getRelationCount(String opayId);

    List<OpayMasterPupilAwardVo> getTaskByOpayId(String opayId);

    OpayInviteRankVo getInviteInfoByOpayId(String opayId);

    OpayInviteRankVo getTotalRewardByDetail(String opayId);

    OpayInviteCode getInviteCode(String opayId);

    void saveInviteCode(String opayId, String code, String phone, Date time);

    OpayInviteCode getOpayIdByInviteCode(String inviteCode);

    OpayActiveCashback getActivityCashbackByOpayId(String opayId);

    void saveCashback(String opayId, String phone,Date time);

    void updateCashback(List<OpayActiveCashback> cashbacklist) throws Exception;

    int deductionCashback(OpayActiveCashback cashback);

    void updateCashback(OpayActiveCashback cashback);

    void saveReward(List<OpayMasterPupilAward> list);
}
