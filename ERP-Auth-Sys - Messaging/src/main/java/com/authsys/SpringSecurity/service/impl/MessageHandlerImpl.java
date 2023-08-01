package com.authsys.SpringSecurity.service.impl;

import com.authsys.SpringSecurity.kafka.MessageReceiver;
import com.authsys.SpringSecurity.service.MessageHandler;
import com.authsys.SpringSecurity.websocket.WebSocketPool;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
public class MessageHandlerImpl implements MessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

    @Override
    public void addSessionToPool(Long userId, WebSocketSession session) {
        Set<WebSocketSession> userSessions = WebSocketPool.websockets.get(userId);

        if (userSessions != null) {
            userSessions.add(session);
            WebSocketPool.websockets.put(userId, userSessions);
        } else {
            Set<WebSocketSession> newUserSessions = new HashSet<>();
            newUserSessions.add(session);
            WebSocketPool.websockets.put(userId, newUserSessions);
        }
        log.info("addSession:" + userSessions + "WebSocketPool: " + WebSocketPool.websockets);
    }

    @Override
    public void sendMessageToUser(Long sendTo, String message) throws IOException {

        Set<WebSocketSession> userSessions = WebSocketPool.websockets.get(sendTo);

        if (userSessions == null) {
            return;
        }

        TextMessage textMessage = new TextMessage(message);

        for (WebSocketSession session : userSessions) {
            session.sendMessage(textMessage);
        }
        log.info("sendMessageToUser:" + userSessions + "WebSocketPool: " + WebSocketPool.websockets);
    }

    @Override
    public void removeFromSessionToPool(Long senderId, WebSocketSession session) {
        Set<WebSocketSession> userSessions = WebSocketPool.websockets.get(senderId);

        if (userSessions != null) {
            for (WebSocketSession sessionItem : userSessions) {
                if (sessionItem.equals(session)) {
                    userSessions.remove(session);
                } else {
                    LOG.info("This session is not equal hmm: " + sessionItem.hashCode() + " <> " + session.hashCode());
                }
            }
        }
        WebSocketPool.websockets.put(senderId, userSessions);
        log.info("removeFromSessionToPool:" + userSessions + "WebSocketPool: " + WebSocketPool.websockets);
    }
}
