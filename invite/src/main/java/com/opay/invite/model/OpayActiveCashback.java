package com.opay.invite.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户钱包", description = "用户钱包")
public class OpayActiveCashback {
    @JsonIgnore
    @ApiModelProperty(value = "主键")
    private Long id;

    @JsonIgnore
    @ApiModelProperty(value = "受益人")
    private String opayId;

    @JsonIgnore
    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "奖励金额,变动")
    private BigDecimal amount;

    @JsonIgnore
    @ApiModelProperty(value = "收益时间")
    private Date createAt;

    @JsonIgnore
    @ApiModelProperty(value = "0:正常 1:冻结")
    private int status;

    @ApiModelProperty(value = "奖励金额，累计")
    private BigDecimal totalAmount;

    @JsonIgnore
    @ApiModelProperty(value = "版本号")
    private int version;

    @JsonIgnore
    @ApiModelProperty(value = "更新时间")
    private Date updateAt;

    @JsonIgnore
    @ApiModelProperty(value = "活动ID")
    private String activeId;
}
