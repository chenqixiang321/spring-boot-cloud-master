package com.opay.im.controller;

import com.opay.im.config.OpayConfig;
import com.opay.im.model.response.opaycallback.OPayCallBackResponse;
import com.opay.im.model.response.opaycallback.PayloadResponse;
import com.opay.im.service.LuckyMoneyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.HexUtils;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@RestController
@Slf4j
@RequestMapping(value = "/callback")
public class OPayCallBackController {

    @Autowired
    private LuckyMoneyService luckyMoneyService;
    @Autowired
    private OpayConfig opayConfig;

    @ApiOperation(value = "opay回调", notes = "自测用")
    @PostMapping
    public void callBack(@RequestBody OPayCallBackResponse oPayCallBackResponse) {
        PayloadResponse payload = oPayCallBackResponse.getPayload();
        String data = "{Amount:\"" + payload.getAmount() + "\",Currency:\"" + payload.getCurrency() + "\",Reference:\"" + payload.getReference() + "\",Refunded:" + (payload.isRefunded() ? "t" : "f") + ",Status:\"" + payload.getStatus() + "\",Timestamp:\"" + payload.getTimestamp() + "\",Token:\"" + payload.getToken() + "\",TransactionID:\"" + payload.getTransactionId() + "\"}";
        if (oPayCallBackResponse.getSha512().equals(HexUtils.toHexString(hmacSha3(data, opayConfig.getPrivatekey())))) {
            try {
                String business = oPayCallBackResponse.getPayload().getReference().split(":")[0];
                if("SLM".equals(business)){ //发红包
                    luckyMoneyService.updatePayStatus(oPayCallBackResponse);
                }

            } catch (Exception e) {
                log.error("call back error", e);
            }
        } else {
            log.error("sign does not equals,data{},sign{}:", data, oPayCallBackResponse.getSha512());
        }
    }

    private byte[] hmacSha3(String text, String key) {
        HMac hmac = new HMac(new SHA3Digest(512));
        hmac.init(new KeyParameter(key.getBytes(Charset.forName("utf-8"))));
        byte[] result = new byte[hmac.getMacSize()];
        byte[] bytes = text.getBytes(Charset.forName("utf-8"));
        hmac.update(bytes, 0, bytes.length);
        hmac.doFinal(result, 0);
        return result;
    }

}
