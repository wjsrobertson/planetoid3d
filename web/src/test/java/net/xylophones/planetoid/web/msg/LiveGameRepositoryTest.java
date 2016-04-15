package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.websocket.Session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LiveGameRepositoryTest {

    @InjectMocks
    private LiveGameRepository underTest;

    @Mock
    private Session p1Session;

    @Mock
    private Session p2Session;

    @Test
    public void checkAddAndRemove() {
        // given
        LiveGame game = new LiveGame(null, null, null);

        // when
        underTest.add(game);

        // then
        assertThat(underTest.getAll()).contains(game);

        // and when
        underTest.remove(game);

        // then
        assertThat(underTest.getAll()).isEmpty();
    }

    @Test
    public void checkFindBySessionIdFindsForPlayerOneAndPlayer2() {
        // given
        DownstreamPlayer p1 = new DownstreamPlayer(null, null, p1Session);
        DownstreamPlayer p2 = new DownstreamPlayer(null, null, p2Session);
        when(p1Session.getId()).thenReturn("id1");
        when(p2Session.getId()).thenReturn("id2");
        LiveGame game = new LiveGame(null, p1, p2);

        // when
        underTest.add(game);

        // then
        assertThat(underTest.findBySessionId("id1").isPresent()).isTrue();
        assertThat(underTest.findBySessionId("id2").isPresent()).isTrue();
        assertThat(underTest.findBySessionId("id3").isPresent()).isFalse();
    }
}