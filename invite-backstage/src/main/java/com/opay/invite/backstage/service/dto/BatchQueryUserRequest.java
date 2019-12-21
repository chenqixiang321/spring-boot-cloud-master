package com.opay.invite.backstage.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatchQueryUserRequest {
    private String mobile;
    private String userId;
}
