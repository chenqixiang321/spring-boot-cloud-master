package com.opay.im.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "创建群组", description = "创建群组所需参数")
public class CreateGroupRequest {
    @ApiModelProperty(value = "群主ID,创建人ID", hidden = true)
    private String opayId;
    @ApiModelProperty(value = "群名称")
    private String groupName;
    @ApiModelProperty(value = "群头像")
    private String groupImg;
    @ApiModelProperty(value = "创建人头像")
    private String headImg;
    @ApiModelProperty(value = "创建人名称")
    private String founderName;
}
