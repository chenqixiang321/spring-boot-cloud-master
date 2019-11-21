package com.opay.invite.controller;

import com.opay.invite.model.LuckDrawModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/luckDraw")
@Api(value = "抽奖功能API")
public class LuckDrawController {

    @ApiOperation(value = "获取抽奖信息", notes = "获取抽奖信息")
    @GetMapping("/info")
    public LuckDrawModel getGroupInfo() {
        return new LuckDrawModel();
    }
}
