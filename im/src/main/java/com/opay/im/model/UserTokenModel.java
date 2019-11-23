package com.opay.im.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-opay-im-model-UserToken")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenModel {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * opay帐号id
     */
    @ApiModelProperty(value = "opay帐号id")
    private String opayId;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 融云token
     */
    @ApiModelProperty(value = "融云token")
    private String token;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}