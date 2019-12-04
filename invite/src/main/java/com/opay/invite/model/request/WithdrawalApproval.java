package com.opay.invite.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "提现审批", description = "提现审批")
public class WithdrawalApproval {

    @ApiModelProperty(value = "调用请求的当前时间，格式2019-01-11")
    private String time;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "审核状态")
    private Integer status;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "提现用户")
    private String opayId;
}
