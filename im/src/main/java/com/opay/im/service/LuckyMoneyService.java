package com.opay.im.service;

import com.opay.im.model.LuckyMoneyModel;
import com.opay.im.model.request.GrabLuckyMoneyRequest;
import com.opay.im.model.request.LuckyMoneyRequest;
import com.opay.im.model.response.GrabLuckyMoneyResponse;
import com.opay.im.model.response.LuckyMoneyDetailResponse;
import com.opay.im.model.response.LuckyMoneyInfoResponse;
import com.opay.im.model.response.LuckyMoneyRecordResponse;
import com.opay.im.model.response.LuckyMoneyResponse;
import com.opay.im.model.response.opaycallback.OPayCallBackResponse;

import java.util.List;

public interface LuckyMoneyService {


    int deleteByPrimaryKey(Long id);

    int insert(LuckyMoneyModel record);

    int insertSelective(LuckyMoneyModel record);

    LuckyMoneyModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LuckyMoneyModel record);

    int updateByPrimaryKey(LuckyMoneyModel record);

    LuckyMoneyResponse sendLuckyMoney(LuckyMoneyRequest luckyMoneyRequest) throws Exception;

    GrabLuckyMoneyResponse grabLuckyMoney(GrabLuckyMoneyRequest grabLuckyMoneyRequest) throws Exception;

    LuckyMoneyDetailResponse selectLuckyMoneyEveryPerson(String opayId, Long id) throws Exception;

    int updatePayStatus(OPayCallBackResponse oPayCallBackResponse) throws Exception;

    int selectPayStatus(String opayId, String reference) throws Exception;

    LuckyMoneyModel selectLuckyMoneyByOpayId(Long id, String opayId) throws Exception;

    LuckyMoneyInfoResponse selectLuckyMoneyDetailByOpayId(Long id,String senderOpayId, String receivedOpayId) throws Exception;

    List<LuckyMoneyRecordResponse> selectLuckyMoneyView(Long id) throws Exception;
}
