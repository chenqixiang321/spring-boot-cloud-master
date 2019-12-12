package com.opay.invite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.invite.model.request.OpayApiRequest;
import com.opay.invite.model.response.OpayApiResultResponse;
import com.opay.invite.utils.AESUtil;


public abstract class AbstractOpayApi {

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
}
