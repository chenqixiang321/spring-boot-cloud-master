package com.opay.im.controller;

import com.opay.im.model.response.BlackListUserIdsResponse;
import com.opay.im.model.response.ResultResponse;
import com.opay.im.model.response.SuccessResponse;
import com.opay.im.service.UserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @ApiOperation(value = "添加用户到黑名单", notes = "添加用户到黑名单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blackUserId", value = "用户ID", required = true, paramType = "path", dataType = "String")
    })
    @PostMapping("/black/add/{blackUserId}")
    public SuccessResponse addBlackList(@PathVariable String blackUserId) throws Exception {
        String userId = String.valueOf(request.getAttribute("opayId"));
        userTokenService.addBlackList(userId, blackUserId);
        return new SuccessResponse();
    }

    @ApiOperation(value = "把用户从黑名单移除", notes = "把用户从黑名单移除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blackUserId", value = "用户ID", required = true, paramType = "path", dataType = "String")
    })
    @PostMapping("/black/remove/{blackUserId}")
    public SuccessResponse removeBlackList(@PathVariable String blackUserId) throws Exception {
        String userId = String.valueOf(request.getAttribute("opayId"));
        userTokenService.removeBlackList(userId, blackUserId);
        return new SuccessResponse();
    }

    @ApiOperation(value = "获取用户的黑名单列表", notes = "获取用户的黑名单列表")
    @PostMapping("/black/get")
    public ResultResponse<BlackListUserIdsResponse> getBlackList() throws Exception {
        String userId = String.valueOf(request.getAttribute("opayId"));
        return new ResultResponse<>(userTokenService.getBlackList(userId));
    }
}
