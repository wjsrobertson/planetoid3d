package net.xylophones.planetoid.web.msg;

import javax.websocket.Session;

public class MessageSendException extends RuntimeException {

    private final Session session;

    public MessageSendException(String message, Throwable throwable, Session session) {
        super(message, throwable);
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
