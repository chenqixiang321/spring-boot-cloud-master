package com.opay.invite.backstage.service.dto;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.hsf.HSFJSONUtils;
import com.opay.invite.backstage.config.TransferConfig;
import com.opay.invite.backstage.dto.TransOrder;
import com.opay.invite.backstage.service.RpcService;
import com.opay.invite.backstage.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RpcServiceImpl implements RpcService {


    @Autowired
    private TransferConfig transferConfig;

    @Override
    public Map<String, String> transfer(String requestId, String merchantId, String senderId, String recieptId,
                                        String amount, String currency, String country, String reference,
                                        String orderType, String callBackURL, String payChannel) throws Exception {
        if (callBackURL == null || "".equals(callBackURL)) {
            callBackURL = transferConfig.getTransferNotify();
        }
        Map<String, String> map = getParamMap(senderId, recieptId, amount, currency, country, reference, orderType, callBackURL, payChannel);
        log.info("transfer:{}", JSON.toJSONString(map));
        String str = getEncrypt(map, transferConfig.getAesKey());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("requestId", requestId);
        paramMap.put("merchantId", merchantId);
        paramMap.put("data", str);
        String result = HttpClientUtil.postEntity(paramMap, transferConfig.getDomain() + transferConfig.getUrl());
        if (result != null && "-1".equals(result)) {//超时异常
            Map<String, String> rMap = new HashMap<>();
            rMap.put("code", "504");
            return rMap;
        }
        if (result != null && !"".equals(result)) {
            Map<String, String> rMap = JSONObject.parseObject(result, Map.class);
            String dataStr = rMap.get("data");
            if (dataStr != null && !"".equals(dataStr)) {
                String resultStr = getDecrypt(dataStr, transferConfig.getAesKey());
                Map<String, String> aMap = JSONObject.parseObject(resultStr, Map.class);
                rMap.putAll(aMap);
            }
            return rMap;
        }
        return null;
    }

    @Override
    public Map<String, String> transfer(String requestId, String recieptId, String amount, String reference, String orderType, String payChannel) throws Exception {
        return this.transfer(requestId, transferConfig.getMerchantId(), transferConfig.getMerchantId(), recieptId,
                amount, null, null, reference, orderType, null, payChannel);
    }

    @Override
    public Map<String, String> getOpayUser(String phone, String requestId, String merchantId) throws Exception {
        if (merchantId == null || "".equals(merchantId)) {
            merchantId = transferConfig.getMerchantId();
        }
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", phone);
        String str = getEncrypt(map, transferConfig.getAesKey());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("requestId", requestId);
        paramMap.put("merchantId", merchantId);
        paramMap.put("data", str);
        String result = HttpClientUtil.postEntity(paramMap, transferConfig.getDomain() + transferConfig.getUserUrl());
        if (result == null || "".equals(result) || "-1".equals(result)) {
            return null;
        }
        if (result != null && !"".equals(result)) {
            Map<String, String> rMap = JSONObject.parseObject(result, Map.class);
            String dataStr = rMap.get("data");
            if ("00000".equals(rMap.get("code"))) {
                if (dataStr != null && !"".equals(dataStr)) {
                    String resultStr = getDecrypt(dataStr, transferConfig.getAesKey());
                    rMap = JSONObject.parseObject(resultStr, Map.class);
                    return rMap;
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, String> queryUserRecordByPhone(String phone, String startTime, String requestId, String merchantId, String serviceType) throws Exception {
        if (merchantId == null || "".equals(merchantId)) {
            merchantId = transferConfig.getMerchantId();
        }
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("startTime", startTime);
        map.put("serviceType", serviceType);
        String str = getEncrypt(map, transferConfig.getAesKey());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("requestId", requestId);
        paramMap.put("merchantId", merchantId);
        paramMap.put("data", str);
        String result = HttpClientUtil.postEntity(paramMap, transferConfig.getDomain() + transferConfig.getUserRecordUrl());
        if (result == null || "".equals(result) || "-1".equals(result)) {
            return null;
        }
        if (result != null && !"".equals(result)) {
            Map<String, String> rMap = JSONObject.parseObject(result, Map.class);
            String dataStr = rMap.get("data");
            if ("00000".equals(rMap.get("code"))) {
                if (dataStr != null && !"".equals(dataStr)) {
                    String resultStr = getDecrypt(dataStr, transferConfig.getAesKey());
                    JSONObject jsonObjectMap = JSONObject.parseObject(resultStr);
                    Map or = jsonObjectMap.getObject("records", Map.class);
                    return or;
                }
            }
        }
        return null;
    }

    @Override
    public TransOrder queryOrder(String requestId, String merchantId, String orderNo, String orderType) throws Exception {
        if (merchantId == null || "".equals(merchantId)) {
            merchantId = transferConfig.getMerchantId();
        }

        Map<String, String> param = buildQueryOrderParam(requestId, merchantId, orderNo, orderType);
        String url = transferConfig.getDomain() + transferConfig.getUserRecordUrl();
        log.info("RpcServiceImpl.queryOrder request url:{}, param:{}" , url, param);
        String result = HttpClientUtil.postEntity(param, transferConfig.getDomain() + transferConfig.getQueryOrderUrl());
        log.info("RpcServiceImpl.queryOrder response:{}" , result);
        if (result == null || "".equals(result) || "-1".equals(result)) {
            return null;
        }
        if (result != null && !"".equals(result)) {
            return buildTransOrder(result);
        }
        return null;
    }

    private Map<String, String> buildQueryOrderParam(String requestId, String merchantId, String orderNo, String orderType) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("reference", requestId);
        map.put("orderNo", orderNo);
        map.put("orderType", orderType);
        log.info("RpcServiceImpl.queryOrder, data:{}", map);
        String str = getEncrypt(map, transferConfig.getAesKey());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("requestId", requestId);
        paramMap.put("merchantId", merchantId);
        paramMap.put("data", str);
        return paramMap;
    }

    /**
     * 构建查询订单响应信息
     * @param result
     * @return
     * @throws Exception
     */
    private TransOrder buildTransOrder(String result) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(result);
        String code = jsonObject.getString("code");
        if (!"00000".equals(code)) {
            return null;
        }

        String data = jsonObject.getString("data");
        String dataStr = getDecrypt(data, transferConfig.getAesKey());
        if (StringUtils.isEmpty(dataStr)) {
            return null;

        }

        return JSON.parseObject(dataStr, TransOrder.class);
    }

}
