package net.xylophones.planetoid.web.msg;

import com.google.common.base.Stopwatch;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayerPair;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.Session;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameStartQueueTest {

    private GameStartQueue underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new GameStartQueue();
    }

    @Test(timeout = 10_000)
    public void checkDoesNotBlockWhenTwoAreInQueue() throws InterruptedException {
        // given
        underTest.add(new DownstreamPlayer(null, null, null));
        underTest.add(new DownstreamPlayer(null, null, null));

        // when
        DownstreamPlayerPair pair = underTest.getNextGameStartSessionPair();

        // then
        assertThat(pair).isNotNull();
    }

    @Test(timeout = 10_000)
    public void checkBlocksWhenOneIsInQueue() throws InterruptedException {
        // given
        Stopwatch stopwatch = Stopwatch.createStarted();
        underTest.add(new DownstreamPlayer(null, null, null));
        addSecondEntryInNewThreadAfterTwoSeconds(underTest);

        // when
        DownstreamPlayerPair pair = underTest.getNextGameStartSessionPair();
        stopwatch.stop();

        // then
        assertThat(stopwatch.elapsed(TimeUnit.SECONDS)).isGreaterThanOrEqualTo(2);
    }

    private void addSecondEntryInNewThreadAfterTwoSeconds(final GameStartQueue underTest) {
        new Thread(() -> {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                // never mind
            }
            underTest.add(new DownstreamPlayer(null, null, null));
        }).start();
    }

    @Test
    public void checkRemoveDownstreamPlayerWithSessionIdDoesRemove() {
        // given
        Session session = mock(Session.class);
        when(session.getId()).thenReturn("session id");
        underTest.add(new DownstreamPlayer(null, null, session));

        // when
        underTest.removeDownstreamPlayerWithSessionId("session id");

        // then
        assertThat(underTest.numWaiting()).isEqualTo(0);
    }
}