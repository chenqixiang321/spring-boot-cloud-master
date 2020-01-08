//package com.opay.invite.controller;
//
//import com.opay.invite.resp.Result;
//import com.opay.invite.service.ActiveService;
//import com.opay.invite.stateconfig.RewardConfig;
//import com.opay.invite.utils.RedisUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Slf4j
//@RestController
//@RequestMapping(value = "/test")
//@Api(value = "测试接口")
//public class TestController {
//
//    @Autowired
//    private ActiveService activeService;
//
//    @Autowired
//    private RewardConfig rewardConfig;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @ApiOperation(value = "活动锁定", notes = "活动锁定")
//    @GetMapping("/activeLock")
//    public Result activeLock(HttpServletRequest request) throws Exception {
//        activeService.lockActive(rewardConfig.getActiveId());
//        return Result.success();
//    }
//
//    @ApiOperation(value = "活动解锁", notes = "活动解锁")
//    @GetMapping("/activeUnLock")
//    public Result activeUnLock(HttpServletRequest request) throws Exception {
//        activeService.unLockActive(rewardConfig.getActiveId());
//        return Result.success();
//    }
//
//    @ApiOperation(value = "抽奖活动锁定", notes = "抽奖活动锁定")
//    @GetMapping("/luckDrawActiveLock")
//    public Result luckDrawActiveLock(HttpServletRequest request) throws Exception {
//        activeService.lockActive(rewardConfig.getLuckDrawId());
//        return Result.success();
//    }
//
//    @ApiOperation(value = "抽奖活动解锁", notes = "抽奖活动解锁")
//    @GetMapping("/luckDrawActiveUnLock")
//    public Result luckDrawActiveUnLock(HttpServletRequest request) throws Exception {
//        activeService.unLockActive(rewardConfig.getLuckDrawId());
//        return Result.success();
//    }
//
//    @ApiOperation(value = "test", notes = "test")
//    @GetMapping("/test")
//    public Result test(HttpServletRequest request) throws Exception {
//        Integer cashBackTotalAmount = redisUtil.get("invite_active_", "cashBackTotalAmount");
//        log.info("RewardJob cashBackTotalAmount:{}",cashBackTotalAmount);
//        if(cashBackTotalAmount == null || cashBackTotalAmount < 0 ){
//            log.warn("RewardJob 活动已结束 开关已关 cashBackTotalAmount:{}",cashBackTotalAmount);
//        }
//        return Result.success();
//    }
//
//
//
//
//
//
//}
