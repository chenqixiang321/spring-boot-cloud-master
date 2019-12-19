package com.opay.invite.controller;

import com.opay.invite.model.LoginUser;
import com.opay.invite.model.request.MyLuckDrawListRequest;
import com.opay.invite.model.response.LuckDrawCountResponse;
import com.opay.invite.model.response.LuckDrawInfoResponse;
import com.opay.invite.model.response.LuckDrawListResponse;
import com.opay.invite.model.response.LuckDrawResponse;
import com.opay.invite.model.response.ResultResponse;
import com.opay.invite.model.response.SuccessResponse;
import com.opay.invite.resp.CodeMsg;
import com.opay.invite.service.InviteOperateService;
import com.opay.invite.service.LuckDrawInfoService;
import com.opay.invite.utils.DateFormatter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping(value = "/luckDraw")
@Api(value = "抽奖功能API")
public class LuckDrawController {

    @Autowired
    private LuckDrawInfoService luckDrawInfoService;
    @Autowired
    private com.opay.invite.service.InviteCountService InviteCountService;
    @Value("${spring.jackson.time-zone}")
    private String timeZone;
    @Value("${prize-pool.firstPoolStart}")
    private int firstPoolStart;
    @Value("${prize-pool.firstPoolEnd}")
    private int firstPoolEnd;
    @Value("${prize-pool.secondPoolStart}")
    private int secondPoolStart;
    @Value("${prize-pool.secondPoolEnd}")
    private int secondPoolEnd;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private InviteOperateService inviteOperateService;
    private SimpleDateFormat format = new SimpleDateFormat("HH");

    @ApiOperation(value = "获取抽奖信息", notes = "获取抽奖信息")
    @GetMapping("/info")
    public ResultResponse<LuckDrawResponse> getLuckDrawInfo() {
        ResultResponse resultResponse = new ResultResponse();
        LuckDrawResponse luckDrawResponse = new LuckDrawResponse();
        format.setTimeZone(TimeZone.getTimeZone(timeZone));
        int hour = Integer.parseInt(format.format(new Date()));
        Date date = new Date();
        if (hour >= firstPoolStart && hour < firstPoolEnd) {
            luckDrawResponse.setCanLuckyDraw(true);
            luckDrawResponse.setCurrentStartTime(DateFormatter.formatDateByZone(date, timeZone) + " " + firstPoolStart + ":00:00");
            luckDrawResponse.setCurrentEndTime(DateFormatter.formatDateByZone(date, timeZone) + " " + firstPoolEnd + ":00:00");
        } else if (hour >= secondPoolStart && hour < secondPoolEnd) {
            luckDrawResponse.setCanLuckyDraw(true);
            luckDrawResponse.setCurrentStartTime(DateFormatter.formatDateByZone(date, timeZone) + " " + secondPoolStart + ":00:00");
            luckDrawResponse.setCurrentEndTime(DateFormatter.formatDateByZone(date, timeZone) + " " + secondPoolEnd + ":00:00");
        } else {
            luckDrawResponse.setCanLuckyDraw(false);
            if (hour < firstPoolStart) {
                luckDrawResponse.setNextStartTime(DateFormatter.formatDateByZone(date, timeZone) + " " + firstPoolStart + ":00:00");
                luckDrawResponse.setNextEndTime(DateFormatter.formatDateByZone(date, timeZone) + " " + firstPoolEnd + ":00:00");
            } else if (firstPoolEnd <= hour && hour < secondPoolStart) {
                luckDrawResponse.setNextStartTime(DateFormatter.formatDateByZone(date, timeZone) + " " + secondPoolStart + ":00:00");
                luckDrawResponse.setNextEndTime(DateFormatter.formatDateByZone(date, timeZone) + " " + secondPoolEnd + ":00:00");
            } else if (hour >= secondPoolEnd) {
                Date afterDate = DateFormatter.getDateAfter(date, 1);
                luckDrawResponse.setNextStartTime(DateFormatter.formatDateByZone(afterDate, timeZone) + " " + firstPoolStart + ":00:00");
                luckDrawResponse.setNextEndTime(DateFormatter.formatDateByZone(afterDate, timeZone) + " " + firstPoolEnd + ":00:00");
            }
        }
        luckDrawResponse.setSystemTime(DateFormatter.formatDatetimeByZone(date, timeZone));
        luckDrawResponse.setPrizeInfo(luckDrawInfoService.getPrize());
        resultResponse.setData(luckDrawResponse);
        return resultResponse;
    }

    @ApiOperation(value = "获取抽奖次数信息", notes = "获取抽奖次数信息")
    @GetMapping("/count")
    public ResultResponse<LuckDrawCountResponse> getLuckDrawCount(HttpServletRequest request) throws Exception {
        LoginUser user = inviteOperateService.getOpayInfo(request);
        ResultResponse resultResponse = new ResultResponse();
        LuckDrawCountResponse luckDrawCountResponse = new LuckDrawCountResponse();
        Object activityCount = redisTemplate.opsForValue().get("prize_pool:count");
        if (activityCount == null) {
            luckDrawCountResponse.setActivityCount(0);
        } else {
            luckDrawCountResponse.setActivityCount(Integer.parseInt(activityCount.toString()));
        }
        Object inviteCount = redisTemplate.opsForValue().get("invite_count:" + user.getOpayId());
        Object shareCount = redisTemplate.opsForHash().get("invite_share_count:" + user.getOpayId(), "share_current");
        Object shareMaxCount = redisTemplate.opsForHash().get("invite_share_count:" + user.getOpayId(), "share_max");
        Object loginCount = redisTemplate.opsForHash().get("invite_share_count:" + user.getOpayId(), "login_current");
        int inviteCt = 0;
        int shareCt = 0;
        int shareMaxCt = 0;
        int loginCt = 1;
        if (inviteCount != null) {
            inviteCt = Integer.parseInt(inviteCount.toString());
        }
        if (shareCount != null) {
            shareCt = Integer.parseInt(shareCount.toString());
        }
        if (loginCount != null) {
            loginCt = Integer.parseInt(loginCount.toString());
        }
        if (shareMaxCount != null) {
            shareMaxCt = Integer.parseInt(shareMaxCount.toString());
        }
        luckDrawCountResponse.setInviteCount(inviteCt);
        luckDrawCountResponse.setLoginCount(loginCt);
        luckDrawCountResponse.setShareCount(shareCt);
        luckDrawCountResponse.setShareMaxCount(shareMaxCt);
        resultResponse.setData(luckDrawCountResponse);
        return resultResponse;
    }

    @ApiOperation(value = "获取中奖者信息", notes = "获取中奖者信息")
    @GetMapping("/list")
    public ResultResponse<List<LuckDrawListResponse>> getLuckDrawList() throws Exception {
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setData(luckDrawInfoService.selectLuckDrawInfoList());
        return resultResponse;
    }

    @ApiOperation(value = "我的中奖列表", notes = "我的中奖列表")
    @PostMapping("/mylist")
    public ResultResponse<List<LuckDrawListResponse>> getMyLuckDrawList(HttpServletRequest request, @RequestBody @ApiParam(name = "我的中奖列表请求参数", value = "传入json格式", required = true) MyLuckDrawListRequest myLuckDrawListRequest) throws Exception {
        LoginUser user = inviteOperateService.getOpayInfo(request);
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setData(luckDrawInfoService.selectLuckDrawInfoList(user.getOpayId(), myLuckDrawListRequest.getPageNum(), myLuckDrawListRequest.getPageSize()));
        return resultResponse;
    }

    @ApiOperation(value = "分享次数+1", notes = "分享次数+1")
    @PostMapping("/share")
    public SuccessResponse updateShareCount(HttpServletRequest request) throws Exception {
        LoginUser user = inviteOperateService.getOpayInfo(request);
        InviteCountService.updateShareCount(user.getOpayId(), user.getOpayName(), user.getPhoneNumber());
        return new SuccessResponse();
    }

//    @ApiOperation(value = "邀请次数+5", notes = "邀请次数+5")
//    @PostMapping("/invite")
//    public SuccessResponse updateInviteCount(HttpServletRequest request) throws Exception {
//        LoginUser user = inviteOperateService.getOpayInfo(request);
//        InviteCountService.updateInviteCount(user.getOpayId(), user.getOpayName(), user.getPhoneNumber());
//        return new SuccessResponse();
//    }

    @ApiOperation(value = "抽奖", notes = "抽奖")
    @GetMapping
    public ResultResponse<LuckDrawInfoResponse> luckDraw(HttpServletRequest request) throws Exception {
        LoginUser user = inviteOperateService.getOpayInfo(request);
        format.setTimeZone(TimeZone.getTimeZone(timeZone));
        int hour = Integer.parseInt(format.format(new Date()));
        if ((hour >= firstPoolStart && hour < firstPoolEnd) || (hour >= secondPoolStart && hour < secondPoolEnd)) {
            return new ResultResponse(luckDrawInfoService.getLuckDraw(user.getOpayId(), user.getOpayName(), user.getPhoneNumber()));
        } else {
            return new ResultResponse(CodeMsg.LUCKY_DRAW_NOT_START_CODE.getCode(), CodeMsg.LUCKY_DRAW_NOT_START_CODE.getMessage());
        }
    }
}
