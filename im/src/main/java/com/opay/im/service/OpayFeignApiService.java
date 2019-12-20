package com.opay.im.service;

import com.alibaba.fastjson.JSONObject;
import com.opay.im.config.OpayFeignFactory;
import com.opay.im.model.LookupUser;
import com.opay.im.model.request.OpayApiRequest;
import com.opay.im.model.response.OpayApiResultResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "opayFeignApiService",
        url = "${config.opay.domain}",
        fallbackFactory = OpayFeignFactory.class
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

    @PostMapping({"/api/redEnvelopes/accept"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse acceptRedPacket(@RequestBody OpayApiRequest opayApiRequest);

    @PostMapping({"/api/redEnvelopes/chargeBack"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse refundRedPacket(@RequestBody OpayApiRequest opayApiRequest);

    @PostMapping({"/api/users/lookupCurrentUser"})
    @Headers({"ContentType:application/json"})
    JSONObject currentUser(@RequestHeader("Authorization") String var1);

    @PostMapping({"api/users/queryUserInfoByPhone"})
    @Headers({"ContentType:application/json"})
    JSONObject queryUserInfoByPhone(@RequestBody LookupUser var1);
}
