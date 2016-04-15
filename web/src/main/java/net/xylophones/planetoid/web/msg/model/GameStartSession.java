package net.xylophones.planetoid.web.msg.model;

import com.google.common.base.Objects;
import net.xylophones.planetoid.web.msg.dto.GameStartRequest;

import javax.websocket.Session;

public class GameStartSession {

    private final GameStartRequest gameStartRequest;

    private final Session session;

    public GameStartSession(GameStartRequest gameStartRequest, Session session) {
        this.gameStartRequest = gameStartRequest;
        this.session = session;
    }

    public GameStartRequest getGameStartRequest() {
        return gameStartRequest;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof GameStartSession)) {
            return false;
        }

        GameStartSession that = (GameStartSession) other;

        return Objects.equal(gameStartRequest, that.gameStartRequest) &&
                Objects.equal(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gameStartRequest, session);
    }
}
