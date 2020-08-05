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

@ApiModel(value = "view查看红包")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckyMoneyRecordResponse {

    @ApiModelProperty(value = "红包id")
    private Long luckMoneyId;

    @ApiModelProperty(value = "红包个数")
    private Integer quantity;
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "已抢红包个数")
    private Integer grabQuantity;
    @ApiModelProperty(value = "已抢金额")
    private BigDecimal grabAmount;
    @ApiModelProperty(value = "已抢了n秒")
    private long seconds;

    @ApiModelProperty(value = "谁抢了")
    private List<LuckyMoneyRecordListResponse> luckyMoneyRecordListResponse;
}