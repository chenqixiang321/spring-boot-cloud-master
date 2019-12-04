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

@ApiModel(value = "抢红包请求参数")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GrabLuckyMoneyRequest {

    @ApiModelProperty(value = "红包ID")
    private Long id;
    /**
     * 发送者的opay_id
     */
    @ApiModelProperty(value = "发送者的opay_id")
    private String opayId;
    /**
     * 发送目标 群or人
     */
    @ApiModelProperty(value = "发送目标 群or人的id,不传则默认为当前用户id")
    @NotNull
    private String targetId;

    /**
     * 目标类型:0个人 1群
     */
    @ApiModelProperty(value = "目标类型:0个人 1群")
    @NotNull
    private Integer targetType;

    @ApiModelProperty(value = "发送者的opay名字", hidden = true)
    private String opayName;

    /**
     * 发送者的手机号
     */
    @ApiModelProperty(value = "发送者的手机号", hidden = true)
    private String opayPhone;
}