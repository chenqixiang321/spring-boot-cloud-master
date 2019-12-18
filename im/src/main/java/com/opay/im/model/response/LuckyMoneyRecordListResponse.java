package com.opay.im.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "view查看红包-每人抢到的钱数")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckyMoneyRecordListResponse {
    @ApiModelProperty(value = "单号")
    private Long id;

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
}