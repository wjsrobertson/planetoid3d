package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.dto.GameStartRequest;
import net.xylophones.planetoid.web.msg.model.GameStartSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

@Component
public class GameStartRequestMessageHandler extends AbstractIncomingMessageHandler<GameStartRequest> {

    @Autowired
    private GameStartQueue gameStartQueue;

    @Override
    protected void process(GameStartRequest payload, Session session) {
        GameStartSession gameStartSession = new GameStartSession(payload, session);

        gameStartQueue.add(gameStartSession);
    }

    @Override
    public IncomingMessageType supportedMessageType() {
        return IncomingMessageType.GameRequest;
    }


}
