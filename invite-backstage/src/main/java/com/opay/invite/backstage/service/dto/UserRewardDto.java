package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liuzhihang
 * @date 2019/12/18 17:25
 */
@Data
@ApiModel(value = "用户获取奖励DTO")
public class UserRewardDto {

    @ApiModelProperty(value = "用户id")
    private String opayId;
    @ApiModelProperty(value = "徒弟id")
    private String pupilId;
    // @ApiModelProperty(value = "徒弟手机号")
    // private String pupilPhone;
    // @ApiModelProperty(value = "徒弟名字")
    private String pupilName;
    @ApiModelProperty(value = "奖励金额")
    private String reward;
    @ApiModelProperty(value = "时间")
    private String createTime;
    @ApiModelProperty(value = "0:待入账，1:已入账")
    private Byte status;
    @ApiModelProperty(value = "执行行为奖励 1:注册绑定徒弟关系 2:徒弟首次充值到钱包 3:购买话费或其它小额支付 4:博彩 5:打车")
    private Byte action;
    @ApiModelProperty(value = "实际金额")
    private String amount;
    @ApiModelProperty(value = "邀请人账号类型0:普通用户， 1:代理")
    private Byte markType;


}