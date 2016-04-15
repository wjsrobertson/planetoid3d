package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.IncomingMessage;
import net.xylophones.planetoid.web.msg.model.IncomingMessageType;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Optional;

@Component
public class IncomingMessageCreator {

    public Optional<IncomingMessage> parse(String message, Session session) {
        Optional<IncomingMessageType> maybeMessageType = IncomingMessageType.getMessageType(message);

        if (maybeMessageType.isPresent()) {
            IncomingMessageType messageType = maybeMessageType.get();
            String payload = message.substring(messageType.prefix.length());

            return Optional.of(new IncomingMessage(messageType, payload, session));
        } else {
            return Optional.empty();
        }
    }
}
