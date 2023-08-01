package com.prac.springkafka.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.springkafka.dto.SendMessageRequest;
import com.prac.springkafka.dto.SendMessageResponse;
import com.prac.springkafka.kafka.MessageSender;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("message")
@Log4j2

public class MessageController {

    @Autowired
    private MessageSender messageSender;

    private String topic = "topic1";

    @PostMapping(value = "/send-message", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendMessage (@RequestBody SendMessageRequest sendMessageRequest) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            // send the message
            messageSender.send(topic,
                    mapper.writeValueAsString(sendMessageRequest));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(
                "Message sent to Kafka topic",
                HttpStatus.OK
        );
    }


    }
