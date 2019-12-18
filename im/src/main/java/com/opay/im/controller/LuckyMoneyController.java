package com.opay.im.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.im.common.SystemCode;
import com.opay.im.model.request.GrabLuckyMoneyRequest;
import com.opay.im.model.request.LuckyMoneyDetailRequest;
import com.opay.im.model.request.LuckyMoneyRequest;
import com.opay.im.model.response.GrabLuckyMoneyResponse;
import com.opay.im.model.response.LuckyMoneyInfoResponse;
import com.opay.im.model.response.LuckyMoneyPayStatusResponse;
import com.opay.im.model.response.LuckyMoneyRecordResponse;
import com.opay.im.model.response.LuckyMoneyResponse;
import com.opay.im.model.response.ResultResponse;
import com.opay.im.service.LuckyMoneyService;
import com.opay.im.service.RongCloudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/luckyMoney")
@Api(value = "红包API")
public class LuckyMoneyController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LuckyMoneyService luckyMoneyService;
    @Value("${config.opay.publickey}")
    private String publickey;

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
        LuckyMoneyResponse luckyMoneyResponse = luckyMoneyService.sendLuckyMoney(createLuckyMoney);
        luckyMoneyResponse.setPublicKey(publickey);
        return new ResultResponse(luckyMoneyResponse);
    }
    @Autowired
    private RongCloudService rongCloudService;
    @ApiOperation(value = "轮询红包支付状态", notes = "轮询红包支付状态 0:未支付 1:支付成功 2;失败 3:支付中")
    @GetMapping("{reference}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reference", value = "reference", required = true, paramType = "path", dataType = "String")
    })
    public ResultResponse<LuckyMoneyPayStatusResponse> getLuckyMoneyPayStatus(@PathVariable String reference) throws Exception {
        LuckyMoneyPayStatusResponse luckyMoneyPayStatusResponse = new LuckyMoneyPayStatusResponse(luckyMoneyService.selectPayStatus(String.valueOf(request.getAttribute("opayId")), reference));

//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> map = new HashMap<>();
//        map.put("sendUserId", "1");
//        map.put("sendNickName", "");
//        map.put("openUserId", "");
//        map.put("openNickName", "");
//        map.put("envelopId", "");
//        map.put("targetId", "UNG1000004001");
//        map.put("status", "0");
//        rongCloudService.sendMessage("UNG1000004001", "156619121267002134", mapper.writeValueAsString(map), mapper.writeValueAsString(map));
//        rongCloudService.sendMessage("156619121267002134", "UNG1000004001", mapper.writeValueAsString(map), mapper.writeValueAsString(map));

        return new ResultResponse(luckyMoneyPayStatusResponse);
    }

    @ApiOperation(value = "抢红包", notes = "抢红包")
    @PostMapping("/grab")
    public ResultResponse<GrabLuckyMoneyResponse> grabLuckyMoney(@RequestBody @Validated @ApiParam(name = "抢红包", value = "传入json格式", required = true) GrabLuckyMoneyRequest grabLuckyMoneyRequest) throws Exception {
        grabLuckyMoneyRequest.setCurrentOpayId(String.valueOf(request.getAttribute("opayId")));
        grabLuckyMoneyRequest.setCurrentOpayName(String.valueOf(request.getAttribute("opayName")));
        grabLuckyMoneyRequest.setCurrentPhone(String.valueOf(request.getAttribute("phoneNumber")));
        GrabLuckyMoneyResponse grabLuckyMoneyResponse = luckyMoneyService.grabLuckyMoney(grabLuckyMoneyRequest);
        if (grabLuckyMoneyResponse != null) {
            return new ResultResponse(grabLuckyMoneyResponse);
        } else {
            return new ResultResponse(SystemCode.LUCKY_MONEY_ERROR.getCode(), SystemCode.LUCKY_MONEY_ERROR.getMessage());
        }

    }

    @ApiOperation(value = "查看红包状态信息和详情", notes = "查看红包状态信息和详情")
    @PostMapping("/status")
    public ResultResponse<LuckyMoneyInfoResponse> getLuckyMoneyStatus(@RequestBody LuckyMoneyDetailRequest luckyMoneyDetailRequest) throws Exception {
        return new ResultResponse(luckyMoneyService.selectLuckyMoneyDetailByOpayId(luckyMoneyDetailRequest.getId(), luckyMoneyDetailRequest.getSenderId(), String.valueOf(request.getAttribute("opayId"))));
    }

    @ApiOperation(value = "查看红包每个人抢了多少钱信息", notes = "查看红包每个人抢了多少钱信息")
    @GetMapping("/view/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "红包ID", required = true, paramType = "path", dataType = "Long")
    })
    public ResultResponse<LuckyMoneyRecordResponse> getViewLuckyMoney(@PathVariable long id) throws Exception {
        return new ResultResponse(luckyMoneyService.selectLuckyMoneyView(id));
    }
}
