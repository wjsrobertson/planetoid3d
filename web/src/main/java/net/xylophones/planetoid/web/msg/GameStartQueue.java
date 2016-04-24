package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayerPair;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class GameStartQueue {

    private static final int QUEUE_MAX_SIZE = 128;

    private BlockingQueue<DownstreamPlayer> queue = new ArrayBlockingQueue<>(QUEUE_MAX_SIZE, true);

    public void add(DownstreamPlayer gameStartSession) {
        queue.add(gameStartSession);
    }

    /*
     * Blocks until a pair is available
     *
     * // TODO - handle case where session for player 1 or player 2 has closed
     */
    public DownstreamPlayerPair getNextGameStartSessionPair() throws InterruptedException {
        DownstreamPlayer player1 = queue.take();
        DownstreamPlayer player2 = queue.take();

        return new DownstreamPlayerPair(player1, player2);
    }

    public void removeDownstreamPlayerWithSessionId(String sessionId) {
        queue.removeIf(p -> p.getSession().getId().equals(sessionId));
    }

    public int numWaiting() {
        return queue.size();
    }
}
