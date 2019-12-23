package com.opay.invite.backstage.service;

import com.opay.invite.backstage.service.dto.OpayApiRequest;
import com.opay.invite.backstage.service.dto.OpayApiResultResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "opayFeignApiService",
        url = "${config.opay.domain}"//,
       // fallbackFactory = OpayFeignFactory.class
)
public interface OpayFeignApiService {
    @PostMapping({"api/im/batchQueryUserByPhone"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse batchQueryUserByPhone(@RequestBody OpayApiRequest opayApiRequest);

    @PostMapping({"api/im/queryUserListByPhone"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse queryUserListByPhone(@RequestBody OpayApiRequest opayApiRequest);

    @PostMapping({"api/users/queryUserInfoByPhone"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse queryUserInfoByPhone(@RequestBody OpayApiRequest opayApiRequest);

}
