package com.opay.im.service;

import com.opay.im.model.request.OpayApiRequest;
import com.opay.im.model.response.OpayApiResultResponse;
import com.opos.feign.factory.OpayFeignFactory;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "opayRedEnvelope",
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

    @PostMapping({"/api/im/createRedPacket"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse createRedPacket(@RequestBody OpayApiRequest opayApiRequest);

    @PostMapping({"/api/im/refundRedPacket"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse refundRedPacket(@RequestBody OpayApiRequest opayApiRequest);
}
