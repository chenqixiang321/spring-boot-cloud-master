package com.opay.invite.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpayUserOrder {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "订单号")
    private Long order_id;

    @ApiModelProperty(value = "折扣前总金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "收益状态")
    private BigDecimal discountAmount;

    @ApiModelProperty(value = "订单时间")
    private int orderTime;

    @ApiModelProperty(value = "交易用户")
    private String opayId;

    @ApiModelProperty(value = "充值类型:0:充值钱包 1:购买充值卡，2:博彩 3:打车")
    private int type;

    @ApiModelProperty(value = "师傅")
    private String masterOpayId;

    @ApiModelProperty(value = "邀请人账号类型0:普通用户， 1:代理")
    private int markType;
}
