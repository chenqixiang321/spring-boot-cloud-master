package com.opay.invite.service;

import com.alibaba.fastjson.JSON;
import com.opay.invite.utils.AESUtil;

import java.util.HashMap;
import java.util.Map;

public interface TransferService {
    default Map<String,String> getParamMap(String senderId, String recieptId, String amount, String currency,
                                           String country, String reference, String orderType, String callBackURL){
        Map<String,String> map =new HashMap<>();
        map.put("senderId",senderId);
        map.put("recieptId",recieptId);
        map.put("amount",amount);
        if(currency==null || "".equals(currency)){
            currency="NGN";
        }
        map.put("currency",currency);
        if(country==null || "".equals(country)){
            country="NG";
        }
        map.put("country",country);
        map.put("reference",reference);
        map.put("orderType",orderType);
        map.put("callBackURL",callBackURL);
        return map;
    }
    default String getEncrypt(Map<String,String> map,String key) throws Exception{
        String str = JSON.toJSONString(map);
        return AESUtil.encrypt(str,key);
    }

    default String getDecrypt(String content,String key) throws Exception{
        String str = AESUtil.decrypt(content,key);
        return str;
    }
    Map<String,String> transfer(String requestId, String merchantId,String senderId, String recieptId, String amount, String currency,
                         String country, String reference, String orderType, String callBackURL) throws Exception;

    Map<String,String> transfer(String requestId,String recieptId, String amount,String reference, String orderType) throws Exception;
}