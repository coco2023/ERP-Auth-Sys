package com.prac.springkafka.service;

public interface MessageService {

    void sendMessageToUser(String accessToken, Long sendTo, String msg, String topic);

}
