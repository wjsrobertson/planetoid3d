package net.xylophones.planetoid.web.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Map;
import java.util.Optional;

@Component
public class IncomingMessageProcessor {

    @Autowired
    private IncomingMessageParser incomingMessageParser;

    Map<IncomingMessageType,AbstractIncomingMessageHandler> handlers;

    public void process(String message, Session session) {
        Optional<IncomingMessage> maybeMessage = incomingMessageParser.parse(message, session);
        if (maybeMessage.isPresent()) {
            IncomingMessage incomingMessage = maybeMessage.get();
            AbstractIncomingMessageHandler handler = handlers.get(incomingMessage.getType());

            handler.handleMessage(incomingMessage);
        }
    }

}
