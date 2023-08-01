package com.prac.springkafka.kafka;

import com.prac.springkafka.service.MessageService;
import com.prac.springkafka.websocket.WebSocketPool;
import org.json.JSONObject;
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

    private final static String topic = "topic1";

    @KafkaListener(topics = topic)
    public void messageSendToUser (@Payload String message, @Headers MessageHeaders headers) {

        // convert String into JSON object
        JSONObject jsonObject = new JSONObject(message);

        if (jsonObject.get("sendTo") != null &&
                WebSocketPool.websockets.get(jsonObject.getLong("sendTo")) != null &&
                WebSocketPool.websockets.get(jsonObject.getLong("sendTo")).size() > 0
        ) {

            String accessToken = jsonObject.getString("accessToken");
            Long sendTo = jsonObject.getLong("sendTo");
            String msg = jsonObject.getString("msg");

            LOG.info("Websocket message is sent to " + sendTo);

            messageService.sendMessageToUser(accessToken, sendTo, msg, topic);


        }
        }


}
