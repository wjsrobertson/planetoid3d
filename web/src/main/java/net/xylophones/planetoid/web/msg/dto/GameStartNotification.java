package net.xylophones.planetoid.web.msg.dto;

public class GameStartNotification {

    private final boolean isPlayerOne;

    private final String gameId;

    private final String opponentName;

    public GameStartNotification(boolean isPlayerOne, String gameId, String opponentName) {
        this.isPlayerOne = isPlayerOne;
        this.gameId = gameId;
        this.opponentName = opponentName;
    }

    public boolean getIsPlayerOne() {
        return isPlayerOne;
    }

    public String getGameId() {
        return gameId;
    }

    public String getOpponentName() {
        return opponentName;
    }
}
