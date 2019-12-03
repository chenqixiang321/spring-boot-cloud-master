package com.opay.im.service;

import com.opay.im.model.request.BatchQueryUserRequest;
import com.opay.im.model.request.QueryUserRequest;
import com.opay.im.model.response.ResultResponse;
import com.opos.feign.factory.OpayFeignFactory;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "opayFriends",
        url = "${config.opay.domain}",
        fallbackFactory = OpayFeignFactory.class
)
public interface OpayFriends {
    @PostMapping({"api/im/batchQueryUserByPhone"})
    @Headers({"ContentType:application/json"})
    ResultResponse batchQueryUserByPhone(@RequestBody BatchQueryUserRequest batchQueryUserRequest);

    @PostMapping({"api/im/queryUserListByPhone"})
    @Headers({"ContentType:application/json"})
    ResultResponse queryUserListByPhone(@RequestBody QueryUserRequest queryUserRequest);
}
