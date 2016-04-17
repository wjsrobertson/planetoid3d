package net.xylophones.planetoid.web.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.any;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageSenderTest {

    @InjectMocks
    private MessageSender underTest;

    @Captor
    private ArgumentCaptor<String> textMessageCaptor;

    @Before
    public void setUp() throws Exception {
        // use the real JSON mapper instead of a mock
        ReflectionTestUtils.setField(underTest, "objectMapper", new ObjectMapper());
    }

    @Test
    public void checkSentMessagePayloadHasClassPrefixAndContents() throws IOException {
        // given
        Session session = mock(Session.class);
        RemoteEndpoint.Basic basic = mock(RemoteEndpoint.Basic.class);
        when(session.getBasicRemote()).thenReturn(basic);
        Banana message = new Banana();

        // when
        underTest.send(session, message);

        // then
        verify(basic).sendText(textMessageCaptor.capture());
        String actualMessage = textMessageCaptor.getValue();
        assertThat(actualMessage).contains("\"messageType\":\"Banana\"").contains("yellow");
    }

    @Test
    public void checkAsyncSentMessagePayloadHasClassPrefixAndContents() throws IOException {
        // given
        Session session = mock(Session.class);
        RemoteEndpoint.Async async = mock(RemoteEndpoint.Async.class);
        when(session.getAsyncRemote()).thenReturn(async);
        Banana message = new Banana();

        // when
        underTest.sendAsync(session, message);

        // then
        verify(async).sendText(textMessageCaptor.capture());
        String actualMessage = textMessageCaptor.getValue();
        assertThat(actualMessage).contains("\"messageType\":\"Banana\"").contains("yellow");
    }

    @Test
    public void checkIOExceptionIsWrappedAndExcaptionContainsSession() throws IOException {
        // given
        Session session = mock(Session.class);
        RemoteEndpoint.Basic basic = mock(RemoteEndpoint.Basic.class);
        when(session.getBasicRemote()).thenReturn(basic);
        doThrow(new IOException("forced exception")).when(basic).sendText(isA(String.class));

        // when
        try {
            underTest.send(session, new Banana());
            fail("MessageSendException should have been thrown");
        } catch (MessageSendException e) {
            assertThat(e.getSession()).isEqualTo(session);
        }
    }

    private static class Banana {
        String type = "yellow";

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}