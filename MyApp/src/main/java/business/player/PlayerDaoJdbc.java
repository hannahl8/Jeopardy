package business.player;

import business.AppDbException;
import business.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {

    private static final String FIND_BY_PLAYER_ID_SQL = """
            SELECT player_id, name, score, game_id
            FROM player
            WHERE player_id = ?
            """;

    private static final String FIND_BY_PLAYER_NAME_SQL = """
            SELECT player_id, name, score, game_id
            FROM player
            WHERE name = ?
            """;

    private static final String CREATE_PLAYER_SQL = """
            INSERT INTO player (name, game_id)
            VALUES (?, ?)
            """;

    private static final String UPDATE_PLAYER_SCORE_SQL = """
            UPDATE player
            SET score = ?
            WHERE player_id = ?
            """;

    private static final String DELETE_PLAYER_SQL = """
            DELETE FROM player
            WHERE player_id = ?
            """;

    private static final String FIND_ALL_PLAYERS_SQL = """
            SELECT player_id, name, score, game_id
            FROM player
            WHERE game_id = ?
            """;

    @Override
    public Player findByPlayerId(long playerId) {
        Player player = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_PLAYER_ID_SQL)) {
            statement.setLong(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    player = readPlayer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new AppDbException.AppQueryDbException("Encountered a problem finding player with ID " + playerId, e);
        }
        return player;
    }

    @Override
    public Player findByName(String name) {
        Player player = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_PLAYER_NAME_SQL)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    player = readPlayer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new AppDbException.AppQueryDbException("Encountered a problem finding player with name " + name, e);
        }
        return null;
    }

    @Override
    public long create(Connection connection, String name, long gameId) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_PLAYER_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setLong(2, gameId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating player failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating player failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new AppDbException.AppQueryDbException("Encountered a problem creating player " + name, e);
        }
    }

    @Override
    public void updateScore(long playerId, int score) {
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAYER_SCORE_SQL)) {
            statement.setInt(1, score);
            statement.setLong(2, playerId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating player score failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new AppDbException.AppQueryDbException("Encountered a problem updating score for player " + playerId, e);
        }
    }

    @Override
    public void delete(long playerId) {
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAYER_SQL)) {
            statement.setLong(1, playerId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Deleting player failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new AppDbException.AppQueryDbException("Encountered a problem deleting player " + playerId, e);
        }
    }

    @Override
    public List<Player> findAllPlayers(long gameId) {
        List<Player> players = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLAYERS_SQL)) {
            statement.setLong(1, gameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Player player = readPlayer(resultSet);
                    players.add(player);
                }
            }
        } catch (SQLException e) {
            throw new AppDbException.AppQueryDbException("Encountered a problem finding all players for game " + gameId, e);
        }
        return players;
    }

    private Player readPlayer(ResultSet resultSet) throws SQLException {
        long playerId = resultSet.getLong("player_id");
        String name = resultSet.getString("name");
        int score = resultSet.getInt("score");
        long gameId = resultSet.getLong("game_id");
        return new Player(playerId, name, score, gameId);
    }
}
