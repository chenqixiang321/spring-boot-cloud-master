package com.opay.im.service;

import com.opay.im.model.LuckyMoneyRecordModel;
import com.opay.im.model.response.opaycallback.OPayCallBackResponse;

public interface LuckyMoneyRecordService {


    int deleteByPrimaryKey(Long id);

    int insert(LuckyMoneyRecordModel record);

    int insertSelective(LuckyMoneyRecordModel record);

    LuckyMoneyRecordModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckyMoneyRecordModel record);

    int updateByPrimaryKey(LuckyMoneyRecordModel record);

    int updateGetStatus(Long luckyMoneyId, Long luckyMoneyRecordId, OPayCallBackResponse oPayCallBackResponse) throws Exception;
}
