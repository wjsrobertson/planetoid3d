package net.xylophones.planetoid.web.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.websocket.Session;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public abstract class AbstractIncomingMessageHandler<T> {

    @Autowired
    private ObjectMapper objectMapper;

    private final Class<T> messageClass;

    @SuppressWarnings("unchecked")
    public AbstractIncomingMessageHandler() {
        messageClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void handleMessage(IncomingMessage message) {
        Optional<T> maybePayload = convertPayload(message);
        maybePayload.ifPresent(payload -> process(payload, message.getSession()));
    }

    protected abstract void process(T payload, Session session);

    public abstract IncomingMessageType supportedMessageType();

    private Optional<T> convertPayload(IncomingMessage message) {
        try {
            return Optional.of(objectMapper.readValue(message.getPayload(), messageClass));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
