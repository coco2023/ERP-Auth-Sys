package com.authsys.SpringSecurity.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface MessageHandler {

    public void addSessionToPool(Long userId, WebSocketSession session);

    public void sendMessageToUser(Long sendTo, String message) throws IOException;

    void removeFromSessionToPool(Long senderId, WebSocketSession session);
}
