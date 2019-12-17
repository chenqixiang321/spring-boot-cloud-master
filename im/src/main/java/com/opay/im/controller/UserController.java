package com.opay.im.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opay.im.common.SystemCode;
import com.opay.im.exception.ImException;
import com.opay.im.model.OpayUserModel;
import com.opay.im.model.request.BatchQueryUserRequest;
import com.opay.im.model.request.OpayApiRequest;
import com.opay.im.model.request.OpayFriendRequest;
import com.opay.im.model.request.OpayFriendsRequest;
import com.opay.im.model.request.QueryUserRequest;
import com.opay.im.model.response.BlackListUserIdsResponse;
import com.opay.im.model.response.OpayApiQueryUserByPhoneResponse;
import com.opay.im.model.response.OpayApiResultResponse;
import com.opay.im.model.response.ResultResponse;
import com.opay.im.model.response.SuccessResponse;
import com.opay.im.service.IncrKeyService;
import com.opay.im.service.OpayFeignApiService;
import com.opay.im.service.RongCloudService;
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
    private RongCloudService rongCloudService;
    @Autowired
    private OpayFeignApiService opayFeignApiService;
    @Autowired
    private IncrKeyService incrKeyService;
    @Value("${config.opay.aesKey}")
    private String aesKey;
    @Value("${config.opay.iv}")
    private String iv;
    @Value("${config.opay.merchantId}")
    private String merchantId;

    @ApiOperation(value = "获得融云token", notes = "从数据库里获取token,不存在则创建")
    @PostMapping("/token")
    public ResultResponse getToken() throws Exception {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setCode(SystemCode.SYS_API_SUCCESS.getCode());
        resultResponse.setMessage("success");
        Map<String, String> token = new HashMap<>();
        token.put("token", rongCloudService.getRyToken(String.valueOf(request.getAttribute("opayId")), String.valueOf(request.getAttribute("phoneNumber"))));
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
        rongCloudService.addBlackList(userId, blackUserId);
        return new SuccessResponse();
    }

    @ApiOperation(value = "把用户从黑名单移除", notes = "把用户从黑名单移除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blackUserId", value = "用户ID", required = true, paramType = "path", dataType = "String")
    })
    @PostMapping("/black/remove/{blackUserId}")
    public SuccessResponse removeBlackList(@PathVariable String blackUserId) throws Exception {
        String userId = String.valueOf(request.getAttribute("opayId"));
        rongCloudService.removeBlackList(userId, blackUserId);
        return new SuccessResponse();
    }

    @ApiOperation(value = "获取用户的黑名单列表", notes = "获取用户的黑名单列表")
    @PostMapping("/black/get")
    public ResultResponse<BlackListUserIdsResponse> getBlackList() throws Exception {
        String userId = String.valueOf(request.getAttribute("opayId"));
        return new ResultResponse<>(rongCloudService.getBlackList(userId));
    }

    @ApiOperation(value = "根据通讯录获取opay好友", notes = "根据通讯录获取opay好友")
    @PostMapping("/friends")
    public ResultResponse<List<OpayUserModel>> getFriends(@RequestBody OpayFriendsRequest opayFriendsRequest) throws Exception {
        String userId = String.valueOf(request.getAttribute("opayId"));
        mobilesHandler(opayFriendsRequest);
        List<OpayUserModel> resultList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        BatchQueryUserRequest batchQueryUserRequest = new BatchQueryUserRequest();
        batchQueryUserRequest.setMobile(String.join(",", opayFriendsRequest.getMobiles()));
        Map<String, String> userIdMap = rongCloudService.getBlackListMap(userId);
        OpayApiResultResponse<String> opayApiResultResponse = opayFeignApiService.batchQueryUserByPhone(getOpayApiRequest(batchQueryUserRequest));
        String json = opayApiResultResponseHandler(opayApiResultResponse);
        OpayApiQueryUserByPhoneResponse queryUserByPhoneResponse = mapper.readValue(json, OpayApiQueryUserByPhoneResponse.class);
        Map<String, OpayUserModel> userMap = new HashMap<>();
        for (OpayUserModel user : queryUserByPhoneResponse.getUsers()) {
            user.setOnlyTrade(false);
            userMap.put(user.getUserId(), user);
            if (userIdMap.get(user.getUserId()) == null) {
                user.setBlackList(false);
            } else {
                user.setBlackList(true);
            }
        }
        if (opayFriendsRequest.isLoadTradePhone()) {
            String phoneNumber = String.valueOf(request.getAttribute("phoneNumber"));
            QueryUserRequest queryUserRequest = new QueryUserRequest();
            queryUserRequest.setMobile(mobileHandler(phoneNumber));
            queryUserRequest.setStartTime(opayFriendsRequest.getStartTime());
            OpayApiResultResponse<String> resultResponse2 = opayFeignApiService.queryUserListByPhone(getOpayApiRequest(queryUserRequest));
            json = opayApiResultResponseHandler(resultResponse2);
            OpayApiQueryUserByPhoneResponse queryUserByPhoneResponse2 = mapper.readValue(json, OpayApiQueryUserByPhoneResponse.class);
            for (OpayUserModel user : queryUserByPhoneResponse2.getUsers()) {
                user.setOnlyTrade(true);
                user.setBlackList(false);
                userMap.put(user.getUserId(), user);
                if (userIdMap.get(user.getUserId()) == null) {
                    user.setBlackList(false);
                } else {
                    user.setBlackList(true);
                }
            }
        }

        Set<Map.Entry<String, OpayUserModel>> entry = userMap.entrySet();
        for (Map.Entry<String, OpayUserModel> e : entry) {
            resultList.add(e.getValue());
        }
        return new ResultResponse(resultList);
    }

    @ApiOperation(value = "获取单个好友信息", notes = "获取单个好友信息")
    @PostMapping("/friend")
    public ResultResponse<OpayUserModel> getFriendInfo(@RequestBody OpayFriendRequest opayFriendRequest) throws Exception {
        String userId = String.valueOf(request.getAttribute("opayId"));
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> userIdMap = rongCloudService.getBlackListMap(userId);
        BatchQueryUserRequest batchQueryUserRequest = new BatchQueryUserRequest();
        batchQueryUserRequest.setUserId(opayFriendRequest.getOpayId());
        OpayApiResultResponse<String> opayApiResultResponse = opayFeignApiService.batchQueryUserByPhone(getOpayApiRequest(batchQueryUserRequest));
        String json = opayApiResultResponseHandler(opayApiResultResponse);
        OpayApiQueryUserByPhoneResponse queryUserByPhoneResponse = mapper.readValue(json, OpayApiQueryUserByPhoneResponse.class);
        List<OpayUserModel> users = queryUserByPhoneResponse.getUsers();
        if (users.isEmpty()) {
            throw new ImException("opay user does not exist");
        }
        OpayUserModel user = users.get(0);
        user.setBlackList(false);
        if (userIdMap.get(user.getUserId()) == null) {
            user.setBlackList(false);
        } else {
            user.setBlackList(true);
        }
        return new ResultResponse<>(user);
    }

    private OpayApiRequest getOpayApiRequest(Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OpayApiRequest batchQuery = new OpayApiRequest();
        batchQuery.setMerchantId(merchantId);
        batchQuery.setRequestId(incrKeyService.getIncrKey());
        batchQuery.setData(AESUtil.encrypt(mapper.writeValueAsString(object), aesKey, iv));
        return batchQuery;
    }

    private String opayApiResultResponseHandler(OpayApiResultResponse<String> opayApiResultResponse) throws Exception {
        if (!SystemCode.SYS_API_SUCCESS.getCode().equals(opayApiResultResponse.getCode())) {
            throw new ImException(opayApiResultResponse.getMessage());
        }
        return AESUtil.decrypt(opayApiResultResponse.getData(), aesKey, iv);
    }

    private void mobilesHandler(OpayFriendsRequest opayFriendsRequest) {
        List<String> mobiles = new ArrayList<>();
        for (String mobile : opayFriendsRequest.getMobiles()) {
            mobiles.add("+234" + StringUtils.substring(mobile, -10));
        }
        opayFriendsRequest.setMobiles(mobiles);
    }

    private String mobileHandler(String mobile) {
        return "+234" + StringUtils.substring(mobile, -10);
    }
}
