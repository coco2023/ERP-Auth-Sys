package com.prac.springkafka.service.impl;

import com.prac.springkafka.kafka.MessageReceiver;
import com.prac.springkafka.repository.cache.CacheRepository;
import com.prac.springkafka.service.MessageHandler;
import com.prac.springkafka.service.MessageService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private CacheRepository cacheRepository;

    @Override
    public void sendMessageToUser(String accessToken, Long sendTo, String msg, String topic) {

        Long senderUserId = 0L;

        // first: find the senderId in the cache
        String senderId = cacheRepository.getUserIdByAccessToken(accessToken);

        // second: find the senderId in the database
        if (senderId == null) {
            User sender = userRepository.findByToken(accessToken);
            if (sender != null) {
                senderUserId = sender.getUserId();
            }
        } else {
            senderUserId = Long.valueOf(senderId);
        }
        if (senderUserId == 0L) {
            LOGGER.info("Invalid sender " + senderUserId);
            return;
        }

        try {
            // enrich message with senderId and topic
            JSONObject msgJson = new JSONObject();
            msgJson.put("msg", msg);
            msgJson.put("senderId", senderUserId);
            msgJson.put("topic", topic);

            LOGGER.info("msgJson: " + msgJson);

            messageHandler.sendMessageToUser(sendTo, msgJson.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

    }
}
