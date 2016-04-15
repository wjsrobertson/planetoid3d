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

    @Autowired
    private LiveGameRepository liveGameRepository;

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder().setNameFormat("game update thread ").build()
    );

    @PreDestroy
    public void stop() {
        service.shutdownNow();
    }

    @PostConstruct
    public void startScheduledGameUpdates() {
        service.scheduleWithFixedDelay(() -> {
            liveGameRepository.getAll().forEach(
                    liveGame -> System.out.print("")
            );
        }, 0L, 20L, TimeUnit.MILLISECONDS);
    }
}
