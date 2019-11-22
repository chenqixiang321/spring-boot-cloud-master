package com.opay.invite.controller;

import com.opay.invite.model.InviteModel;
import com.opay.invite.model.InviteRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/invite")
@Api(value = "邀请API")
public class InviteController {

    @ApiOperation(value = "生成邀请码", notes = "生成邀请码")
    @PostMapping("/getInviteCode")
    public InviteModel getInviteCode(HttpServletRequest request) {
        //更登录用户生成code

        //该code的有效期,存入redis

        return new InviteModel();
    }


    @ApiOperation(value = "插入邀请关系信息", notes = "插入邀请关系信息")
    @PostMapping("/save")
    public InviteModel save(HttpServletRequest request, @RequestBody InviteRequest inviteRequest) {
        //判断邀请码是否合法,和反作弊，不能建立师徒关系

        //解析登录用户ID和邀请码用户ID


        //校验用户是否已经建立关系，或是不能互相邀请关系

        // 建立关系，增加金额奖励,同时充入账户，需要判断角色和用户邀请人数所属阶梯，奖励不同

        return new InviteModel();
    }


    @ApiOperation(value = "获取当前登录人邀请列表", notes = "获取当前登录人邀请列表")
    @PostMapping("/list")
    public InviteModel getList(HttpServletRequest request,@RequestBody InviteRequest inviteRequest) {
        //获取列表关系列表和奖励金额

        //调用opay 获取用户头像信息

        return new InviteModel();
    }


    @ApiOperation(value = "获取历史用户前30名数据", notes = "获取历史用户前30名数据")
    @PostMapping("/rank")
    public InviteModel getRankList(HttpServletRequest request) {

        //获取列表,名称和奖励金额，邀请人数


        return new InviteModel();
    }

}
