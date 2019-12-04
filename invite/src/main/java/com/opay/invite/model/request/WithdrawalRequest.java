package com.opay.invite.model.request;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "用户提现", description = "用户提现")
public class WithdrawalRequest {

    @ApiModelProperty(value = "提现金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "提现类型0:bonus,1:balance")
    private Integer type;//提现类型

    @ApiModelProperty(value = "设备号")
    private String deviceId;
}
