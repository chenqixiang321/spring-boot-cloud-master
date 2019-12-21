package com.opay.invite.backstage.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class WithdrawRecordDto implements Serializable {

    @ApiModelProperty(value = "记录表主键id")
    private Long id;
    @ApiModelProperty(value = "申请人id")
    private String opayId;

    @ApiModelProperty(value = "申请金额")
    private String amount;

    @ApiModelProperty(value = "0:bonus 1:balance")
    private Byte type;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "申请状态 0:申请中， 1:审批通过 2:审批不通过 3:转账成功 4:转账失败")
    private Byte status;

    private Integer month;

    private Integer day;

    @ApiModelProperty(value = "用户手机设备号")
    private String deviceId;
    @ApiModelProperty(value = "用户ip地址")
    private String ip;

    @ApiModelProperty(value = "外部订单号")
    private String tradeNo;

    @ApiModelProperty(value = "内部流水号")
    private String reference;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作时间")
    private String operateTime;

    @ApiModelProperty(value = "备注名")
    private String remarkName;
    @ApiModelProperty(value = "名")
    private String firstName;
    @ApiModelProperty(value = "性别")
    private String gender;
    @ApiModelProperty(value = "中间名")
    private String middleName;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "照片")
    private String photo;
    @ApiModelProperty(value = "签名")
    private String signature;
    @ApiModelProperty(value = "姓")
    private String surname;


}