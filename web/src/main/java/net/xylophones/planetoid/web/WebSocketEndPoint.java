package net.xylophones.planetoid.web;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint(value = "/websocket", configurator = SpringConfigurator.class)
public class WebSocketEndPoint {

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        System.out.println("Message sent by session with ID " + session.getId() + " - " + message);
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
    }

}
