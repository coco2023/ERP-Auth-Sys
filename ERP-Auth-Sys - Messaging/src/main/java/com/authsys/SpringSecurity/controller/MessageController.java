package com.authsys.SpringSecurity.controller;

import com.authsys.SpringSecurity.kafka.MessageSender;
import com.authsys.SpringSecurity.model.SendMessageRequest;
import com.authsys.SpringSecurity.model.SendMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@Log4j2

public class MessageController {

    @Autowired
    private MessageSender messageSender;

    private String topic = "topic0727";

    @RequestMapping(value = "/send-message", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGER','ROLE_ADMIN')")
    public ResponseEntity<SendMessageResponse> sendMessage (@RequestBody SendMessageRequest sendMessageRequest) {

        ObjectMapper mapper = new ObjectMapper();

        SendMessageResponse sendMessageResponse = null;

        try {
            // send the message
            messageSender.send(topic,
                    mapper.writeValueAsString(sendMessageRequest));

            sendMessageResponse = new SendMessageResponse().builder()
                    .message(sendMessageRequest.toString())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(
                sendMessageResponse,
                HttpStatus.OK
        );
    }

}
