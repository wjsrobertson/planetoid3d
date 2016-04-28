package net.xylophones.planetoid.web.msg;

import com.google.common.base.Stopwatch;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayerPair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.websocket.Session;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameStartQueueTest {

    private GameStartQueue underTest;

    @Mock
    Session session1;

    @Mock
    Session session2;

    @Mock
    Session session3;

    @Before
    public void setUp() throws Exception {
        underTest = new GameStartQueue();

        when(session1.isOpen()).thenReturn(true);
        when(session2.isOpen()).thenReturn(true);
        when(session3.isOpen()).thenReturn(true);
    }

    @Test(timeout = 10_000)
    public void checkDoesNotBlockWhenTwoAreInQueue() throws InterruptedException {
        // given
        underTest.add(new DownstreamPlayer("bob", null, session1));
        underTest.add(new DownstreamPlayer("frank", null, session2));

        // when
        DownstreamPlayerPair pair = underTest.getNextGameStartSessionPair();

        // then
        assertThat(pair).isNotNull();
    }

    @Test(timeout = 10_000)
    public void checkBlocksWhenOneIsInQueue() throws InterruptedException {
        // given
        Stopwatch stopwatch = Stopwatch.createStarted();
        underTest.add(new DownstreamPlayer("bob", null, session1));
        addSecondEntryInNewThreadAfterTwoSeconds(underTest);

        // when
        DownstreamPlayerPair pair = underTest.getNextGameStartSessionPair();
        stopwatch.stop();

        // then
        assertThat(stopwatch.elapsed(TimeUnit.SECONDS)).isGreaterThanOrEqualTo(2);
    }

    @Test(timeout = 10_000)
    public void checkBlocksWhenOneSessionIsClosed() throws InterruptedException {
        // given
        reset(session1);
        when(session1.isOpen()).thenReturn(false);
        Stopwatch stopwatch = Stopwatch.createStarted();
        underTest.add(new DownstreamPlayer("bob", null, session1));
        addTwoEntriesInNewThreadAfterTwoSeconds(underTest);

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
            underTest.add(new DownstreamPlayer("frank", null, session2));
        }).start();
    }


    private void addTwoEntriesInNewThreadAfterTwoSeconds(GameStartQueue underTest) {
        new Thread(() -> {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                // never mind
            }
            underTest.add(new DownstreamPlayer("frank", null, session2));
            underTest.add(new DownstreamPlayer("john", null, session3));
        }).start();
    }


    @Test
    public void checkRemoveDownstreamPlayerWithSessionIdDoesRemove() {
        // given
        when(session1.getId()).thenReturn("session id");
        underTest.add(new DownstreamPlayer("bob", null, session1));

        // when
        underTest.removeDownstreamPlayerWithSessionId("session id");

        // then
        assertThat(underTest.numWaiting()).isEqualTo(0);
    }
}