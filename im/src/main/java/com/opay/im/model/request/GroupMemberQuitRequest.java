package com.opay.im.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "群成员退群", description = "删除群成员退群")
public class GroupMemberQuitRequest {
    /**
     * 群id
     */
    @ApiModelProperty(value="群id")
    private Long groupId;

    /**
     * 群成员的opay_id
     */
    @ApiModelProperty(value="群成员的opay_id",hidden = true)
    private String opayId;

}
