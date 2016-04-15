package net.xylophones.planetoid.web.msg.model;

public class LiveGame {

    private final String gameId;

    private final DownstreamPlayer player1;

    private final DownstreamPlayer player2;

    public LiveGame(String gameId, DownstreamPlayer player1, DownstreamPlayer player2) {
        this.gameId = gameId;
        this.player1 = player1;
        this.player2 = player2;
    }

    public String getGameId() {
        return gameId;
    }

    public DownstreamPlayer getPlayer1() {
        return player1;
    }

    public DownstreamPlayer getPlayer2() {
        return player2;
    }
}
