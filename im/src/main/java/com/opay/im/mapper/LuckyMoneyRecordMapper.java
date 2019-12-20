package com.opay.im.mapper;

import com.opay.im.model.LuckyMoneyRecordModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface LuckyMoneyRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LuckyMoneyRecordModel record);

    int insertSelective(LuckyMoneyRecordModel record);

    LuckyMoneyRecordModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckyMoneyRecordModel record);

    int updateByPrimaryKey(LuckyMoneyRecordModel record);

    List<LuckyMoneyRecordModel> selectLuckyMoneyRecord(Long luckMoneyId);

    LuckyMoneyRecordModel selectLuckyMoneyRecordByOpayId(@Param("luckMoneyId") Long luckMoneyId, @Param("opayId") String opayId);

    List<LuckyMoneyRecordModel> selectListByCreateTime(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    void updateStatusAndRefundIdByLuckMoneyIdId(@Param("recordStatus") byte recordStatus, @Param("refundId") String refundId, @Param("luckMoneyId") Long luckMoneyId, @Param("version") Long version);
}