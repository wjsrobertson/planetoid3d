package net.xylophones.planetoid.web.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.IncomingMessage;
import net.xylophones.planetoid.web.msg.model.IncomingMessageType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.websocket.Session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameStartRequestMessageProcessorTest {

    @InjectMocks
    private GameStartRequestMessageProcessor underTest;

    @Mock
    private GameStartQueue gameStartQueue;

    @Mock
    private Session session;

    @Captor
    private ArgumentCaptor<DownstreamPlayer> downstreamPlayerCaptor;

    @Before
    public void setUp() throws Exception {
        // use the real JSON mapper instead of a mock
        ReflectionTestUtils.setField(underTest, "objectMapper", new ObjectMapper());
    }

    @Test
    public void checkPayloadGetsPassedToGameStartQueue() throws Exception {
        // given
        String payload = "{\"id\":\"1234\",\"name\":\"Will Robertson\"}";
        IncomingMessage message = new IncomingMessage(IncomingMessageType.GameStartRequest, payload, session);

        // when
        underTest.handleMessage(message);

        // then
        verify(gameStartQueue).add(downstreamPlayerCaptor.capture());
        DownstreamPlayer gameStartSession = downstreamPlayerCaptor.getValue();

        assertThat(gameStartSession.getUserId()).isEqualTo("1234");
        assertThat(gameStartSession.getName()).isEqualTo("Will Robertson");
    }
}