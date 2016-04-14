package net.xylophones.planetoid.web.msg;

import javax.websocket.Session;

public class IncomingMessage {

    private final IncomingMessageType type;
    private final String payload;
    private final Session session;

    public IncomingMessage(IncomingMessageType type, String payload, Session session) {
        this.type = type;
        this.payload = payload;
        this.session = session;
    }

    public IncomingMessageType getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public Session getSession() {
        return session;
    }
}
