package net.xylophones.planetoid.web.msg;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractIncomingMessageHandler<T> {

    @Autowired
    private Gson gson = new Gson();

    private final Class<T> messageClass;

    @SuppressWarnings("unchecked")
    public AbstractIncomingMessageHandler() {
        messageClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void handleMessage(IncomingMessage message) {
        T payload = convertPayload(message);
    }

    protected abstract void process(T payload, Session session);

    private T convertPayload(IncomingMessage message) {
        return gson.fromJson(message.getPayload(), messageClass);
    }

}
