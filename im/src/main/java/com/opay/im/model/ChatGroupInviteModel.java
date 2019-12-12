package com.opay.im.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-opay-im-model-ChatGroupInvite")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroupInviteModel {
    @ApiModelProperty(value = "null")
    private Long id;

    /**
     * 群id
     */
    @ApiModelProperty(value = "群id")
    private Long groupId;

    /**
     * 邀请人的opay_id
     */
    @ApiModelProperty(value = "邀请人的opay_id")
    private String opayId;

    /**
     * 被邀请人的opay_id
     */
    @ApiModelProperty(value = "被邀请人的opay_id")
    private String inviteOpayId;

    /**
     * 加入时间
     */
    @ApiModelProperty(value = "加入时间")
    private Date inviteTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}