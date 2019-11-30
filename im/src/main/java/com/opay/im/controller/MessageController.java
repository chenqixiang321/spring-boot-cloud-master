package com.opay.im.controller;

import com.opay.im.model.RongCloudMessageModel;
import com.opay.im.service.RongCloudMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;

@Controller
@RequestMapping(value = "/rongCloud")
public class MessageController {

    @Value("${rongyun.appSecret}")
    private String appSecret;

    @Autowired
    private RongCloudMessageService rongCloudMessageService;

    @PostMapping("/sync")
    public void syncMessage(HttpServletResponse response, @RequestParam String signTimestamp, @RequestParam String nonce, @RequestParam String signature,
                            @RequestParam String fromUserId,
                            @RequestParam String toUserId,
                            @RequestParam String objectName,
                            @RequestParam String content,
                            @RequestParam String channelType,
                            @RequestParam String msgTimestamp,
                            @RequestParam String msgUID,
                            @RequestParam int sensitiveType,
                            @RequestParam String source,
                            @RequestParam String[] groupUserIds) {
        try {
            String localSignature = shaEncode(appSecret + nonce + signTimestamp);
            if (signature.equals(localSignature)) {
                RongCloudMessageModel msg = new RongCloudMessageModel();
                msg.setMsgUID(msgUID);
                msg.setFromUserId(fromUserId);
                msg.setToUserId(toUserId);
                msg.setObjectName(objectName);
                msg.setContent(content);
                msg.setChannelType(channelType);
                msg.setMsgTimestamp(msgTimestamp);
                msg.setSensitiveType(sensitiveType);
                msg.setSource(source);
                msg.setGroupUserIds(String.join(",", groupUserIds));
                rongCloudMessageService.insertSelective(msg);
                response.setStatus(200);
            } else {
                response.setStatus(400);
            }
        } catch (Exception e) {
            response.setStatus(400);
        }


    }

    private String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
