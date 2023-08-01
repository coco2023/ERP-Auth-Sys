package com.authsys.SpringSecurity.service.impl;

import com.authsys.SpringSecurity.service.MessageHandler;
import com.authsys.SpringSecurity.service.MessageService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageHandler messageHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public void sendMessageToUser(Long senderId, Long sendTo, String msg, String topic) {

        if (senderId == 0L) {
            LOGGER.info("Invalid sender " + senderId);
            return;
        }

        try {
            // enrich message with senderId and topic
            JSONObject msgJson = new JSONObject();
            msgJson.put("senderId", senderId);
            msgJson.put("topic", topic);
            msgJson.put("msg", msg);

            LOGGER.info("msgJson: " + msgJson);

            messageHandler.sendMessageToUser(sendTo, msgJson.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
