package net.xylophones.planetoid.web;

import net.xylophones.planetoid.web.msg.MessageSendException;
import net.xylophones.planetoid.web.msg.MessageSender;
import net.xylophones.planetoid.web.msg.dto.GameAbortNotification;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

@Component
public class GameAbortPlayerNotifier {

    @Autowired
    private MessageSender messageSender;

    public void notifyPlayerWithOpenSessionOfGameAbort(LiveGame liveGame) {
        notifyOfGameAbort(liveGame.getPlayer1().getSession(), liveGame.getGameId());
        notifyOfGameAbort(liveGame.getPlayer2().getSession(), liveGame.getGameId());
    }

    private void notifyOfGameAbort(Session session, String gameId) {
        if (session.isOpen()) {
            GameAbortNotification message = new GameAbortNotification(gameId);

            try {
                messageSender.sendAsync(session, message);
            } catch (MessageSendException e) {
                // never mind
            }
        }
    }
}
