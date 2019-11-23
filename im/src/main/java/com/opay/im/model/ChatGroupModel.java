package com.opay.im.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-opay-im-model-ChatGroupModel")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroupModel {
    @ApiModelProperty(value="群id")
    private long id;

    @ApiModelProperty(value="群主ID")
    private String opayId;
    /**
    * 群组头像
    */
    @ApiModelProperty(value="群组头像")
    private String img;

    /**
    * 群组名称
    */
    @ApiModelProperty(value="群组名称")
    private String name;

    /**
    * 公告
    */
    @ApiModelProperty(value="公告")
    private String notice;

    /**
    * 人数
    */
    @ApiModelProperty(value="人数")
    private Integer number;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;
}