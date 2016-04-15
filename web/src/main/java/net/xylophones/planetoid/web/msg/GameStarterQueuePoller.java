package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.DownstreamPlayerPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class GameStarterQueuePoller {

    private volatile boolean running = true;

    @Autowired
    private GameStartQueue gameStartQueue;

    @Autowired
    private GameStarter gameStarter;

    @PreDestroy
    public void stop() {
        running = false;
    }

    @PostConstruct
    public void pollGameStartQueueAndStartGames() {
        Thread thread = new Thread(() -> {
            while (running) {
                try {
                    DownstreamPlayerPair nextDownstreamPlayerPair = gameStartQueue.getNextGameStartSessionPair();
                    gameStarter.startGame(nextDownstreamPlayerPair);
                } catch (InterruptedException e) {
                    return;
                } catch (Exception e) {
                    // keep on trucking
                }
            }
        });
        thread.setName("Game start queue poller");
        thread.start();
    }
}
