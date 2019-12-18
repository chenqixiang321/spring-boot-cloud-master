package com.opay.invite.controller;


import com.opay.invite.model.response.opaycallback.OPayCallBackResponse;
import com.opay.invite.service.LuckDrawInfoService;
import com.opay.invite.stateconfig.BonusStatus;
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
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/callback")
public class OPayCallBackController {

    @Value("${opay.luckDraw.privatekey}")
    private String privatekey;
    @Autowired
    private LuckDrawInfoService luckDrawInfoService;

    @ApiOperation(value = "opay回调", notes = "自测用")
    @PostMapping("/bonus")
    public void callBack(@RequestBody OPayCallBackResponse<Map> oPayCallBackResponse) {
        Map<String, String> payload = oPayCallBackResponse.getPayload();
        String sign = sign(payload, privatekey);
        if (oPayCallBackResponse.getSha512().equals(sign)) {
            try {
                if ("SUCCESS".equals(payload.get("orderStatus"))) {
                    luckDrawInfoService.updateBonusStatus(payload.get("platformOrderNo"), BonusStatus.SUCCESS);
                } else if ("PENDING".equals(payload.get("orderStatus"))) {
                    luckDrawInfoService.updateBonusStatus(payload.get("platformOrderNo"), BonusStatus.PENDING);
                } else if ("FAIL".equals(payload.get("orderStatus"))) {
                    luckDrawInfoService.updateBonusStatus(payload.get("platformOrderNo"), BonusStatus.FAIL);
                }
            } catch (Exception e) {
                log.error("call back error", e);
            }
        } else {
            log.error("sign does not equals data{},sign{}:", sign, oPayCallBackResponse.getSha512());
        }
    }

    private String sign(Map<String, String> payload, String privateKey) {
        String format = "{orderNo:\"%s\",platformId:\"%s\",platformOrderNo:\"%s\",orderStatus:%s}";
        String signString = String.format(format,
                payload.get("orderNo"),
                payload.get("platformId"),
                payload.get("platformOrderNo"),
                payload.get("orderStatus")
        );
        String sign = HexUtils.toHexString(hmacSha3(signString, privateKey));
        return sign;
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
