package com.opay.invite.backstage.controller;

import com.alibaba.fastjson.JSON;
import com.opay.invite.backstage.dto.NotifyMessage;
import com.opay.invite.backstage.exception.BackstageExceptionEnum;
import com.opay.invite.backstage.service.WithdrawService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping(value = "/api")
@Api(value = "提现转账异步回调")
public class ApiController {

    @Resource
    private WithdrawService withdrawService;

    /**
     * 转账结果异步通知
     * @param notifyMessage
     */
    @PostMapping("/transferNotify")
    public void transferNotify(@RequestBody String notifyMessage) {
        log.warn("ApiController.transferNotify request param notifyMessage:{}", notifyMessage);
        if (StringUtils.isEmpty(notifyMessage)) {
            log.error(BackstageExceptionEnum.PARAMETER_EMPTY.getMessage());
        }

        NotifyMessage message = JSON.parseObject(notifyMessage, NotifyMessage.class);

        // 1. 返回参数解析
        NotifyMessage.Body payload = message.getPayload();
        String orderStatus = payload.getOrderStatus();
        String orderNo = payload.getOrderNo();

        withdrawService.transferNotify(null, orderNo, orderStatus);
    }

}
