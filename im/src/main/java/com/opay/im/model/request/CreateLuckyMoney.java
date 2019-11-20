package com.opay.im.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "生成红包", description = "生成红包所需参数")
public class CreateLuckyMoney {
    @ApiModelProperty(value = "红包金额")
    private BigDecimal money;

    @ApiModelProperty(value = "红包数量")
    private int number;
}
