package com.authsys.SpringSecurity.websocket;

import com.authsys.SpringSecurity.entity.User;
import com.authsys.SpringSecurity.kafka.MessageSender;
import com.authsys.SpringSecurity.repository.UserRepository;
import com.authsys.SpringSecurity.service.MessageHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageHandler messageHandler;

    @Autowired
    private MessageSender messageSender;

    private String currentTopic = "topic0727";

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        String parameters[] = session.getUri().getQuery().split("=");

        if (parameters.length == 2 && parameters[0].equals("senderId")) {

            Long senderId = Long.valueOf(parameters[1]);

            if (senderId == 0L) {
                return;
            }

            messageHandler.removeFromSessionToPool(senderId, session);
        }
    }

    @Override
    public void afterConnectionEstablished (WebSocketSession session) throws  Exception {
        String parameters[] = session.getUri().getQuery().split("=");

        if (parameters.length == 2 && parameters[0].equals("senderId")) {
            User sender = userRepository.findById(Long.parseLong(parameters[0])).get();
            if (sender == null) {
                return;
            }
            Long senderId = sender.getId();

            messageHandler.addSessionToPool(senderId, session);

        } else {
            session.close();
        }
    }

    @Override
    protected void handleTextMessage (WebSocketSession session, TextMessage textMessage) throws Exception {

        JSONObject jsonObject = new JSONObject(textMessage.getPayload());
        String topic = jsonObject.getString("topic");
        JSONObject message = jsonObject.getJSONObject("message");

        // only SEND_MESSAGE topic is available
        if (topic == null && !topic.equals(currentTopic)) {
            return;
        }
        messageSender.send(topic, message.toString());
    }
}
