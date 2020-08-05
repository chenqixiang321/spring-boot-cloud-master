package com.opay.im.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.opay.im.model.LookupUser;
import com.opay.im.model.request.OpayApiRequest;
import com.opay.im.model.response.OpayApiResultResponse;
import com.opay.im.service.OpayFeignApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OpayFeignApiServiceFallback implements OpayFeignApiService {
    @Override
    public OpayApiResultResponse batchQueryUserByPhone(OpayApiRequest opayApiRequest) {
        log.error("OpayFeignFallback.batchQueryUserByPhone execute param:{}", opayApiRequest.toString());
        return null;
    }

    @Override
    public OpayApiResultResponse queryUserListByPhone(OpayApiRequest opayApiRequest) {
        log.error("OpayFeignFallback.queryUserListByPhone execute param:{}", opayApiRequest.toString());
        return null;
    }

    @Override
    public OpayApiResultResponse queryUserInfoByPhone(OpayApiRequest opayApiRequest) {
        log.error("OpayFeignFallback.queryUserInfoByPhone execute param:{}", opayApiRequest.toString());
        return null;
    }

    @Override
    public OpayApiResultResponse acceptRedPacket(OpayApiRequest opayApiRequest) {
        log.error("OpayFeignFallback.acceptRedPacket execute param:{}", opayApiRequest.toString());
        return null;
    }

    @Override
    public OpayApiResultResponse refundRedPacket(OpayApiRequest opayApiRequest) {
        log.error("OpayFeignFallback.refundRedPacket execute param:{}", opayApiRequest.toString());
        return null;
    }

    @Override
    public JSONObject currentUser(String var1) {
        log.error("OpayFeignFallback.currentUser execute param:{}", var1);
        return null;
    }

    @Override
    public JSONObject queryUserInfoByPhone(LookupUser var1) {
        log.error("OpayFeignFallback.queryUserInfoByPhone execute param:{}", var1.toString());
        return null;
    }
}
