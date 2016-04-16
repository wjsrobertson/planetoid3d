package net.xylophones.planetoid.web.msg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.io.IOException;

// TODO - handle the message send exceptions
@Component
public class MessageSender {

    @Autowired
    private ObjectMapper objectMapper;

    public void send(Session session, Object message) {
        try {
            String messageString = createMessageString(message);
            session.getBasicRemote().sendText(messageString);
        } catch (IOException e) {
            throw new MessageSendException("Problem sending text message ", e, session);
        }
    }

    public void sendAsync(Session session, Object message) {
        try {
            String messageString = createMessageString(message);
            session.getAsyncRemote().sendText(messageString);
        } catch (IOException e) {
            throw new MessageSendException("Problem sending async text message ", e, session);
        }
    }

    private String createMessageString(Object message) throws JsonProcessingException {
        String payload = objectMapper.writeValueAsString(message);

        return message.getClass().getSimpleName() + ":" + payload;
    }
}
