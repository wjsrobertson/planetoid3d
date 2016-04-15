package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.game.PlanetoidsGameService;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayerPair;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameStarterTest {

    @InjectMocks
    private GameStarter underTest;

    @Mock
    private PlanetoidsGameService planetoidsGameService;

    @Mock
    private GameStartPlayerNotifier gameStartPlayerNotifier;

    @Mock
    private LiveGameRepository liveGameRepository;

    @Captor
    private ArgumentCaptor<LiveGame> gameCaptor;

    @Test
    public void checkStartGameGetsAddedToRepositoryAndPlayersGetNotified() {
        // given
        DownstreamPlayerPair pair = createPlayerPair();
        when(planetoidsGameService.createGame("p1UserId", "p2UserId")).thenReturn("gameId");

        // when
        underTest.startGame(pair);

        // then
        verify(liveGameRepository).add(gameCaptor.capture());
        verify(gameStartPlayerNotifier).notifyPlayersOfGameStart(isA(LiveGame.class));

        LiveGame liveGame = gameCaptor.getValue();
        assertThat(liveGame.getPlayer1().getUserId()).isEqualTo("p1UserId");
        assertThat(liveGame.getPlayer2().getUserId()).isEqualTo("p2UserId");
        assertThat(liveGame.getGameId()).isEqualTo("gameId");
    }

    private DownstreamPlayerPair createPlayerPair() {
        DownstreamPlayer p1 = new DownstreamPlayer("p1UserId", "p1 name", null);
        DownstreamPlayer p2 = new DownstreamPlayer("p2UserId", "p2 name", null);

        return new DownstreamPlayerPair(p1, p2);
    }
}