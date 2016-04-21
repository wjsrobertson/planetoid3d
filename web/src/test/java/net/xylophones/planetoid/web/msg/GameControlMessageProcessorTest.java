package net.xylophones.planetoid.web.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.xylophones.planetoid.game.PlanetoidsGameService;
import net.xylophones.planetoid.game.model.PlayerInput;
import net.xylophones.planetoid.web.msg.dto.GameControl;
import net.xylophones.planetoid.web.msg.model.IncomingMessage;
import net.xylophones.planetoid.web.msg.model.IncomingMessageType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameControlMessageProcessorTest {

    @InjectMocks
    private GameControlMessageProcessor underTest;

    @Mock
    private PlanetoidsGameService gameService;

    @Before
    public void setUp() throws Exception {
        // use the real JSON mapper instead of a mock
        ReflectionTestUtils.setField(underTest, "objectMapper", new ObjectMapper());
    }

    @Test
    public void checkProcess() {
        // given
        String payload = "{\"userId\":\"user id\",\"gameId\":\"game id\",\"fireMissile\":true,\"left\":false,\"right\":true,\"thrust\":false}";
        IncomingMessage message = new IncomingMessage(IncomingMessageType.GameControl, payload, null);

        // when
        underTest.handleMessage(message);

        // then
        verify(gameService).applyUserInput("game id", "user id", new PlayerInput(false, true, false,false, true));
    }
}