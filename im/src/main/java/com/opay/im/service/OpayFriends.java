package com.opay.im.service;

import com.opay.im.model.request.OpayApiRequest;
import com.opay.im.model.response.OpayApiResponse;
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
    OpayApiResponse batchQueryUserByPhone(@RequestBody OpayApiRequest opayApiRequest);

    @PostMapping({"api/im/queryUserListByPhone"})
    @Headers({"ContentType:application/json"})
    OpayApiResponse queryUserListByPhone(@RequestBody OpayApiRequest opayApiRequest);
}
