package com.opay.invite.service;


import com.opay.invite.model.OpayUser;
import com.opay.invite.model.response.OpayApiResultResponse;


public interface OpayApiService{

    OpayApiResultResponse createOrder(String merchantId, String requestId, Object object, String aesKey, String iv) throws Exception;

    OpayApiResultResponse queryUserRecordByUserId(String merchantId, String requestId, Object object, String aesKey, String iv) throws Exception;

    OpayUser getOpayUser(String var1) throws Exception;
}
