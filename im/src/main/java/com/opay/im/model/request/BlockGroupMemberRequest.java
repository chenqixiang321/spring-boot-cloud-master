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
@ApiModel(value = "禁言群成员", description = "禁言群成员所需参数")
public class BlockGroupMemberRequest {

    @ApiModelProperty(value="群id")
    private Long groupId;

    @ApiModelProperty(value="群成员的opay_id")
    private String opayId;

    @ApiModelProperty(value="是否禁言 true:开启 false;关闭")
    private boolean IsBlock;

    @ApiModelProperty(value="群主的opay_id",hidden = true)
    private String ownerOpayId;
}
