package com.opay.im.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel(value = "红包详情请求参数")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckyMoneyDetailRequest {

    @ApiModelProperty(value = "红包ID")
    private Long id;

    @ApiModelProperty(value = "发送者的opay_id")
    private String senderId;
}