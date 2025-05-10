package business.game;

import business.AppDbException;
import business.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameDaoJdbc implements GameDao {

    private static final String FIND_ALL_SQL = """
            SELECT game_id, name, date_created
            FROM game
            """;

    private static final String FIND_BY_GAME_ID_SQL = """
            SELECT game_id, name, date_created
            FROM game
            WHERE game_id = ?
            """;

    private static final String FIND_BY_NAME_SQL = """
            SELECT game_id, name, date_created
            FROM game
            WHERE name = ?
            """;

    private static final String CREATE_GAME_SQL = """
            INSERT INTO game (name, date_created)
            VALUES (?, ?)
            """;

    @Override
    public List<Game> findAll() {
        List<Game> games = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Game game = readGame(resultSet);
                games.add(game);
            }
        } catch (SQLException e) {
            throw new AppDbException.AppQueryDbException("Encountered a problem finding all games", e);
        }
        return games;
    }

    @Override
    public Game findByGameId(long gameId) {
        Game game = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GAME_ID_SQL)) {
            statement.setLong(1, gameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    game = readGame(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new AppDbException.AppQueryDbException("Encountered a problem finding game " + gameId, e);
        }
        return game;
    }

    @Override
    public Game findByName(String gameName) {
        Game game = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, gameName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    game = readGame(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new AppDbException.AppQueryDbException("Encountered a problem finding game " + gameName, e);
        }
        return game;
    }

    @Override
    public long create(Connection connection, String name) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_GAME_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setDate(2, new java.sql.Date(new Date().getTime()));
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new AppDbException.AppUpdateDbException("Failed to insert a game, affected row count = " + affected);
            }
            long gameId;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                gameId = rs.getLong(1);
            } else {
                throw new AppDbException.AppUpdateDbException("Failed to retrieve gameId auto-generated key");
            }
            return gameId;
        } catch (SQLException e) {
            throw new AppDbException.AppUpdateDbException("Encountered problem creating a new game ", e);
        }
    }

    private Game readGame(ResultSet resultSet) throws SQLException {
        long gameId = resultSet.getLong("game_id");
        String name = resultSet.getString("name");
        Date dateCreated = resultSet.getTimestamp("date_created");
        return new Game(gameId, name, dateCreated);
    }
}
