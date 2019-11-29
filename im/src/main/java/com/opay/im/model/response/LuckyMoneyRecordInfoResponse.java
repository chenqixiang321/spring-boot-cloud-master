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

@ApiModel(value = "获取红包信息返回,每个人抢了多少钱")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LuckyMoneyRecordInfoResponse {

    @ApiModelProperty(value = "抢到者的opay名字")
    private String opayName;

    /**
     * 红包金额
     */
    @ApiModelProperty(value = "红包金额")
    private BigDecimal amount;

    /**
     * 抢到红包时间
     */
    @ApiModelProperty(value = "抢到红包时间")
    private Date getTime;

}