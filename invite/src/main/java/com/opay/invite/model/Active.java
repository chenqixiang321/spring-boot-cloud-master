package com.opay.invite.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 活动实体
 * @author yimin
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "活动实体", description = "活动实体")
public class Active {

    private Long id;

    private Integer version;

    private Date createTime;

    private Date updateTime;

    private String activeId;

    private Integer valid;

}
