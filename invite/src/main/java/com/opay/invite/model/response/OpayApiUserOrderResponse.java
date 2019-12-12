package com.opay.invite.model.response;

import com.opay.invite.model.OpayApiUserOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OpayApiUserOrderResponse {
    private String total;

    private List<OpayApiUserOrder> records;

}
