package com.opay.invite.backstage.service;

import com.alibaba.fastjson.JSON;
import com.opay.invite.backstage.config.OrderType;
import com.opay.invite.backstage.dto.TransOrder;
import com.opay.invite.backstage.utils.AESUtil;

import java.util.HashMap;
import java.util.Map;

public interface RpcService {

    default Map<String, String> getParamMap(String senderId, String recieptId, String amount, String currency,
                                            String country, String reference, String orderType, String callBackURL, String payChannel) {
        Map<String, String> map = new HashMap<>();
        map.put("senderId", senderId);
        map.put("recieptId", recieptId);
        map.put("amount", amount);
        if (currency == null || "".equals(currency)) {
            currency = "NGN";
        }
        map.put("currency", currency);
        if (country == null || "".equals(country)) {
            country = "NG";
        }
        map.put("country", country);
        map.put("reference", reference);
        map.put("orderType", orderType);
        if (callBackURL != null && !"".equals(callBackURL)) {
            map.put("callBackURL", callBackURL);
        }
        map.put("payChannel", payChannel);
        if (orderType.equals(OrderType.bonusOffer.getOrderType())) {
            map.put("activityType", OrderType.CASHBACK.getOrderType());
        }
        return map;
    }

    default String getEncrypt(Map<String, String> map, String key) throws Exception {
        String str = JSON.toJSONString(map);
        return AESUtil.encrypt(str, key);
    }

    default String getDecrypt(String content, String key) throws Exception {
        String str = AESUtil.decrypt(content, key);
        return str;
    }

    Map<String, String> transfer(String requestId, String merchantId, String senderId, String recieptId, String amount, String currency,
                                 String country, String reference, String orderType, String callBackURL, String payChannel) throws Exception;

    Map<String, String> transfer(String requestId, String recieptId, String amount, String reference, String orderType, String payChannel) throws Exception;

    Map<String, String> getOpayUser(String phone, String requestId, String merchantId) throws Exception;

    Map<String, String> queryUserRecordByPhone(String phone, String startTime, String requestId, String merchantId, String serviceType) throws Exception;

    /**
     * 根据订单号查询转账订单信息
     * @param requestId
     * @param merchantId
     * @param orderNo
     * @param orderType
     * @return
     * @throws Exception
     */
    TransOrder queryOrder(String requestId, String merchantId, String orderNo, String orderType) throws Exception;

}
