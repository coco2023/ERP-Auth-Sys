package com.prac.springkafka.service.impl;

import com.prac.springkafka.service.MessageHandler;
import com.prac.springkafka.websocket.WebSocketPool;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;

@Service
public class MessageHandlerImpl implements MessageHandler {

    @Override
    public void sendMessageToUser(Long userId, String message) throws IOException {

        Set<WebSocketSession> userSessions = WebSocketPool.websockets.get(userId);

        if (userSessions == null) {
            return;
        }

        TextMessage textMessage = new TextMessage(message);
        for (WebSocketSession session : userSessions) {
            session.sendMessage(textMessage);
        }

    }
}
