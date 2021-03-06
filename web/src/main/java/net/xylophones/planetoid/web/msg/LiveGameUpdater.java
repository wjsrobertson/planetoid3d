package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.game.PlanetoidsGameService;
import net.xylophones.planetoid.game.model.GameEvent;
import net.xylophones.planetoid.game.model.GameModelUpdateResult;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Option;

// TODO - this should really happen in a threadpool
@Component
public class LiveGameUpdater {

    @Autowired
    private PlanetoidsGameService gameService;

    @Autowired
    private LiveGameUpdateNotifier liveGameUpdateNotifier;

    @Autowired
    private LiveGameRepository liveGameRepository;

    public void updateGameAndNotifyPlayers(LiveGame liveGame) {
        String gameId = liveGame.getGameId();
        Option<GameModelUpdateResult> maybeUpdate = gameService.updateGame(gameId);
        if (maybeUpdate.isDefined()) {
            GameModelUpdateResult updateResult = maybeUpdate.get();

            liveGameUpdateNotifier.notifyPlayersOfGameUpdate(liveGame, updateResult);

            if (updateResult.events().contains(GameEvent.GameOver())) {
                liveGameRepository.remove(liveGame);
            }
        }
    }
}
