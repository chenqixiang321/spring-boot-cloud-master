package com.opay.invite.backstage.service.impl;

import com.opay.invite.backstage.service.OpayFeignApiService;
import com.opay.invite.backstage.service.dto.OpayApiRequest;
import com.opay.invite.backstage.service.dto.OpayApiResultResponse;
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

}
