package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayerPair;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.google.common.collect.Sets.newHashSet;

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
        Set<DownstreamPlayer> nextPair = newHashSet();

        while (nextPair.size() < 2) {
            DownstreamPlayer nextPlayer = queue.take();
            nextPair.add(nextPlayer);

            nextPair.removeIf(player -> !player.getSession().isOpen());
        }

        DownstreamPlayer[] objects = nextPair.toArray(new DownstreamPlayer[2]);
        return new DownstreamPlayerPair(objects[0], objects[1]);
    }

    public void removeDownstreamPlayerWithSessionId(String sessionId) {
        queue.removeIf(p -> p.getSession().getId().equals(sessionId));
    }

    public int numWaiting() {
        return queue.size();
    }
}
