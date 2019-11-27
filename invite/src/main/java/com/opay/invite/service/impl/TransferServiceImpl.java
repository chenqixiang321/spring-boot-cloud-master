package com.opay.invite.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.opay.invite.service.TransferService;
import com.opay.invite.transferconfig.TransferConfig;
import com.opay.invite.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
   private TransferConfig transferConfig;

    @Override
    public Map<String,String> transfer(String requestId, String merchantId, String senderId, String recieptId,
                                String amount, String currency, String country, String reference,
                                String orderType, String callBackURL) throws Exception{
        Map<String,String> map = getParamMap(senderId,recieptId,amount, currency, country, reference, orderType, callBackURL);
        String str = getEncrypt(map,transferConfig.getAesKey());
        Map<String,Object> paramMap = new HashMap<>();
        String result = HttpClientUtil.post(paramMap,transferConfig.getDomain()+transferConfig.getUrl());
        if(result!=null && "-1".equals(result)){//超时异常
            Map<String,String> rMap = new HashMap<>();
            rMap.put("code","504");
            return rMap;
        }
        if(result!=null && !"".equals(result)){
            String resultStr = getDecrypt(result, transferConfig.getAesKey());
            Map<String,String> rMap = JSONObject.parseObject(resultStr,Map.class);
            return rMap;
        }
        return null;
    }

    @Override
    public Map<String, String> transfer(String requestId,String recieptId, String amount, String reference, String orderType) throws Exception {
        return this.transfer(requestId,transferConfig.getMerchantId(),transferConfig.getMerchantId(),recieptId,
                amount,null,null,reference, orderType,null);
    }
}
