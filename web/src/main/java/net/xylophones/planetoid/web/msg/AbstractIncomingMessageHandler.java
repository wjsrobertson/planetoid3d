package net.xylophones.planetoid.web.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractIncomingMessageHandler<T> {

    // todo - autowire
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Class<T> messageClass;

    @SuppressWarnings("unchecked")
    public AbstractIncomingMessageHandler() {
        messageClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void handleMessage(IncomingMessage message) {
        T payload = convertPayload(message);
        process(payload, message.getSession());
    }

    protected abstract void process(T payload, Session session);

    private T convertPayload(IncomingMessage message) {
        try {
            return objectMapper.readValue(message.getPayload(), messageClass);
        } catch (IOException e) {
            // todo - handle
            return null;
        }
    }

}
