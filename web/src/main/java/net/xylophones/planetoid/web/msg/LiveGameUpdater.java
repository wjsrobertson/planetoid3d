package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.game.PlanetoidsGameService;
import net.xylophones.planetoid.game.model.GameModelUpdateResult;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Option;

@Component
public class LiveGameUpdater {

    @Autowired
    private PlanetoidsGameService gameService;

    @Autowired
    private LiveGameUpdateNotifier liveGameUpdateNotifier;

    public void updateGameAndNotifyPlayers(LiveGame liveGame) {
        String gameId = liveGame.getGameId();
        Option<GameModelUpdateResult> maybeUpdate = gameService.updateGame(gameId);
        if (maybeUpdate.isDefined()) {
            GameModelUpdateResult updateResult = maybeUpdate.get();

            liveGameUpdateNotifier.notifyPlayersOfGameUpdate(liveGame, updateResult);
        }
    }
}
