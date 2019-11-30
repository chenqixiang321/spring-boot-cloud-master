package com.opay.invite.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "关系列表", description = "关系列表")
public class InviteList {

    @ApiModelProperty(value = "描述")
    private String msg;

    @ApiModelProperty(value = "list数据")
    private List<OpayInviteRelationVo> list;

}
