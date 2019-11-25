package com.opay.im.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "删除群成员", description = "删除群成员")
public class RemoveGroupMemberRequest {
    /**
     * 群id
     */
    @ApiModelProperty(value="群id")
    private Long groupId;

    /**
     * 群成员的opay_id
     */
    @ApiModelProperty(value="群成员的opay_id")
    private String opayId;


    @ApiModelProperty(value="群主的opay_id",hidden = true)
    private String ownerOpayId;
}
