package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.dto.GameStartRequestDTO;

import javax.websocket.Session;

public class GameStartRequestMessageHandler extends AbstractIncomingMessageHandler<GameStartRequestDTO>{

    @Override
    protected void process(GameStartRequestDTO payload, Session session) {

    }
}
