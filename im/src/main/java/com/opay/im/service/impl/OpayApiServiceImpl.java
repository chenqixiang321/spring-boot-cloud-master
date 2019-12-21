package com.opay.im.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.im.model.OpayUser;
import com.opay.im.model.request.OpayApiRequest;
import com.opay.im.model.response.OpayApiResultResponse;
import com.opay.im.service.OpayApiService;
import com.opay.im.service.OpayFeignApiService;
import com.opay.im.utils.AESUtil;
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

        // String result = opayApiResultResponseHandler(feiginResponse, aesKey, iv);

        String result = AESUtil.decrypt((String) feiginResponse.getData(), aesKey, iv);

        log.info("refundRedPacket reuslt:{}", JSON.toJSONString(feiginResponse));
        Map opayApiOrderResult = (Map) mapperToClassObject(result, Map.class);
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

    protected String parseToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        } else {
            String[] values = token.split(" ");
            if (values.length == 2 && "Bearer".equals(values[0])) {
                token = values[1];
                if (token.split("\\.").length != 3) {
                    log.warn("invalid token :{} ", token);
                    return null;
                } else {
                    return token;
                }
            } else {
                log.warn("invalid token :{} ", token);
                return null;
            }
        }
    }
}
