package com.opay.im.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@ApiModel(value = "加入群", description = "加入群所需参数")
public class JoinGroupRequest {

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
    /**
     * 群主的opay_id
     */
//    @ApiModelProperty(value="群主的opay_id")
//    private String ownerOpayId;
    /**
     * 群成员名
     */
    @ApiModelProperty(value="群成员名")
    private String name;

    /**
     * 成员头像
     */
    @ApiModelProperty(value="成员头像")
    private String img;



}
