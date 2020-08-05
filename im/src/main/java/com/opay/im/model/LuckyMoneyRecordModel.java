package com.opay.im.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "com-opay-im-model-LuckyMoneyRecord")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckyMoneyRecordModel {
    @ApiModelProperty(value = "null")
    private Long id;

    /**
     * 红包id
     */
    @ApiModelProperty(value = "红包id")
    private Long luckMoneyId;

    /**
     * 发送者的opay_id
     */
    @ApiModelProperty(value = "抢到者的opay_id")
    private String opayId;

    /**
     * 发送者的opay名字
     */
    @ApiModelProperty(value = "抢到者的opay名字")
    private String opayName;

    /**
     * 发送者的手机号
     */
    @ApiModelProperty(value = "抢到者的手机号")
    private String opayPhone;

    /**
     * 红包金额
     */
    @ApiModelProperty(value = "抢到红包金额")
    private BigDecimal amount;

    /**
     * 抢到红包时间
     */
    @ApiModelProperty(value = "抢到红包时间")
    private Date getTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 版本
     */
    @ApiModelProperty(value = "版本")
    private Long version;

    @ApiModelProperty(value = "reference")
    private String reference;

    @ApiModelProperty(value = "获取红包状态1:成功 2:失败 3:PENDING")
    private int getStatus;

    @ApiModelProperty(value="transactionId")
    private String transactionId;
}