package business.player;

import business.JdbcUtils;

import java.sql.Connection;
import java.util.List;

public class DefaultPlayerService implements PlayerService {

    private PlayerDao playerDao;

    public DefaultPlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public long createPlayer(PlayerRequest playerRequest) {
        try (Connection connection = JdbcUtils.getConnection()) {
            return performCreatePlayer(connection, playerRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error creating player", e);
        }

    }

    @Override
    public Player getPlayer(long playerId) {
        Player player = playerDao.findByPlayerId(playerId);
        if (player == null) {
            throw new RuntimeException("Player not found");
        }
        return player;
    }

    @Override
    public void updatePlayerScore(long playerId, int score) {
        try (Connection connection = JdbcUtils.getConnection()) {
            connection.setAutoCommit(false);
            playerDao.updateScore(playerId, score);
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error updating player score", e);
        }
    }

    @Override
    public void deletePlayer(long playerId) {
        try (Connection connection = JdbcUtils.getConnection()) {
            connection.setAutoCommit(false);
            playerDao.delete(playerId);
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting player", e);
        }
    }

    @Override
    public List<Player> getAllPlayers(long gameId) {
        List<Player> players = playerDao.findAllPlayers(gameId);
        if (players == null || players.isEmpty()) {
            throw new RuntimeException("No players found for game ID: " + gameId);
        }
        return players;
    }

    private long performCreatePlayer(Connection connection, PlayerRequest playerRequest) {
        try {
            connection.setAutoCommit(false);
            long playerId = playerDao.create(connection, playerRequest.getName(), playerRequest.getGameId());
            connection.commit();
            return playerId;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception rollbackException) {
                throw new RuntimeException("Error rolling back transaction", rollbackException);
            }
            throw new RuntimeException("Error creating player", e);
        }
    }

}
