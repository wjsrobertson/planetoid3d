package net.xylophones.planetoid.web.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.xylophones.planetoid.web.msg.model.IncomingMessage;
import net.xylophones.planetoid.web.msg.model.IncomingMessageType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public abstract class AbstractIncomingMessageProcessor<T> {

    @Autowired
    private ObjectMapper objectMapper;

    private final Class<T> messageClass;

    private final IncomingMessageType incomingMessageType;

    @SuppressWarnings("unchecked")
    public AbstractIncomingMessageProcessor(IncomingMessageType incomingMessageType) {
        this.incomingMessageType = incomingMessageType;
        messageClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void handleMessage(IncomingMessage message) {
        Optional<T> maybePayload = convertPayload(message);
        maybePayload.ifPresent(payload -> process(payload, message.getSession()));
    }

    protected abstract void process(T payload, Session session);

    private Optional<T> convertPayload(IncomingMessage message) {
        try {
            return Optional.of(objectMapper.readValue(message.getPayload(), messageClass));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public IncomingMessageType supportedMessageType() {
        return incomingMessageType;
    }
}
