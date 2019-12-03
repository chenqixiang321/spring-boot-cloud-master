package com.opay.im.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryUserRequest {
    private String mobile;
    private String startTime;
}
