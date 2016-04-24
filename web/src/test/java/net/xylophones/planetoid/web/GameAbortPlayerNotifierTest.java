package net.xylophones.planetoid.web;

import net.xylophones.planetoid.web.msg.MessageSender;
import net.xylophones.planetoid.web.msg.dto.GameAbortNotification;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.websocket.Session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameAbortPlayerNotifierTest {

    @InjectMocks
    private GameAbortPlayerNotifier underTest;

    @Mock
    private MessageSender messageSender;

    @Test
    public void checkPlayerWithOpenSessionIsNotifiedOfAbort() {
        // given
        Session session1 = mock(Session.class);
        Session session2 = mock(Session.class);
        ArgumentCaptor<GameAbortNotification> messageCaptor = ArgumentCaptor.forClass(GameAbortNotification.class);
        LiveGame liveGame = createLiveGame(session1, session2);
        when(session1.isOpen()).thenReturn(true);
        when(session2.isOpen()).thenReturn(false);

        // when
        underTest.notifyPlayerWithOpenSessionOfGameAbort(liveGame);

        // then
        verify(messageSender).sendAsync(eq(session1), messageCaptor.capture());
        GameAbortNotification message = messageCaptor.getValue();
        assertThat(message.getGameId()).isEqualTo("gameId");

        verify(messageSender, times(0)).sendAsync(eq(session2), any());
    }

    private LiveGame createLiveGame(Session p1Session, Session p2Session) {
        DownstreamPlayer p1 = new DownstreamPlayer("p1UserId", "p1 name", p1Session);
        DownstreamPlayer p2 = new DownstreamPlayer("p2UserId", "p2 name", p2Session);

        return new LiveGame("gameId", p1, p2);
    }
}