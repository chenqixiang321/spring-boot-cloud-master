package com.opay.im.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel(value = "黑名单中的userIds")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlackListUserIdsResponse {

    @ApiModelProperty(value = "黑名单用户ids")
    private List<String> userIds;
}
