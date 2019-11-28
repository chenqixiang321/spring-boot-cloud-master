package com.opay.invite.controller;

import com.opay.invite.model.response.LuckDrawInfoResponse;
import com.opay.invite.model.response.LuckDrawResponse;
import com.opay.invite.model.response.ResultResponse;
import com.opay.invite.model.response.SuccessResponse;
import com.opay.invite.service.LuckDrawInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/luckDraw")
@Api(value = "抽奖功能API")
public class LuckDrawController {

    @Autowired
    private LuckDrawInfoService luckDrawInfoService;
    @Autowired
    private com.opay.invite.service.InviteCountService InviteCountService;

    @ApiOperation(value = "获取抽奖信息", notes = "获取抽奖信息")
    @GetMapping("/info")
    public ResultResponse getLuckDrawInfo() {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setData(new LuckDrawResponse());
        return resultResponse;
    }

    @ApiOperation(value = "获取中奖者信息", notes = "获取中奖者信息")
    @GetMapping("/list")
    public ResultResponse getLuckDrawList() throws Exception {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setData(luckDrawInfoService.selectLuckDrawInfoList());
        return resultResponse;
    }

    @ApiOperation(value = "分享次数+1", notes = "分享次数+1")
    @PostMapping("/share")
    public SuccessResponse updateShareCount() throws Exception {
        boolean t = InviteCountService.updateShareCount("1", "1", "1");
        return new SuccessResponse();
    }

    @ApiOperation(value = "邀请次数+5", notes = "邀请次数+5")
    @PostMapping("/invite")
    public SuccessResponse updateInviteCount() throws Exception {
        boolean t = InviteCountService.updateInviteCount("1", "1", "1");
        return new SuccessResponse();
    }

    @ApiOperation(value = "抽奖", notes = "抽奖")
    @GetMapping
    public LuckDrawInfoResponse luckDraw() throws Exception {
        return luckDrawInfoService.getLuckDraw("1", "1", "1");
    }
}
