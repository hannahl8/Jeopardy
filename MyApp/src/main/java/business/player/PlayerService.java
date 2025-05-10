package business.player;

import java.util.List;

public interface PlayerService {
    long createPlayer(PlayerRequest playerRequest);

    Player getPlayer(long playerId);

    void updatePlayerScore(long playerId, int score);

    void deletePlayer(long playerId);

    List<Player> getAllPlayers(long gameId);
}
