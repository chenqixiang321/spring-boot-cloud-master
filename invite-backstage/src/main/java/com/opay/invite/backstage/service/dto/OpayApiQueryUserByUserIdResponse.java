package com.opay.invite.backstage.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpayApiQueryUserByUserIdResponse {
    private List<OpayUserModel> users;
}
