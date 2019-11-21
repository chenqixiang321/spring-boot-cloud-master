package com.opay.im.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "修改群组信息", description = "修改群组信息")
public class UpdateGroupModelRequest {

    @ApiModelProperty(value = "群组ID")
    private String id;

    @ApiModelProperty(value = "群组头像")
    private String img;

    @ApiModelProperty(value = "群组名称")
    private String name;

    @ApiModelProperty(value = "群组公告")
    private String notice;
}
