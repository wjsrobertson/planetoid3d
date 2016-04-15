package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.DownstreamPlayerPair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameStarterQueuePollerTest {

    @InjectMocks
    private GameStarterQueuePoller underTest;

    @Mock
    private GameStartQueue gameStartQueue;

    @Mock
    private GameStarter gameStarter;

    @Mock
    private DownstreamPlayerPair downstreamPlayerPair;

    @Test
    public void checkQueueNotPolledOnceStopped() throws InterruptedException {
        // given
        underTest.stop();

        // when
        underTest.pollGameStartQueueAndStartGames();
        waitForASecond();

        // then
        verifyNoMoreInteractions(gameStartQueue);
    }

    @Test
    public void checkSessionPairsArePulledOffQueue() throws InterruptedException {
        // given
        when(gameStartQueue.getNextGameStartSessionPair()).thenReturn(downstreamPlayerPair);

        // when
        underTest.pollGameStartQueueAndStartGames();
        waitForASecond();

        // then
        verify(gameStarter, atLeastOnce()).startGame(downstreamPlayerPair);
    }

    private void waitForASecond() throws InterruptedException {
        Thread.sleep(1_000);
    }

}