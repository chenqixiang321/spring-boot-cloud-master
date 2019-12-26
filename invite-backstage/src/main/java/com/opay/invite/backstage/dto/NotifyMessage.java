package com.opay.invite.backstage.dto;

import lombok.Data;

/**
 * 异步回调参数
 */
@Data
public class NotifyMessage {
    private String type;
    private Body payload;
    private String sha512;

    @Data
    public class Body {
        /**
         * 统一订单号
         **/
        private String orderNo;
        /**
         * 平台商户号
         **/
        private String platformId;
        /**
         * 平台商户订单号
         **/
        private String platformOrderNo;
        /**
         * 订单状态
         **/
        private String orderStatus;
        /**
         * 失败码
         **/
        private String errorCode;
        /**
         * 失败原因
         **/
        private String errorMsg;

    }

}