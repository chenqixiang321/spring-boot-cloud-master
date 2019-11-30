package com.opay.im.controller;

import com.opay.im.model.RongCloudMessageModel;
import com.opay.im.service.RongCloudMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;

@Controller
@Slf4j
@RequestMapping(value = "/rongCloud")
public class MessageController {

    @Value("${rongyun.appSecret}")
    private String appSecret;

    @Autowired
    private RongCloudMessageService rongCloudMessageService;

    @PostMapping("/sync")
    public void syncMessage(HttpServletRequest request, HttpServletResponse response, @RequestParam String signTimestamp, @RequestParam String nonce, @RequestParam String signature) {
        try {
            String localSignature = shaEncode(appSecret + nonce + signTimestamp);
            if (signature.equals(localSignature)) {
                RongCloudMessageModel msg = new RongCloudMessageModel();
                msg.setMsgUID(request.getParameter("msgUID"));
                msg.setFromUserId(request.getParameter("fromUserId"));
                msg.setToUserId(request.getParameter("toUserId"));
                msg.setObjectName(request.getParameter("objectName"));
                msg.setContent(request.getParameter("content"));
                msg.setChannelType(request.getParameter("channelType"));
                msg.setMsgTimestamp(request.getParameter("msgTimestamp"));
                String sensitiveType = request.getParameter("sensitiveType");
                if (sensitiveType != null) {
                    msg.setSensitiveType(Integer.parseInt(sensitiveType));
                }
                String source = request.getParameter("source");
                if (source != null) {
                    msg.setSource(source);
                }
                String[] groupUserIds = request.getParameterValues("groupUserIds");
                if (groupUserIds.length != 0) {
                    msg.setGroupUserIds(String.join(",", groupUserIds));
                }
                rongCloudMessageService.insertSelective(msg);
                response.setStatus(200);
            } else {
                log.error("sync message Signature error,server:{},rongCloud:{}", localSignature, signature);
                response.setStatus(400);
            }
        } catch (Exception e) {
            log.error("sync message error", e);
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
