package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.IncomingMessage;
import net.xylophones.planetoid.web.msg.model.IncomingMessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IncomingMessageRouter {

    @Autowired
    private IncomingMessageCreator incomingMessageCreator;

    @Autowired
    private Set<AbstractIncomingMessageProcessor> handlerSet;

    private Map<IncomingMessageType,AbstractIncomingMessageProcessor> handlers = new ConcurrentHashMap<>();

    @PostConstruct
    private void initialiseHandlerMap() {
        if (handlerSet != null) {
            handlerSet.forEach(handler -> handlers.put(handler.supportedMessageType(), handler));
        }
    }

    public void process(String message, Session session) {
        Optional<IncomingMessage> maybeMessage = incomingMessageCreator.parse(message, session);
        if (maybeMessage.isPresent()) {
            IncomingMessage incomingMessage = maybeMessage.get();
            AbstractIncomingMessageProcessor handler = handlers.get(incomingMessage.getType());

            handler.handleMessage(incomingMessage);
        }
    }
}
