package com.opay.invite.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value = "提现记录", description = "提现记录")
public class OpayActiveTixian {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "受益人")
    private String opayId;

    @ApiModelProperty(value = "奖励金额,变动")
    private BigDecimal amount;

    @ApiModelProperty(value = "提现类型")
    private int type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "收益时间")
    private Date createAt;

    @ApiModelProperty(value = "0:申请中")
    private int status;

    @ApiModelProperty(value = "年月201911",hidden = true)
    private int month;

    @ApiModelProperty(value = "年月日20191101",hidden = true)
    private int day;

    @ApiModelProperty(value = "手机设备号")
    private String deviceId;

    @ApiModelProperty(value = "提现时所属ip")
    private String ip;
}
