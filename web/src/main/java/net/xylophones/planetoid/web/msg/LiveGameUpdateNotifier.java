package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.game.model.GameModelUpdateResult;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiveGameUpdateNotifier {

    @Autowired
    private MessageSender messageSender;

    public void notifyPlayersOfGameUpdate(LiveGame liveGame, GameModelUpdateResult updateResult) {
        messageSender.sendAsync(liveGame.getPlayer1().getSession(), updateResult);
        messageSender.sendAsync(liveGame.getPlayer2().getSession(), updateResult);
    }

}
