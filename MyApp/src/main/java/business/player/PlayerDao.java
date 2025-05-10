package business.player;

import java.sql.Connection;
import java.util.List;

public interface PlayerDao {
    public Player findByPlayerId(long playerId);

    public Player findByName(String name);

    public long create(Connection connection, String name, long gameId);

    public void updateScore(long playerId, int score);

    public void delete(long playerId);

    public List<Player> findAllPlayers(long gameId);
}
