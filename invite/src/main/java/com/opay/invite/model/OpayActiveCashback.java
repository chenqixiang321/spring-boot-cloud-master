package com.opay.invite.model;


import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "用户钱包", description = "用户钱包")
public class OpayActiveCashback {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "受益人")
    private String opayId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "奖励金额,变动")
    private BigDecimal amount;

    @ApiModelProperty(value = "收益时间")
    private Date createAt;

    @ApiModelProperty(value = "0:正常 1:冻结")
    private int status;

    @ApiModelProperty(value = "奖励金额，累计")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "版本号")
    private int version;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;
}
