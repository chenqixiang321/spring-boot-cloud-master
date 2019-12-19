package com.opay.im.service;

import com.opay.im.model.response.OpayApiResultResponse;

public interface OpayApiService {

    OpayApiResultResponse acceptRedPacket(String merchantId, String requestId, Object object, String aesKey, String iv) throws Exception;

    OpayApiResultResponse refundRedPacket(String merchantId, String requestId, Object object, String aesKey, String iv) throws Exception;
}
