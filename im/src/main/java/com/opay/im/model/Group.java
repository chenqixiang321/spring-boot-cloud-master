package com.opay.im.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "群组信息", description = "群组信息")
public class Group {

    @ApiModelProperty(value = "群组ID")
    private String id;

    @ApiModelProperty(value = "群组头像")
    private String img;

    @ApiModelProperty(value = "群组名称")
    private String name;

    @ApiModelProperty(value = "群组公告")
    private String notice;

    @ApiModelProperty(value = "群组人数")
    private int number;
}
