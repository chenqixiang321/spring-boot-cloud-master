package com.opay.im.controller;

import com.opay.im.model.request.GetRongCloudTokenRequest;
import com.opay.im.model.request.InviteGroupRequest;
import com.opay.im.model.response.ResultResponse;
import com.opay.im.service.UserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user")
@Api(value = "用户融云登录功能API")
public class UserController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserTokenService userTokenService;

    @ApiOperation(value = "获得融云token", notes = "从数据库里获取token,不存在则创建")
    @PostMapping("/token")
    public ResultResponse getToken() throws Exception {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(200);
        resultResponse.setMessage("success");
        Map<String, String> token = new HashMap<>();
        token.put("token", userTokenService.getRyToken(String.valueOf(request.getAttribute("opayId")), String.valueOf(request.getAttribute("phoneNumber"))));
        resultResponse.setData(token);
        return resultResponse;
    }
}
