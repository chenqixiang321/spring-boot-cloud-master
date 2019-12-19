package com.opay.im.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.im.model.request.OpayApiRequest;
import com.opay.im.model.response.OpayApiResultResponse;
import com.opay.im.service.OpayApiService;
import com.opay.im.service.OpayFeignApiService;
import com.opay.im.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    protected <T> Object mapperToClassObject(String result, Class<T> tClass) throws JsonProcessingException {
        if (result == null || "".equals(result)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(result, tClass);
    }

    @Override
    public OpayApiResultResponse acceptRedPacket(String merchantId, String requestId, Object object, String aesKey, String iv) throws Exception {
        log.info("createRedPacket param:{}", JSON.toJSONString(object));
        OpayApiRequest request = getOpayApiRequest(merchantId, requestId, object, aesKey, iv);
        OpayApiResultResponse feiginResponse = opayFeignApiService.acceptRedPacket(request);
        String result = opayApiResultResponseHandler(feiginResponse, aesKey, iv);
        Map opayApiOrderResult = (Map) mapperToClassObject(result, Map.class);
        log.info("createRedPacket reuslt:{}", JSON.toJSONString(feiginResponse));
        feiginResponse.setData(opayApiOrderResult);
        return feiginResponse;
    }

    @Override
    public OpayApiResultResponse refundRedPacket(String merchantId, String requestId, Object object, String aesKey, String iv) throws Exception {
        log.info("refundRedPacket param:{}", JSON.toJSONString(object));
        OpayApiRequest request = getOpayApiRequest(merchantId, requestId, object, aesKey, iv);
        OpayApiResultResponse feiginResponse = opayFeignApiService.refundRedPacket(request);
        String result = opayApiResultResponseHandler(feiginResponse, aesKey, iv);
        log.info("refundRedPacket reuslt:{}", JSON.toJSONString(feiginResponse));
        Map opayApiOrderResult = (Map) mapperToClassObject(result, Map.class);
        feiginResponse.setData(opayApiOrderResult);
        return feiginResponse;
    }
}
