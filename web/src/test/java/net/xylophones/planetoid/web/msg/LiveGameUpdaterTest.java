package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.game.PlanetoidsGameService;
import net.xylophones.planetoid.game.model.GameEvent;
import net.xylophones.planetoid.game.model.GameModelUpdateResult;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import scala.Enumeration;
import scala.Some;
import scala.collection.immutable.Set;
import scala.collection.immutable.Set$;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LiveGameUpdaterTest {

    @InjectMocks
    private LiveGameUpdater underTest;

    @Mock
    private PlanetoidsGameService gameService;

    @Mock
    private LiveGameUpdateNotifier liveGameUpdateNotifier;

    @Mock
    private LiveGameRepository liveGameRepository;

    @Test
    @SuppressWarnings("unchecked")
    public void checkGameIsUpdatedAndPLayersNotified() {
        // given
        LiveGame liveGame = new LiveGame("game ID", null, null);
        GameModelUpdateResult updateResult = new GameModelUpdateResult(null, getEmptyEventSet());
        when(gameService.updateGame("game ID")).thenReturn(new Some<>(updateResult));

        // when
        underTest.updateGameAndNotifyPlayers(liveGame);

        // then
        verify(liveGameUpdateNotifier).notifyPlayersOfGameUpdate(liveGame, updateResult);
    }

    @Test
    public void checkGameIsRemovedOnGameOverEvent() {
        // given
        LiveGame liveGame = new LiveGame("game ID", null, null);
        Enumeration.Value gameOverEvent = GameEvent.withName("GameOver");
        Set<Enumeration.Value> eventSet = getEventSetWithEvent(gameOverEvent);
        GameModelUpdateResult updateResult = new GameModelUpdateResult(null, eventSet);
        when(gameService.updateGame("game ID")).thenReturn(new Some<>(updateResult));

        // when
        underTest.updateGameAndNotifyPlayers(liveGame);

        // then
        verify(liveGameRepository).remove(liveGame);
    }

    @Test
    public void checkGameIsNotRemovedWhenNoGameOverEvent() {
        // given
        LiveGame liveGame = new LiveGame("game ID", null, null);
        GameModelUpdateResult updateResult = new GameModelUpdateResult(null, getEmptyEventSet());
        when(gameService.updateGame("game ID")).thenReturn(new Some<>(updateResult));

        // when
        underTest.updateGameAndNotifyPlayers(liveGame);

        // then
        verifyNoMoreInteractions(liveGameRepository);
    }

    @SuppressWarnings("unchecked")
    private Set<Enumeration.Value> getEmptyEventSet() {
        return (Set<Enumeration.Value>) Set$.MODULE$.newBuilder().result();
    }

    @SuppressWarnings("unchecked")
    private Set<Enumeration.Value> getEventSetWithEvent(Enumeration.Value gameOverEvent) {
        return (Set<Enumeration.Value>) Set$.MODULE$.newBuilder().$plus$eq(gameOverEvent).result();
    }

}