package net.xylophones.planetoid.web.msg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;

@Component
public class OutboundMessageSender {

    @Autowired
    private ObjectMapper objectMapper;

    public void send(Session session, Object message) {
        String messageString = createMessageString(message);

        try {
            session.getBasicRemote().sendText(messageString);
        } catch (IOException e) {
            // TODO - handle me
        }
    }

    public void sendAsync(Session session, Object message) {
        String messageString = createMessageString(message);

        session.getAsyncRemote().sendText(messageString);
    }

    private String createMessageString(Object message) {
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            // TODO - handle me
        }

        return message.getClass().getSimpleName() + ":" + payload;
    }
}
