package net.xylophones.planetoid.web.msg;

import com.google.common.base.Stopwatch;
import net.xylophones.planetoid.web.msg.model.GameStartSession;
import net.xylophones.planetoid.web.msg.model.GameStartSessionPair;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class GameStartQueueTest {

    private GameStartQueue underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new GameStartQueue();
    }

    @Test(timeout = 10_000)
    public void checkDoesNotBlockWhenTwoAreInQueue() throws InterruptedException {
        // given
        underTest.add(new GameStartSession(null, null));
        underTest.add(new GameStartSession(null, null));

        // when
        GameStartSessionPair pair = underTest.getNextGameStartSessionPair();

        // then
        assertThat(pair).isNotNull();
    }

    @Test(timeout = 10_000)
    public void checkBlocksWhenOneIsInQueue() throws InterruptedException {
        // given
        Stopwatch stopwatch = Stopwatch.createStarted();
        underTest.add(new GameStartSession(null, null));
        addSecondEntryInNewThreadAfterTwoSeconds(underTest);

        // when
        GameStartSessionPair pair = underTest.getNextGameStartSessionPair();
        stopwatch.stop();

        // then
        assertThat(stopwatch.elapsed(TimeUnit.SECONDS)).isGreaterThanOrEqualTo(2);
    }

    private void addSecondEntryInNewThreadAfterTwoSeconds(final GameStartQueue underTest) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    // never mind
                }
                underTest.add(new GameStartSession(null, null));
            }
        }).start();
    }
}