package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.IncomingMessage;
import net.xylophones.planetoid.web.msg.model.IncomingMessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.websocket.Session;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IncomingMessageCreatorTest {

    @InjectMocks
    private IncomingMessageCreator underTest;

    @Mock
    private Session session;

    @Test
    public void checkGameRequestMessageGetsParsed() throws Exception {
        // given
        String message = "greq:blah blah";

        // when
        Optional<IncomingMessage> maybeMessage = underTest.parse(message, session);

        // then
        assertThat(maybeMessage.isPresent()).isTrue();

        IncomingMessage incomingMessage = maybeMessage.get();
        assertThat(incomingMessage.getPayload()).isEqualTo("blah blah");
        assertThat(incomingMessage.getSession()).isEqualTo(session);
        assertThat(incomingMessage.getType()).isEqualTo(IncomingMessageType.GameStartRequest);
    }
}