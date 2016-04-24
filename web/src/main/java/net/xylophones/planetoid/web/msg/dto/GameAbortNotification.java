package net.xylophones.planetoid.web.msg.dto;

public class GameAbortNotification {

    private final String gameId;

    public GameAbortNotification(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }
}
