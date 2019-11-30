package com.opay.im.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "发红包请求参数")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckyMoneyRequest {

    /**
     * 发送者的opay_id
     */
    @ApiModelProperty(value = "发送者的opay_id", hidden = true)
    private String opayId;

    /**
     * 发送者的opay名字
     */
    @ApiModelProperty(value = "发送者的opay名字", hidden = true)
    private String opayName;

    /**
     * 发送者的手机号
     */
    @ApiModelProperty(value = "发送者的手机号", hidden = true)
    private String opayPhone;

    /**
     * 发送目标 群or人
     */
    @ApiModelProperty(value = "发送目标 群or人")
    @NotNull
    private String targetId;

    /**
     * 目标类型:0个人 1群
     */
    @ApiModelProperty(value = "目标类型:0个人 1群")
    @NotNull
    private Byte targetType;

    /**
     * 红包说明Best Wishes!
     */
    @ApiModelProperty(value = "红包说明Best Wishes!")
    @NotNull
    private String show;

    /**
     * 红包总金额
     */
    @ApiModelProperty(value = "红包总金额")
    @NotNull
    private BigDecimal amount;

    /**
     * 红包个数
     */
    @Range(min = 1, max = 200, message = "quantity must between 1 and 200")
    @ApiModelProperty(value = "红包个数")
    @NotNull
    private Integer quantity;
}