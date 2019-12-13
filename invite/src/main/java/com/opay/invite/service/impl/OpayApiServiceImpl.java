package com.opay.invite.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.invite.model.request.OpayApiRequest;
import com.opay.invite.model.response.OpayApiOrderResultResponse;
import com.opay.invite.model.response.OpayApiResultResponse;
import com.opay.invite.model.response.OpayApiUserOrderResponse;
import com.opay.invite.service.AbstractOpayApi;
import com.opay.invite.service.OpayApiService;
import com.opay.invite.service.OpayFeignApiService;
import com.opay.invite.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OpayApiServiceImpl implements OpayApiService {

    @Autowired
    private OpayFeignApiService opayFeignApiService;

    protected OpayApiRequest getOpayApiRequest(String merchantId, String requestId, Object object, String aesKey, String iv) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OpayApiRequest batchQuery = new OpayApiRequest();
        batchQuery.setMerchantId(merchantId);
        batchQuery.setRequestId(requestId);
        batchQuery.setData(AESUtil.encrypt(mapper.writeValueAsString(object), aesKey, iv));
        return batchQuery;
    }
    protected String opayApiResultResponseHandler(OpayApiResultResponse<String> opayApiResultResponse, String aesKey, String iv) throws Exception {
        if (!"00000".equals(opayApiResultResponse.getCode())) {
            return null;
        }
        return AESUtil.decrypt(opayApiResultResponse.getData(), aesKey, iv);
    }

    protected <T> Object mapperToClassObject(String result,Class<T> tClass) throws JsonProcessingException {
        if(result==null || "".equals(result)){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(result,tClass);
    }

    @Override
    public OpayApiResultResponse createOrder(String merchantId, String requestId, Object object,String aesKey,String iv) throws Exception {
        log.info("createOrder param:{}", JSON.toJSONString(object));
        OpayApiRequest request =getOpayApiRequest(merchantId,requestId,object,aesKey,iv);
        OpayApiResultResponse feiginResponse= opayFeignApiService.createOrder(request);
        log.info("createOrder reuslt:{}", JSON.toJSONString(feiginResponse));
        String result= opayApiResultResponseHandler(feiginResponse,aesKey,iv);
        OpayApiOrderResultResponse opayApiOrderResult = (OpayApiOrderResultResponse) mapperToClassObject(result, OpayApiOrderResultResponse.class);
        feiginResponse.setData(opayApiOrderResult);
        return feiginResponse;
    }

    @Override
    public OpayApiResultResponse queryUserRecordByUserId(String merchantId, String requestId, Object object, String aesKey, String iv) throws Exception {
        log.debug("queryUserRecordByUserId param:{}", JSON.toJSONString(object));
        OpayApiRequest request =getOpayApiRequest(merchantId,requestId,object,aesKey,iv);
        OpayApiResultResponse feiginResponse= opayFeignApiService.queryUserRecordByUserId(request);
        log.debug("queryUserRecordByUserId reuslt:{}", JSON.toJSONString(feiginResponse));
        String result= opayApiResultResponseHandler(feiginResponse,aesKey,iv);
        OpayApiUserOrderResponse opayApiOrderResult = (OpayApiUserOrderResponse) mapperToClassObject(result, OpayApiUserOrderResponse.class);
        feiginResponse.setData(opayApiOrderResult);
        return feiginResponse;
    }
}
