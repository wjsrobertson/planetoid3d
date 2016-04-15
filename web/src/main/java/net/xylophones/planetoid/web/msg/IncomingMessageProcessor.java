package net.xylophones.planetoid.web.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IncomingMessageProcessor {

    @Autowired
    private IncomingMessageParser incomingMessageParser;

    @Autowired
    private Set<AbstractIncomingMessageHandler> handlerSet;

    private Map<IncomingMessageType,AbstractIncomingMessageHandler> handlers = new ConcurrentHashMap<>();

    @PostConstruct
    private void initialiseHandlerMap() {
        if (handlerSet != null) {
            handlerSet.forEach(handler -> handlers.put(handler.supportedMessageType(), handler));
        }
    }

    public void process(String message, Session session) {
        Optional<IncomingMessage> maybeMessage = incomingMessageParser.parse(message, session);
        if (maybeMessage.isPresent()) {
            IncomingMessage incomingMessage = maybeMessage.get();
            AbstractIncomingMessageHandler handler = handlers.get(incomingMessage.getType());

            handler.handleMessage(incomingMessage);
        }
    }
}
