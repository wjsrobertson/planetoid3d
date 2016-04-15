package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.dto.GameStartRequest;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.IncomingMessageType;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

@Component
public class GameStartRequestMessageProcessor extends AbstractIncomingMessageProcessor<GameStartRequest> {

    private GameStartQueue gameStartQueue;

    public GameStartRequestMessageProcessor() {
        super(IncomingMessageType.GameStartRequest);
    }

    @Override
    protected void process(GameStartRequest payload, Session session) {
        DownstreamPlayer downstreamPlayer = new DownstreamPlayer(payload.getId(), payload.getName(), session);

        gameStartQueue.add(downstreamPlayer);
    }

}
