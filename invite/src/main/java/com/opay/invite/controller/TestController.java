package com.opay.invite.controller;

import com.opay.invite.resp.Result;
import com.opay.invite.stateconfig.RewardConfig;
import com.opay.invite.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/test")
@Api(value = "测试接口")
public class TestController {

    @Autowired
    private RewardConfig rewardConfig;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "test", notes = "test")
    @GetMapping("/test")
    public Result test(HttpServletRequest request) throws Exception {
        Integer cashBackTotalAmount = redisUtil.get("invite_active_", "cashBackTotalAmount");
        log.info("RewardJob cashBackTotalAmount:{}",cashBackTotalAmount);
        if(cashBackTotalAmount == null || cashBackTotalAmount < 0 ){
            log.warn("RewardJob 活动已结束 开关已关 cashBackTotalAmount:{}",cashBackTotalAmount);
        }
        return Result.success();
    }






}
