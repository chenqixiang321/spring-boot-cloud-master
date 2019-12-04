package com.opay.im.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value="com-opay-im-model-LuckyMoney")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckyMoneyModel {
    @ApiModelProperty(value="null")
    private Long id;

    /**
    * 发送者的opay_id
    */
    @ApiModelProperty(value="发送者的opay_id")
    private String opayId;

    /**
    * 发送者的opay名字
    */
    @ApiModelProperty(value="发送者的opay名字")
    private String opayName;

    /**
    * 发送者的手机号
    */
    @ApiModelProperty(value="发送者的手机号")
    private String opayPhone;

    /**
    * 发送目标 群or人
    */
    @ApiModelProperty(value="发送目标 群or人")
    private String targetId;

    /**
    * 目标类型:0个人 1群
    */
    @ApiModelProperty(value="目标类型:0个人 1群")
    private Integer targetType;

    /**
    * 红包说明Best Wishes!
    */
    @ApiModelProperty(value="红包说明Best Wishes!")
    private String show;

    /**
    * 红包总金额
    */
    @ApiModelProperty(value="红包总金额")
    private BigDecimal amount;

    /**
    * 红包个数
    */
    @ApiModelProperty(value="红包个数")
    private Integer quantity;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 版本
    */
    @ApiModelProperty(value="版本")
    private Long version;
}