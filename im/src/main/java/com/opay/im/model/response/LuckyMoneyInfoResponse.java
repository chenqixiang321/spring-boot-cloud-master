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
import java.util.List;

@ApiModel(value = "获取红包信息返回")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckyMoneyInfoResponse {

    @ApiModelProperty(value = "红包ID")
    private Long id;
    /**
     * 发送者的opay_id
     */
    @ApiModelProperty(value = "发送者的opay_id")
    private String opayId;
    /**
     * 红包说明Best Wishes!
     */
    @ApiModelProperty(value = "红包说明Best Wishes!")
    private String show;

    /**
     * 红包总金额
     */
    @ApiModelProperty(value = "红包总金额")
    private BigDecimal amount;


    @ApiModelProperty(value = "抢到的红包金额")
    private BigDecimal grabAmount;
    /**
     * 红包个数
     */
    @ApiModelProperty(value = "红包个数")
    private Integer quantity;


    @ApiModelProperty(value = "抢了多少")
    private List<LuckyMoneyRecordInfoResponse> LuckyMoneyRecordInfoResponses;
}