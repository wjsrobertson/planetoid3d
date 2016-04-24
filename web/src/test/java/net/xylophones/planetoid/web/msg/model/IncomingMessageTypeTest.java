package net.xylophones.planetoid.web.msg.model;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class IncomingMessageTypeTest {

    @Test
    public void checkGetMessageTypeForGameRequest() throws Exception {
        // given
        String message = "GameStartRequest:blahblah";

        // when
        Optional<IncomingMessageType> messageType = IncomingMessageType.getMessageType(message);

        // then
        assertThat(messageType.isPresent()).isTrue();
        assertThat(messageType.get()).isEqualTo(IncomingMessageType.GameStartRequest);
    }

    @Test
    public void checkGetMessageTypeIsAbsentForInvalidMessage() {
        // given
        String message = "fakemsg:blahblah";

        // when
        Optional<IncomingMessageType> messageType = IncomingMessageType.getMessageType(message);

        // then
        assertThat(messageType.isPresent()).isFalse();
    }
}