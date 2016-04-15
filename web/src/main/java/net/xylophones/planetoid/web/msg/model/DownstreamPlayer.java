package net.xylophones.planetoid.web.msg.model;

import javax.websocket.Session;

public class DownstreamPlayer {

    private final String userId;

    private final String name;

    private final Session session;

    public DownstreamPlayer(String userId, String name, Session session) {
        this.userId = userId;
        this.name = name;
        this.session = session;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Session getSession() {
        return session;
    }
}
