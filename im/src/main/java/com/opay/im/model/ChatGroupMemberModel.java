package com.opay.im.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-opay-im-model-ChatGroupMember")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroupMemberModel {
    @ApiModelProperty(value="null")
    private Long id;

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

    /**
    * 是否静音 0:否 1:是
    */
    @ApiModelProperty(value="是否静音 0:否 1:是")
    private Boolean isMute;

    /**
    * 是否屏蔽 0:否 1:是
    */
    @ApiModelProperty(value="是否屏蔽 0:否 1:是")
    private Boolean isBlock;

    /**
    * 加入时间
    */
    @ApiModelProperty(value="加入时间")
    private Date joinTime;
}