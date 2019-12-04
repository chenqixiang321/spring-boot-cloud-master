package com.opay.im.mapper;

import com.opay.im.model.LuckyMoneyRecordModel;
import org.apache.ibatis.annotations.Mapper;

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
}