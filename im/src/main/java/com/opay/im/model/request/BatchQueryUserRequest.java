package com.opay.im.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchQueryUserRequest {
    private String mobile;
    private String userId;
}
