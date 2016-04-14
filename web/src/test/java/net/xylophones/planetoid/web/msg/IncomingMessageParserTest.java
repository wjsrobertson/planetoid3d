package net.xylophones.planetoid.web.msg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.websocket.Session;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IncomingMessageParserTest {

    @InjectMocks
    private IncomingMessageParser underTest;

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
        assertThat(incomingMessage.getType()).isEqualTo(IncomingMessageType.GameRequest);
    }
}