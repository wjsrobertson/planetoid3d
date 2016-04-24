package net.xylophones.planetoid.web;

import net.xylophones.planetoid.web.msg.ClosedSessionHandler;
import net.xylophones.planetoid.web.msg.IncomingMessageRouter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.Session;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebSocketEndPointTest {

    @InjectMocks
    private WebSocketEndPoint underTest;

    @Mock
    private IncomingMessageRouter incomingMessageRouter;

    @Mock
    private ClosedSessionHandler closedSessionHandler;

    @Test
    public void checkOnMessageDelegatesToMessageRouter() throws IOException, InterruptedException {
        // given
        Session session = mock(Session.class);
        String message = "blah";

        // when
        underTest.onMessage(message, session);

        // then
        verify(incomingMessageRouter).process(message, session);
    }

    @Test
    public void checkOnCloseDelegatesToClosedSessionHandler() {
        // given
        Session session = mock(Session.class);

        // when
        underTest.onClose(session);

        // then
        verify(closedSessionHandler).handleClosedSession(session);
    }
}