package com.prac.springkafka.service;

import java.io.IOException;

public interface MessageHandler {
    public void sendMessageToUser(Long userId, String message) throws IOException;
}
