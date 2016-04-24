package net.xylophones.planetoid.web;

import net.xylophones.planetoid.web.msg.ClosedSessionHandler;
import net.xylophones.planetoid.web.msg.IncomingMessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint(value = "/websocket", configurator = SpringConfigurator.class)
public class WebSocketEndPoint {

    @Autowired
    private IncomingMessageRouter incomingMessageRouter;

    @Autowired
    private ClosedSessionHandler closedSessionHandler;

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        incomingMessageRouter.process(message, session);
    }

    @OnClose
    public void onClose(Session session) {
        closedSessionHandler.handleClosedSession(session);
    }
}
