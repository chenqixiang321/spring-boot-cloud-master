package com.opay.invite.controller;


import com.opay.invite.model.InviteModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/config")
@Api(value = "获取配置API")
public class ConfigController {


    @ApiOperation(value = "获取阶梯配置列表", notes = "获取阶梯配置列表")
    @PostMapping("/stage")
    public InviteModel getList(HttpServletRequest request) {

        //获取列表配置


        return new InviteModel();
    }



}
