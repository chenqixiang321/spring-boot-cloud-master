package com.opay.im.model.request;

import lombok.Data;

/**
 * @author liuzhihang
 * @date 2019/12/19 19:48
 */
@Data
public class OpayRefundLuckMoneyRequest {

    /**
     * 外部退红包生成的唯一订单号 唯一标识
     */
    private String merchartOrderNo;
    /**
     * 发红包订单号(opay 系统 返回的transactionId)
     */
    private String sendOrderNo;

    /**
     * 发红包用户id
     */
    private String senderId;

}
