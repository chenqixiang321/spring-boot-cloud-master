package com.opay.im.controller;

import com.opay.im.model.LuckyMoneyModel;
import com.opay.im.model.request.CreateLuckyMoneyRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/luckyMoney")
@Api(value = "红包API")
public class LuckyMoneyController {

    @ApiOperation(value = "获取红包信息", notes = "获取红包信息")
    @GetMapping("{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "红包ID", required = true, paramType = "path", dataType = "Long")
    })
    public LuckyMoneyModel getGroupInfo(@PathVariable Long id) {
        return new LuckyMoneyModel();
    }

    @ApiOperation(value = "生成红包", notes = "生成红包")
    @PostMapping
    public CreateLuckyMoneyRequest createLuckyMoney(@RequestBody @ApiParam(name = "生成红包", value = "传入json格式", required = true) CreateLuckyMoneyRequest createLuckyMoney) {
        return createLuckyMoney;
    }
}
