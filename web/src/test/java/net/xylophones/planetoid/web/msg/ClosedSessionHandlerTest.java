package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.GameAbortPlayerNotifier;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClosedSessionHandlerTest {

    @InjectMocks
    private ClosedSessionHandler underTest;

    @Mock
    private LiveGameRepository liveGameRepository;

    @Mock
    private GameAbortPlayerNotifier gameAbortPlayerNotifier;

    @Mock
    private GameStartQueue qameStartQueue;

    @Test
    public void checkGameRemovedFromLiveGameListAndOpponentNotified() {
        // given
        LiveGame game = mock(LiveGame.class);
        DownstreamPlayer player1 = getDownstreamPlayerWithSessionIdAndUserId("session id 1", "user 1");
        DownstreamPlayer player2 = getDownstreamPlayerWithSessionIdAndUserId("session id 2", "user 2");
        when(game.getPlayer1()).thenReturn(player1);
        when(game.getPlayer2()).thenReturn(player2);
        when(liveGameRepository.findBySessionId("session id 1")).thenReturn(Optional.of(game));

        // when
        underTest.handleClosedSession(player1.getSession());

        // then
        verify(liveGameRepository).remove(game);
        verify(gameAbortPlayerNotifier).notifyPlayerWithOpenSessionOfGameAbort(game);
        verify(qameStartQueue).removeDownstreamPlayerWithSessionId("session id 1");
    }

    @Test
    public void checkPlayerRemovedFromQueue() {
        // given
        Session session = mock(Session.class);
        when(session.getId()).thenReturn("session id 1");
        when(liveGameRepository.findBySessionId("session id 1")).thenReturn(Optional.empty());

        // when
        underTest.handleClosedSession(session);

        // then

        verify(qameStartQueue).removeDownstreamPlayerWithSessionId("session id 1");
    }

    private DownstreamPlayer getDownstreamPlayerWithSessionIdAndUserId(String sessionId, String userId) {
        DownstreamPlayer player = mock(DownstreamPlayer.class);

        Session session = mock(Session.class);
        when(player.getSession()).thenReturn(session);
        when(session.getId()).thenReturn(sessionId);
        when(player.getSession()).thenReturn(session);
        when(player.getUserId()).thenReturn(userId);

        return player;
    }
}