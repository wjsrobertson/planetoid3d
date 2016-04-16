package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.dto.GameStartNotification;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameStartPlayerNotifier {

    @Autowired
    private MessageSender messageSender;

    public void notifyPlayersOfGameStart(LiveGame liveGame) {
        String gameId = liveGame.getGameId();
        DownstreamPlayer player2 = liveGame.getPlayer2();
        DownstreamPlayer player1 = liveGame.getPlayer1();

        GameStartNotification p1Notification = new GameStartNotification(true, gameId, player2.getName());
        GameStartNotification p2Notification = new GameStartNotification(false, gameId, player1.getName());

        messageSender.send(player1.getSession(), p1Notification);
        messageSender.send(player2.getSession(), p2Notification);
    }

}
