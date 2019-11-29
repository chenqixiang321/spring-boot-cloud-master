package com.opay.im.service;

import com.opay.im.model.LuckyMoneyRecordModel;
public interface LuckyMoneyRecordService{


    int deleteByPrimaryKey(Long id);

    int insert(LuckyMoneyRecordModel record);

    int insertSelective(LuckyMoneyRecordModel record);

    LuckyMoneyRecordModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckyMoneyRecordModel record);

    int updateByPrimaryKey(LuckyMoneyRecordModel record);

}
