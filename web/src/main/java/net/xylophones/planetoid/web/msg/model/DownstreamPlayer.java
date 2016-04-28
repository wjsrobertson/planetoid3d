package net.xylophones.planetoid.web.msg.model;

import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof DownstreamPlayer)) {
            return false;
        }

        DownstreamPlayer that = (DownstreamPlayer) other;

        return Objects.equal(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }
}
