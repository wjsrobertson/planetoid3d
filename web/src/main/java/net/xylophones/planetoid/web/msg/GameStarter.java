package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.game.PlanetoidsGameService;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayer;
import net.xylophones.planetoid.web.msg.model.DownstreamPlayerPair;
import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameStarter {

    @Autowired
    private PlanetoidsGameService planetoidsGameService;

    @Autowired
    private GameStartPlayerNotifier gameStartPlayerNotifier;

    @Autowired
    private LiveGameRepository liveGameRepository;

    public void startGame(DownstreamPlayerPair playerPair) {
        DownstreamPlayer player1 = playerPair.getPlayer1();
        DownstreamPlayer player2 = playerPair.getPlayer2();

        String gameId = planetoidsGameService.createGame(player1.getUserId(), player2.getUserId());
        LiveGame liveGame = new LiveGame(gameId, player1, player2);

        gameStartPlayerNotifier.notifyPlayersOfGameStart(liveGame);
        liveGameRepository.add(liveGame);
    }
}
