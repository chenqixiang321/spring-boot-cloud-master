package com.opay.invite.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpayApiUserRecordRequest {

    private String userId;

    private String startTime;

    private String endTime;

    private String serviceType;

    private String pageNo;

    private String pageSize;

}
