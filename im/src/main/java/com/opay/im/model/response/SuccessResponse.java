package com.opay.im.model.response;

import com.opay.im.common.SystemCode;

public class SuccessResponse extends ResultResponse {
    public SuccessResponse() {
        super(SystemCode.SYS_API_SUCCESS.getCode(), "success");
    }
}
