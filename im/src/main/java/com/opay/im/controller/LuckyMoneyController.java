package com.opay.im.controller;

import com.opay.im.common.SystemCode;
import com.opay.im.model.LuckyMoneyModel;
import com.opay.im.model.request.GrabLuckyMoneyRequest;
import com.opay.im.model.request.LuckyMoneyRequest;
import com.opay.im.model.response.GrabLuckyMoneyResponse;
import com.opay.im.model.response.LuckyMoneyResponse;
import com.opay.im.model.response.ResultResponse;
import com.opay.im.model.response.SuccessResponse;
import com.opay.im.service.LuckyMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/luckyMoney")
@Api(value = "红包API")
public class LuckyMoneyController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LuckyMoneyService luckyMoneyService;

    @ApiOperation(value = "生成红包", notes = "生成红包")
    @PostMapping
    public ResultResponse<LuckyMoneyResponse> createLuckyMoney(@RequestBody @Validated @ApiParam(name = "生成红包", value = "传入json格式", required = true) LuckyMoneyRequest createLuckyMoney) throws Exception {
        createLuckyMoney.setOpayId(String.valueOf(request.getAttribute("opayId")));
        createLuckyMoney.setOpayName(String.valueOf(request.getAttribute("opayName")));
        createLuckyMoney.setOpayPhone(String.valueOf(request.getAttribute("phoneNumber")));
        if (createLuckyMoney.getTargetType() == 0) {
            if (createLuckyMoney.getTargetId().equals(createLuckyMoney.getOpayId())) {
                throw new Exception("The target cannot be yourself");
            }
        }
        return new ResultResponse(luckyMoneyService.sendLuckyMoney(createLuckyMoney));
    }

    @ApiOperation(value = "轮询红包支付状态", notes = "轮询红包支付状态 0:未支付 1:支付成功 2;失败 3:支付中")
    @GetMapping("{reference}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reference", value = "reference", required = true, paramType = "path", dataType = "String")
    })
    public ResultResponse<LuckyMoneyResponse> getLuckyMoneyPayStatus(@PathVariable String reference) throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("status", luckyMoneyService.selectPayStatus(String.valueOf(request.getAttribute("opayId")), reference));
        return new ResultResponse(map);
    }

    @ApiOperation(value = "抢红包", notes = "抢红包")
    @PostMapping("/grab")
    public ResultResponse grabLuckyMoney(@RequestBody @Validated @ApiParam(name = "抢红包", value = "传入json格式", required = true) GrabLuckyMoneyRequest grabLuckyMoneyRequest) throws Exception {
        if (grabLuckyMoneyRequest.getTargetId() == null) {
            grabLuckyMoneyRequest.setOpayId(String.valueOf(request.getAttribute("opayId")));
        }
        GrabLuckyMoneyResponse grabLuckyMoneyResponse = luckyMoneyService.grabLuckyMoney(grabLuckyMoneyRequest);
        if (grabLuckyMoneyResponse != null) {
            return new ResultResponse(grabLuckyMoneyResponse);
        } else {
            return new ResultResponse(SystemCode.LUCKY_MONEY_ERROR.getCode(), SystemCode.LUCKY_MONEY_ERROR.getMessage());
        }

    }

    @ApiOperation(value = "查看红包信息", notes = "每个人抢了多少钱")
    @GetMapping("/info/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "红包ID", required = true, paramType = "path", dataType = "Long")
    })
    public ResultResponse getLuckyMoney(@PathVariable long id) throws Exception {
        return new ResultResponse(luckyMoneyService.selectLuckyMoneyEveryPerson(id));
    }
}
