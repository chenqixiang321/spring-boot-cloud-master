package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "邀请关系", description = "邀请关系")
public class OpayInviteRelation {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "邀请人")
    private String masterId;

    @ApiModelProperty(value = "被要人")
    private String pupilId;

    @ApiModelProperty(value = "邀请时间")
    private Date createAt;

    @ApiModelProperty(value = "邀请人的邀请人")
    private String masterParentId;

    @ApiModelProperty(value = "0:普通新用户，1:老用户 2:代理")
    private int markType;

    @ApiModelProperty(value = "年月201911")
    private int month;

    @ApiModelProperty(value = "年月日20191101")
    private int day;

    @ApiModelProperty(value = "")
    private String pupilPhone;

    @ApiModelProperty(value = "")
    private String masterPhone;

    @ApiModelProperty(value = "邀请时间,long类型到毫秒")
    private Long createTime;

}
