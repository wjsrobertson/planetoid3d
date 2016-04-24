package net.xylophones.planetoid.web.msg;

import net.xylophones.planetoid.web.msg.model.LiveGame;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class LiveGameRepository {

    private final CopyOnWriteArraySet<LiveGame> games = new CopyOnWriteArraySet<>();

    private final Collection<LiveGame> unmodifiableGames = Collections.unmodifiableCollection(games);

    public void add(LiveGame game) {
        games.add(game);
    }

    public boolean remove(LiveGame game) {
        return games.remove(game);
    }

    public Collection<LiveGame> getAll() {
        return unmodifiableGames;
    }

    public Optional<LiveGame> findBySessionId(String sessionId) {
        return games.stream().filter(game -> game.getPlayer1().getSession().getId().equals(sessionId)
                || game.getPlayer2().getSession().getId().equals(sessionId))
                .findFirst();
    }
}
