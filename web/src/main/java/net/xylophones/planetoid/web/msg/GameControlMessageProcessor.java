package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.game.PlanetoidsGameService;
import net.xylophones.planetoid.game.model.PlayerInput;
import net.xylophones.planetoid.web.msg.dto.GameControl;
import net.xylophones.planetoid.web.msg.model.IncomingMessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

@Component
public class GameControlMessageProcessor extends AbstractIncomingMessageProcessor<GameControl> {

    @Autowired
    private PlanetoidsGameService gameService;

    public GameControlMessageProcessor() {
        super(IncomingMessageType.GameControl);
    }

    @Override
    protected void process(GameControl message, Session session) {
        gameService.applyUserInput(message.getGameId(), message.getUserId(), createPlayerInput(message));
    }

    private PlayerInput createPlayerInput(GameControl payload) {
        return new PlayerInput(payload.isLeft(), payload.isRight(), payload.isThrust(), payload.isReverseThrust(),
                payload.isFireMissile());
    }
}
