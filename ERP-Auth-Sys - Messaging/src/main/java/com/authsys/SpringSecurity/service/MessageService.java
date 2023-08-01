package com.authsys.SpringSecurity.service;

public interface MessageService {
    void sendMessageToUser(Long senderId, Long sendTo, String msg, String topic);
}
