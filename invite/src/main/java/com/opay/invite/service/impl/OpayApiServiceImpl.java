package com.opay.invite.service.impl;

import com.alibaba.fastjson.JSON;
import com.opay.invite.model.request.OpayApiRequest;
import com.opay.invite.model.response.OpayApiOrderResultResponse;
import com.opay.invite.model.response.OpayApiResultResponse;
import com.opay.invite.model.response.OpayApiUserOrderResponse;
import com.opay.invite.service.AbstractOpayApi;
import com.opay.invite.service.OpayApiService;
import com.opay.invite.service.OpayFeignApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpayApiServiceImpl extends AbstractOpayApi implements OpayApiService {

    @Autowired
    private OpayFeignApiService opayFeignApiService;


    @Override
    public OpayApiResultResponse createOrder(String merchantId, String requestId, Object object,String aesKey,String iv) throws Exception {
        log.info("createOrder param:{}", JSON.toJSONString(object));
        OpayApiRequest request =getOpayApiRequest(merchantId,requestId,object,aesKey,iv);
        OpayApiResultResponse feiginResponse= opayFeignApiService.createOrder(request);
        String result= opayApiResultResponseHandler(feiginResponse,aesKey,iv);
        log.info("createOrder reuslt:{}", JSON.toJSONString(result));
        OpayApiOrderResultResponse opayApiOrderResult = (OpayApiOrderResultResponse) mapperToClassObject(result, OpayApiOrderResultResponse.class);
        feiginResponse.setData(opayApiOrderResult);
        return feiginResponse;
    }

    @Override
    public OpayApiResultResponse queryUserRecordByUserId(String merchantId, String requestId, Object object, String aesKey, String iv) throws Exception {
        log.debug("queryUserRecordByUserId param:{}", JSON.toJSONString(object));
        OpayApiRequest request =getOpayApiRequest(merchantId,requestId,object,aesKey,iv);
        OpayApiResultResponse feiginResponse= opayFeignApiService.queryUserRecordByUserId(request);
        String result= opayApiResultResponseHandler(feiginResponse,aesKey,iv);
        log.debug("queryUserRecordByUserId reuslt:{}", JSON.toJSONString(result));
        OpayApiUserOrderResponse opayApiOrderResult = (OpayApiUserOrderResponse) mapperToClassObject(result, OpayApiUserOrderResponse.class);
        feiginResponse.setData(opayApiOrderResult);
        return feiginResponse;
    }
}
