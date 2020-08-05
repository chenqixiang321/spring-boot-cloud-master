package com.opay.invite.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.invite.model.OpayUser;
import com.opay.invite.model.request.OpayApiRequest;
import com.opay.invite.model.response.OpayApiOrderResultResponse;
import com.opay.invite.model.response.OpayApiResultResponse;
import com.opay.invite.model.response.OpayApiUserOrderResponse;
import com.opay.invite.service.OpayApiService;
import com.opay.invite.service.OpayFeignApiService;
import com.opay.invite.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

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
        String result= opayApiResultResponseHandler(feiginResponse,aesKey,iv);
        log.info("createOrder reuslt:{}", JSON.toJSONString(feiginResponse));
        OpayApiOrderResultResponse opayApiOrderResult = (OpayApiOrderResultResponse) mapperToClassObject(result, OpayApiOrderResultResponse.class);
        feiginResponse.setData(opayApiOrderResult);
        return feiginResponse;
    }

    @Override
    public OpayApiResultResponse queryUserRecordByUserId(String merchantId, String requestId, Object object, String aesKey, String iv) throws Exception {
        OpayApiRequest request =getOpayApiRequest(merchantId,requestId,object,aesKey,iv);
        log.info("queryUserRecordByUserId param:{}", JSON.toJSONString(request));
        OpayApiResultResponse feiginResponse= opayFeignApiService.queryUserRecordByUserId(request);
        log.info("queryUserRecordByUserId reuslt:{}", JSON.toJSONString(feiginResponse));
        String result= opayApiResultResponseHandler(feiginResponse,aesKey,iv);
        OpayApiUserOrderResponse opayApiOrderResult = (OpayApiUserOrderResponse) mapperToClassObject(result, OpayApiUserOrderResponse.class);
        log.info("queryUserRecordByUserId opayApiOrderResult:{}", JSON.toJSONString(feiginResponse));
        feiginResponse.setData(opayApiOrderResult);
        return feiginResponse;
    }

    @Override
    public OpayUser getOpayUser(String token) throws Exception {
        JSONObject jsonObject = opayFeignApiService.currentUser(token);
        if (null == jsonObject) {
            log.error("call opay info error:{}", jsonObject);
            throw new Exception("you need to be authenticated");
        } else if (jsonObject.get("code") == null) {
            log.error("call opay info error:{}", jsonObject);
            throw new Exception("you need to be authenticated");
        } else if (!"00000".equals(jsonObject.get("code"))) {
            log.error("call opay info error:{}", jsonObject);
            throw new Exception("you need to be authenticated");
        } else {
            JSONObject opayUserJson = jsonObject.getJSONObject("data");
            if (opayUserJson == null) {
                log.error("get opay user error:{}", jsonObject);
                throw new Exception("you need to be authenticated");
            } else {
                OpayUser opayUser = (OpayUser)opayUserJson.toJavaObject(OpayUser.class);
                if (StringUtils.isEmpty(opayUser.getPhoneNumber())) {
                    log.error("get opay user phonenumber error:{}", jsonObject);
                    throw new Exception("get a invalid phonenumber");
                } else {
                    opayUser.setPhoneNumber(opayUser.getPhoneNumber().replace("+234", ""));
                    return opayUser;
                }
            }
        }
    }
}
