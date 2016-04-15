package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.GameStartSession;
import net.xylophones.planetoid.web.msg.model.GameStartSessionPair;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class GameStartQueue {

    private static final int QUEUE_MAX_SIZE = 128;

    private BlockingQueue<GameStartSession> queue = new ArrayBlockingQueue<>(QUEUE_MAX_SIZE, true);

    public void add(GameStartSession gameStartSession) {
        queue.add(gameStartSession);
    }

    public GameStartSessionPair getNextGameStartSessionPair() throws InterruptedException {
        GameStartSession player1 = queue.take();
        GameStartSession player2 = queue.take();

        return new GameStartSessionPair(player1, player2);
    }
}
