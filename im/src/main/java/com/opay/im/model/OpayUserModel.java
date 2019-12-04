package com.opay.im.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-opay-im-model-OpayUserModel")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OpayUserModel {
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
    @ApiModelProperty(value = "id")
    private String userId;
}
