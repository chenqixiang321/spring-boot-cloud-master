package com.opay.invite.backstage.dto;

import lombok.Data;

/**
 * 查询转账订单响应信息
 * @author yimin
 */
@Data
public class TransOrder {

    /**
     * 流水号
     */
    private String requestId;
    /**
     * OPay订单号
     */
    private String orderNo;
    /**
     * 商户订单号
     */
    private String reference;
    /**
     * 订单状态。SUCCESS、PENDING、FAIL，PENDING需发起查单
     */
    private String status;
    /**
     * 订单失败信息
     */
    private String errorMsg;

}
