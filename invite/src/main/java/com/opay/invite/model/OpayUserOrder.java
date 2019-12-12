package com.opay.invite.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpayUserOrder {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "折扣前总金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "收益状态")
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "订单时间")
    private String orderTime;

    @ApiModelProperty(value = "交易用户")
    private String opayId;

    @ApiModelProperty(value = "2:徒弟首次充值到钱包 3:购买话费或其它小额支付 4:博彩 5:打车")
    private int type;

    private Date createAt;

    private int status;

    private String masterOpayId;

    private int markType;

    private int month;

    private int day;

}
