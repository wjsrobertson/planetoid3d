package net.xylophones.planetoid.web.msg;


import net.xylophones.planetoid.web.GameAbortPlayerNotifier;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.Optional;

@Component
public class ClosedSessionHandler {

    @Autowired
    private LiveGameRepository liveGameRepository;

    @Autowired
    private GameAbortPlayerNotifier gameAbortPlayerNotifier;

    @Autowired
    private GameStartQueue qameStartQueue;

    public void handleClosedSession(Session session) {
        removePlayerFromGameQueueIfPresent(session);

        endLiveGameIfInProgress(session);
    }

    private void removePlayerFromGameQueueIfPresent(Session session) {
        qameStartQueue.removeDownstreamPlayerWithSessionId(session.getId());
    }

    private void endLiveGameIfInProgress(Session session) {
        Optional<LiveGame> maybeLiveGame = liveGameRepository.findBySessionId(session.getId());
        if (maybeLiveGame.isPresent()) {
            LiveGame game = maybeLiveGame.get();

            liveGameRepository.remove(game);
            gameAbortPlayerNotifier.notifyPlayerWithOpenSessionOfGameAbort(game);
        }
    }
}
