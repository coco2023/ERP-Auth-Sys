package com.authsys.SpringSecurity.kafka;

import com.authsys.SpringSecurity.websocket.WebSocketPool;
import org.json.JSONObject;
import com.authsys.SpringSecurity.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiver {

    @Autowired
    private MessageService messageService;

    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    private final static String topic = "topic0727";

    @KafkaListener(topics = topic)
    public void messagesSendToUser(@Payload String message, @Headers MessageHeaders headers) {

        // convert String into JSON object
        JSONObject jsonObject = new JSONObject(message);
        LOG.info("***jsonObject:" + jsonObject + ", headers:" + headers + " sendTo: " + jsonObject.get("sendTo") + " web:" + WebSocketPool.websockets.get(jsonObject.getLong("sendTo")));

        if (jsonObject.get("sendTo") != null
//                WebSocketPool.websockets.get(jsonObject.getLong("sendTo")) != null &&
//                WebSocketPool.websockets.get(jsonObject.getLong("sendTo")).size() > 0
        ) {

            Long senderId = jsonObject.getLong("senderId");
            Long sendTo = jsonObject.getLong("sendTo");
            String msg = jsonObject.getString("msg");

            LOG.info("Websocket message is sent to " + sendTo);
            messageService.sendMessageToUser(senderId, sendTo, msg, topic);

        } else {
            LOG.info("Websocket session not found for given sendTo");
        }
    }
}
