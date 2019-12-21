package com.opay.invite.service;


import com.alibaba.fastjson.JSONObject;
import com.opay.invite.config.OpayFeignFactory;
import com.opay.invite.model.request.OpayApiRequest;
import com.opay.invite.model.response.OpayApiResultResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "opayFeignApiService",
        url = "${transfer.opay.domain}",
        fallbackFactory = OpayFeignFactory.class
)
public interface OpayFeignApiService {
    @PostMapping({"/api/m/createOrder"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse createOrder(@RequestBody OpayApiRequest opayApiRequest);

    @PostMapping({"/api/im/queryUserRecordByUserId"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse queryUserRecordByUserId(@RequestBody OpayApiRequest opayApiRequest);

    @PostMapping({"/api/im/createRedPacket"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse createRedPacket(@RequestBody OpayApiRequest opayApiRequest);

    @PostMapping({"/api/im/refundRedPacket"})
    @Headers({"ContentType:application/json"})
    OpayApiResultResponse refundRedPacket(@RequestBody OpayApiRequest opayApiRequest);

    @PostMapping({"/api/users/lookupCurrentUser"})
    @Headers({"ContentType:application/json"})
    JSONObject currentUser(@RequestHeader("Authorization") String var1);

}
