package net.xylophones.planetoid.web.msg;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class LiveGameUpdateScheduler {

    private static final long NO_START_DELAY = 0L;

    private static final long PAUSE_BETWEEN_UPDATES_MS = 40L;

    @Autowired
    private LiveGameRepository liveGameRepository;

    @Autowired
    private LiveGameUpdater liveGameUpdater;

    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder().setNameFormat("game update thread ").build()
    );

    @PreDestroy
    public void stop() throws InterruptedException {
        service.shutdownNow();
        service.awaitTermination(10, TimeUnit.SECONDS);
    }

    @PostConstruct
    public void startScheduledGameUpdates() {
        service.scheduleWithFixedDelay(() -> {
            liveGameRepository.getAll().forEach(liveGame -> {
                try {
                    liveGameUpdater.updateGameAndNotifyPlayers(liveGame);
                } catch (Exception e) {
                    // keep on trucking
                    // TODO - handle. IOException will be quite common if player ends websocket session
                }
            });
        }, NO_START_DELAY, PAUSE_BETWEEN_UPDATES_MS, TimeUnit.MILLISECONDS);
    }
}
