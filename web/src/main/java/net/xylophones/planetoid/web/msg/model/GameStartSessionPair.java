package net.xylophones.planetoid.web.msg.model;

public class GameStartSessionPair {

    private final GameStartSession player1;

    private final GameStartSession player2;

    public GameStartSessionPair(GameStartSession player1, GameStartSession player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public GameStartSession getPlayer1() {
        return player1;
    }

    public GameStartSession getPlayer2() {
        return player2;
    }
}
