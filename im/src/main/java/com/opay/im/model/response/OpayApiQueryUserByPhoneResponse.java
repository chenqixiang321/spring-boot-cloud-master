package com.opay.im.model.response;

import com.opay.im.model.OpayUserModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpayApiQueryUserByPhoneResponse {
    private List<OpayUserModel> users;
}
