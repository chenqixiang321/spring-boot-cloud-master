package com.opay.im.service;

import com.opay.im.model.LuckyMoneyModel;
import com.opay.im.model.request.LuckyMoneyRequest;
import com.opay.im.model.response.LuckyMoneyInfoResponse;
import com.opay.im.model.response.LuckyMoneyResponse;

public interface LuckyMoneyService {


    int deleteByPrimaryKey(Long id);

    int insert(LuckyMoneyModel record);

    int insertSelective(LuckyMoneyModel record);

    LuckyMoneyModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckyMoneyModel record);

    int updateByPrimaryKey(LuckyMoneyModel record);

    LuckyMoneyResponse sendLuckyMoney(LuckyMoneyRequest luckyMoneyRequest) throws Exception;

    LuckyMoneyInfoResponse selectLuckyMoneyEveryPerson(Long id) throws Exception;

}
