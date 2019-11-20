package com.opay.im.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "红包信息", description = "红包信息")
public class LuckyMoney {

    @ApiModelProperty(value = "红包Id")
    private long id;

    @ApiModelProperty(value = "红包金额")
    private BigDecimal money;

    @ApiModelProperty(value = "红包数量")
    private int number;
}
