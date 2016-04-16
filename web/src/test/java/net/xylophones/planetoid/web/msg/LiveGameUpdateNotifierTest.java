package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.game.model.GameModelUpdateResult;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.websocket.Session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LiveGameUpdateNotifierTest {

    @InjectMocks
    private LiveGameUpdateNotifier underTest;

    @Mock
    private MessageSender messageSender;

    @Test
    public void checkPlayersGetNotifiedOfGameUpdate() {
        /*
         when
          */
        GameModelUpdateResult updateResult = mock(GameModelUpdateResult.class);
        Session p1Session = mock(Session.class);
        Session p2Session = mock(Session.class);

        LiveGame liveGame = new LiveGame("gameID",
                new DownstreamPlayer("user id", "name", p1Session),
                new DownstreamPlayer("user id", "name", p2Session));

        // when
        underTest.notifyPlayersOfGameUpdate(liveGame, updateResult);

        // then
        verify(messageSender).sendAsync(p1Session, updateResult);
        verify(messageSender).sendAsync(p2Session, updateResult);

    }
}