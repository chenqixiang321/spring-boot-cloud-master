package com.opay.im.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.im.model.OpayUserModel;
import com.opay.im.model.request.BatchQueryUserRequest;
import com.opay.im.model.request.GetRongCloudTokenRequest;
import com.opay.im.model.request.OpayApiRequest;
import com.opay.im.model.request.OpayFriendsRequest;
import com.opay.im.model.request.QueryUserRequest;
import com.opay.im.model.response.BlackListUserIdsResponse;
import com.opay.im.model.response.OpayApiQueryUserByPhoneResponse;
import com.opay.im.model.response.OpayApiResponse;
import com.opay.im.model.response.OpayApiResultResponse;
import com.opay.im.model.response.ResultResponse;
import com.opay.im.model.response.SuccessResponse;
import com.opay.im.service.IncrKeyService;
import com.opay.im.service.UserTokenService;
import com.opay.im.utils.AESUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/user")
@Api(value = "用户融云登录功能API")
public class UserController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private com.opay.im.service.OpayFriends opayFriends;
    @Autowired
    private IncrKeyService incrKeyService;
    @Value("${config.opay.aesKey}")
    private String aesKey;
    @Value("${config.opay.iv}")
    private String iv;
    @Value("${config.opay.merchantId}")
    private String merchantId;

    @ApiOperation(value = "获得融云token/激活融云", notes = "从数据库里获取token,不存在则创建,传OpayId和phone则激活该opay账户的融云,不传激活当前登录用户的")
    @PostMapping("/token")
    public ResultResponse getToken(@RequestBody GetRongCloudTokenRequest getRongCloudTokenRequest) throws Exception {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(200);
        resultResponse.setMessage("success");
        Map<String, String> token = new HashMap<>();
        if (StringUtils.isNotBlank(getRongCloudTokenRequest.getOpayId())) {
            userTokenService.getRyToken(getRongCloudTokenRequest.getOpayId(), getRongCloudTokenRequest.getPhone());
        } else {
            token.put("token", userTokenService.getRyToken(String.valueOf(request.getAttribute("opayId")), String.valueOf(request.getAttribute("phoneNumber"))));
        }
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

    @ApiOperation(value = "根据通讯录获取opay好友", notes = "根据通讯录获取opay好友")
    @PostMapping("/friends")
    public ResultResponse<List<OpayUserModel>> getFriends(@RequestBody OpayFriendsRequest opayFriendsRequest) throws Exception {
        mobileHandler(opayFriendsRequest);
        List<OpayUserModel> resultList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        BatchQueryUserRequest batchQueryUserRequest = new BatchQueryUserRequest();
        batchQueryUserRequest.setMobile(String.join(",", opayFriendsRequest.getMobiles()));
        OpayApiRequest batchQuery = new OpayApiRequest();
        batchQuery.setMerchantId(merchantId);
        batchQuery.setRequestId(incrKeyService.getIncrKey());
        batchQuery.setData(AESUtil.encrypt(mapper.writeValueAsString(batchQueryUserRequest), aesKey, iv));
        OpayApiResponse resultResponse = opayFriends.batchQueryUserByPhone(batchQuery);
        if (resultResponse == null || resultResponse.getData() == null) {
            return new ResultResponse(resultList);
        }
        String json = AESUtil.decrypt(resultResponse.getData(), aesKey, iv);
        OpayApiQueryUserByPhoneResponse queryUserByPhoneResponse = mapper.readValue(json, OpayApiQueryUserByPhoneResponse.class);
        Map<String, OpayUserModel> userMap = new HashMap<>();
        for (OpayUserModel user : queryUserByPhoneResponse.getUsers()) {
            userMap.put(user.getUserId(), user);
        }
        if (opayFriendsRequest.isLoadTradePhone()) {
            String phoneNumber = String.valueOf(request.getAttribute("phoneNumber"));
            QueryUserRequest queryUserRequest = new QueryUserRequest();
            queryUserRequest.setMobile(phoneNumber);
            queryUserRequest.setStartTime(opayFriendsRequest.getStartTime());
            batchQuery = new OpayApiRequest();
            batchQuery.setMerchantId(merchantId);
            batchQuery.setRequestId(incrKeyService.getIncrKey());
            batchQuery.setData(AESUtil.encrypt(mapper.writeValueAsString(queryUserRequest), aesKey, iv));
            OpayApiResponse resultResponse2 = opayFriends.queryUserListByPhone(batchQuery);
            json = AESUtil.decrypt(resultResponse2.getData(), aesKey, iv);
            OpayApiQueryUserByPhoneResponse queryUserByPhoneResponse2 = mapper.readValue(json, OpayApiQueryUserByPhoneResponse.class);
            for (OpayUserModel user : queryUserByPhoneResponse2.getUsers()) {
                userMap.put(user.getUserId(), user);
            }
        }

        Set<Map.Entry<String, OpayUserModel>> entry = userMap.entrySet();
        for (Map.Entry<String, OpayUserModel> e : entry) {
            resultList.add(e.getValue());
        }
        return new ResultResponse(resultList);
    }

    private void mobileHandler(OpayFriendsRequest opayFriendsRequest) {
        List<String> mobiles = new ArrayList<>();
        for (String mobile : opayFriendsRequest.getMobiles()) {
            if (StringUtils.startsWith(mobile, "0")) {
                mobiles.add("+234" + StringUtils.substringAfter(mobile, "0"));
            } else if (StringUtils.startsWith(mobile, "234")) {
                mobiles.add("+" + mobile);
            } else if (StringUtils.startsWith(mobile, "+234")) {
                mobiles.add(mobile);
            } else {
                mobiles.add("+234" + mobile);
            }
        }
        opayFriendsRequest.setMobiles(mobiles);
    }
}
