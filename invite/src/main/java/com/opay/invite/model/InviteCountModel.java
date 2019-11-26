package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-opay-invite-model-InviteCount")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InviteCountModel {
    @ApiModelProperty(value="null")
    private Long id;

    /**
    * 发送者的opay_id
    */
    @ApiModelProperty(value="发送者的opay_id")
    private String opayId;

    /**
    * 发送者的opay名字
    */
    @ApiModelProperty(value="发送者的opay名字")
    private String opayName;

    /**
    * 发送者的手机号
    */
    @ApiModelProperty(value="发送者的手机号")
    private String opayPhone;

    /**
    * 每天登录获得次数
    */
    @ApiModelProperty(value="每天登录获得次数")
    private Integer login;

    /**
    * 每天分享次数
    */
    @ApiModelProperty(value="每天分享次数")
    private Integer share;

    /**
    * 成功邀请次数
    */
    @ApiModelProperty(value="成功邀请次数")
    private Integer invite;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 版本
    */
    @ApiModelProperty(value="版本")
    private Long version;
}