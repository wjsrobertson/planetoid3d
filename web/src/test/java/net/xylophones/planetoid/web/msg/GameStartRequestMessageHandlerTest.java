package net.xylophones.planetoid.web.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.xylophones.planetoid.web.msg.model.GameStartSession;
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
public class GameStartRequestMessageHandlerTest {

    @InjectMocks
    private GameStartRequestMessageHandler underTest;

    @Mock
    private GameStartQueue gameStartQueue;

    @Mock
    private Session session;

    @Captor
    private ArgumentCaptor<GameStartSession> gameStartSessionCaptor;

    @Before
    public void setUp() throws Exception {
        // use the real JSON mapper instead of a mock
        ReflectionTestUtils.setField(underTest, "objectMapper", new ObjectMapper());
    }

    @Test
    public void checkPayloadGetsPassedToGameStartQueue() throws Exception {
        // given
        String payload = "{\"id\":\"1234\",\"name\":\"Will Robertson\"}";
        IncomingMessage message = new IncomingMessage(IncomingMessageType.GameRequest, payload, session);

        // when
        underTest.handleMessage(message);

        // then
        verify(gameStartQueue).add(gameStartSessionCaptor.capture());
        GameStartSession gameStartSession = gameStartSessionCaptor.getValue();

        assertThat(gameStartSession.getGameStartRequest().getId()).isEqualTo("1234");
        assertThat(gameStartSession.getGameStartRequest().getName()).isEqualTo("Will Robertson");
    }
}