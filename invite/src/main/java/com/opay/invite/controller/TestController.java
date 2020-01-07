package com.opay.invite.controller;

import com.opay.invite.resp.Result;
import com.opay.invite.service.ActiveService;
import com.opay.invite.stateconfig.RewardConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/test")
@Api(value = "测试接口")
public class TestController {

    @Autowired
    private ActiveService activeService;

    @Autowired
    private RewardConfig rewardConfig;

    @ApiOperation(value = "活动限额锁定", notes = "活动限额锁定")
    @PostMapping("/activeLimitUnLock")
    public Result activeLimitUnLock(HttpServletRequest request) throws Exception {
        activeService.lockActive(rewardConfig.getActiveId());
        return Result.success();
    }



}
