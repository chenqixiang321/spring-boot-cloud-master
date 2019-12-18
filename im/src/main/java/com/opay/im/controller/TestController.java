package com.opay.im.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.im.model.response.LuckyMoneyPayStatusResponse;
import com.opay.im.model.response.ResultResponse;
import com.opay.im.service.RongCloudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/test")
@Api(value = "test")
public class TestController {

    @Autowired
    private RongCloudService rongCloudService;
    @ApiOperation(value = "轮询红包支付状态", notes = "轮询红包支付状态 0:未支付 1:支付成功 2;失败 3:支付中")
    @GetMapping("{reference}")

    public void getLuckyMoneyPayStatus(@PathVariable String reference) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("sendUserId", "1");
        map.put("sendNickName", "");
        map.put("openUserId", "");
        map.put("openNickName", "");
        map.put("envelopId", "");
        map.put("targetId", "UNG1000004001");
        map.put("status", "0");
        rongCloudService.sendMessage("UNG1000004001", "156619121267002134", mapper.writeValueAsString(map), mapper.writeValueAsString(map));
        rongCloudService.sendMessage("156619121267002134", "UNG1000004001", mapper.writeValueAsString(map), mapper.writeValueAsString(map));
    }
}
