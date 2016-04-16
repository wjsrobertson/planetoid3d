package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.dto.GameStartNotification;
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
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameStartPlayerNotifierTest {

    @InjectMocks
    private GameStartPlayerNotifier underTest;

    @Mock
    private MessageSender messageSender;

    @Mock
    private Session p1Session;

    @Mock
    private Session p2Session;

    @Captor
    private ArgumentCaptor<GameStartNotification> p1NotificationCaptor;

    @Captor
    private ArgumentCaptor<GameStartNotification> p2NotificationCaptor;

    @Test
    public void checkPlayersAreSentCorrectNotificationMessages() {
        // given
        LiveGame liveGame = createLiveGame();

        // when
        underTest.notifyPlayersOfGameStart(liveGame);

        // then
        verify(messageSender).send(eq(p1Session), p1NotificationCaptor.capture());
        verify(messageSender).send(eq(p2Session), p2NotificationCaptor.capture());

        GameStartNotification p1Notification = p1NotificationCaptor.getValue();
        assertThat(p1Notification.getIsPlayerOne()).isTrue();
        assertThat(p1Notification.getGameId()).isEqualTo("gameId");
        assertThat(p1Notification.getOpponentName()).isEqualTo("p2 name");

        GameStartNotification p2Notification = p2NotificationCaptor.getValue();
        assertThat(p2Notification.getIsPlayerOne()).isFalse();
        assertThat(p2Notification.getGameId()).isEqualTo("gameId");
        assertThat(p2Notification.getOpponentName()).isEqualTo("p1 name");
    }

    private LiveGame createLiveGame() {
        DownstreamPlayer p1 = new DownstreamPlayer("p1UserId", "p1 name", p1Session);
        DownstreamPlayer p2 = new DownstreamPlayer("p2UserId", "p2 name", p2Session);

        return new LiveGame("gameId", p1, p2);
    }

}