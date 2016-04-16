package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.game.PlanetoidsGameService;
import net.xylophones.planetoid.game.model.GameModelUpdateResult;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import scala.Option;
import scala.Some;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LiveGameUpdaterTest {

    @InjectMocks
    private LiveGameUpdater underTest;

    @Mock
    private PlanetoidsGameService gameService;

    @Mock
    private LiveGameUpdateNotifier liveGameUpdateNotifier;

    @Test
    @SuppressWarnings("unchecked")
    public void checkGameIsUpdatedAndPLayersNotified() {
        // given
        LiveGame liveGame = new LiveGame("game ID", null, null);
        GameModelUpdateResult updateResult = new GameModelUpdateResult(null, null);
        when(gameService.updateGame("game ID")).thenReturn(new Some<>(updateResult));

        // when
        underTest.updateGameAndNotifyPlayers(liveGame);

        // then
        verify(liveGameUpdateNotifier).notifyPlayersOfGameUpdate(liveGame, updateResult);
    }
}