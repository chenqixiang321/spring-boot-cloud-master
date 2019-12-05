package com.opay.invite.mapper;

import com.opay.invite.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface InviteMapper {

    int selectRelationCount(@Param("masterId") String masterId,@Param("pupilId")  String pupilId);

    OpayInviteRelation selectRelationMasterByMasterId(@Param("masterId") String masterId);

    void saveInviteRelation(OpayInviteRelation relation);

    void saveInviteReward(List<OpayMasterPupilAward> list);

    List<OpayInviteRelationVo> selectRelationByMasterId(@Param("masterId") String opayId);

    List<OpayInviteRankVo> getRankList();

    List<OpayMasterPupilAwardVo> getDetailList(@Param("opayId") String opayId ,@Param("startYmd") Integer startYmd, @Param("endYmd") Integer endYmd);

    int getRelationCount(@Param("opayId") String opayId);

    Integer getCurrentRelationCount(@Param("masterId") String masterId,@Param("pupilId")  String pupilId);

    List<OpayMasterPupilAwardVo> getTaskByOpayId(@Param("opayId") String opayId);

    OpayInviteRankVo getInviteInfoByOpayId(@Param("opayId") String opayId);

    OpayInviteRankVo getTotalRewardByDetail(@Param("opayId") String opayId);
}
