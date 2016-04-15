package net.xylophones.planetoid.web;

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

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        System.out.println("Message sent by session with ID " + session.getId() + " - " + message);
        incomingMessageRouter.process(message, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("Session with ID " + session.getId() + " had error " + error.getMessage());
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Session opened with ID " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Session closed with ID " + session.getId());

        // if in queue remove

        // if in game then cancel
    }

}
