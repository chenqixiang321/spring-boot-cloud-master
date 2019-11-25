package com.opay.im.model.request;

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
@ApiModel(value = "获取融云token", description = "获取融云token所需参数")
public class GetRongCloudTokenRequest {
    @ApiModelProperty(value = "OpayId")
    private String opayId;
    @ApiModelProperty(value = "手机号")
    private String phone;
}
