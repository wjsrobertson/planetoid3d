package net.xylophones.planetoid.web.msg.model;

public class DownstreamPlayerPair {

    private final DownstreamPlayer player1;

    private final DownstreamPlayer player2;

    public DownstreamPlayerPair(DownstreamPlayer player1, DownstreamPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public DownstreamPlayer getPlayer1() {
        return player1;
    }

    public DownstreamPlayer getPlayer2() {
        return player2;
    }
}
