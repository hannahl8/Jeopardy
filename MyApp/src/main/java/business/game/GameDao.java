package business.game;

import java.sql.Connection;
import java.util.List;

public interface GameDao {

    public List<Game> findAll();

    public Game findByGameId(long gameId);

    public Game findByName(String gameName);

    public long create(Connection connection, String name);
}
