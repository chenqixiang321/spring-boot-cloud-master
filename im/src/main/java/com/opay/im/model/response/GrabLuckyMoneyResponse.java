package com.opay.im.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ApiModel(value = "抢到红包信息返回")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GrabLuckyMoneyResponse {
    @ApiModelProperty(value = "红包ID")
    private Long id;
    @ApiModelProperty(value = "红包金额")
    private BigDecimal amount;
}
