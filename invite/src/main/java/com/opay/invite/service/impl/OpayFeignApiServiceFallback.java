package com.opay.invite.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.opay.invite.model.request.OpayApiRequest;
import com.opay.invite.model.response.OpayApiResultResponse;
import com.opay.invite.service.OpayFeignApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OpayFeignApiServiceFallback implements OpayFeignApiService {

    @Override
    public OpayApiResultResponse createOrder(OpayApiRequest opayApiRequest) {
        log.error("OpayFeignApiServiceFallback.createOrder execute param:{}", opayApiRequest.toString());
        return null;
    }

    @Override
    public OpayApiResultResponse queryUserRecordByUserId(OpayApiRequest opayApiRequest) {
        log.error("OpayFeignApiServiceFallback.queryUserRecordByUserId execute param:{}", opayApiRequest.toString());
        return null;
    }

    @Override
    public OpayApiResultResponse createRedPacket(OpayApiRequest opayApiRequest) {
        log.error("OpayFeignApiServiceFallback.createRedPacket execute param:{}", opayApiRequest.toString());
        return null;
    }

    @Override
    public OpayApiResultResponse refundRedPacket(OpayApiRequest opayApiRequest) {
        log.error("OpayFeignApiServiceFallback.refundRedPacket execute param:{}", opayApiRequest.toString());
        return null;
    }

    @Override
    public JSONObject currentUser(String var1) {
        log.error("OpayFeignApiServiceFallback.currentUser execute param:{}", var1);
        return null;
    }
}
