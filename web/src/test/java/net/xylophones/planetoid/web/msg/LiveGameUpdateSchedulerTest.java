package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LiveGameUpdateSchedulerTest {

    @InjectMocks
    private LiveGameUpdateScheduler underTest;

    @Mock
    private LiveGameRepository liveGameRepository;

    @Mock
    private LiveGameUpdater liveGameUpdater;

    private List<LiveGame> games = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        when(liveGameRepository.getAll()).thenReturn(games);
    }

    @Test(timeout = 10_000)
    public void checkUpdatesCanStartAndStop() throws InterruptedException {
        underTest.startScheduledGameUpdates();
        underTest.stop();
    }

    @Test
    public void checkUpdatesContinueEvenAfterException() {
        // given
        LiveGame game1 = mock(LiveGame.class);
        LiveGame game2 = mock(LiveGame.class);
        games.add(game1);
        games.add(game2);
        doThrow(new RuntimeException("forced exception")).when(liveGameUpdater).updateGameAndNotifyPlayers(game1);

        // when
        underTest.startScheduledGameUpdates();
        waitTwoSeconds();

        // then
        verify(liveGameUpdater, atLeastOnce()).updateGameAndNotifyPlayers(game2);
    }

    private void waitTwoSeconds() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}