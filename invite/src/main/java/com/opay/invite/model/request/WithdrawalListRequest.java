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
@ApiModel(value = "提现申请列表", description = "提现申请列表")
public class WithdrawalListRequest {
    @ApiModelProperty(value = "提现金额,可为空")
    private String phone;

    @ApiModelProperty(value = "调用请求的当前时间，格式2019-01-11")
    private String time;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "审核状态")
    private Integer status;

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;

    @ApiModelProperty(value = "当前页")
    private Integer pageNum;
}
