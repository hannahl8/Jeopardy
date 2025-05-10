package business.clue;

import business.AppDbException;
import business.AppDbException.AppQueryDbException;
import business.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClueDaoJdbc implements ClueDao {

    private static final String FIND_ALL_SQL = """
                    SELECT clue_id, question, answer, value, was_picked, category_id
                    FROM clue
            """;

    private static final String FIND_BY_CLUE_ID_SQL = """
                    SELECT clue_id, question, answer, value, was_picked, category_id
                    FROM clue
                    WHERE clue_id = ?
            """;

    private static final String FIND_BY_CATEGORY_ID_SQL = """
                    SELECT clue_id, question, answer, value, was_picked, category_id
                    FROM clue
                    WHERE category_id = ?
            """;

    private static final String FIND_RANDOM_BY_CATEGORY_ID_SQL = """
                    SELECT clue_id, question, answer, value, was_picked, category_id
                    FROM clue
                    WHERE category_id = ?
                    ORDER BY RAND()
                    LIMIT ?
            """;

    private static final String CREATE_CLUE_SQL = """
                    INSERT INTO clue (question, answer, value, was_picked, category_id)
                    VALUES (?, ?, ?, ?, ?)
            """;

    @Override
    public List<Clue> findAll() {
        List<Clue> clues = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Clue clue = readClue(resultSet);
                clues.add(clue);
            }
        } catch (SQLException e) {
            throw new AppQueryDbException("Encountered a problem finding all clues", e);
        }
        return clues;
    }

    @Override
    public Clue findByClueId(long clueId) {
        Clue clue = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CLUE_ID_SQL)) {
            statement.setLong(1, clueId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    clue = readClue(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new AppQueryDbException("Encountered a problem finding clue " + clueId, e);
        }
        return clue;
    }

    @Override
    public List<Clue> findByCategoryId(long categoryId) {
        List<Clue> clues = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    clues.add(readClue(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new AppQueryDbException("Encountered a problem finding clues for category " + categoryId, e);
        }
        return clues;
    }

    @Override
    public List<Clue> findRandomByCategoryId(long categoryId, int limit) {
        List<Clue> clues = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_RANDOM_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            statement.setInt(2, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    clues.add(readClue(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new AppQueryDbException("Encountered a problem finding random clues for category " + categoryId, e);
        }
        return clues;
    }

    @Override
    public void create(Connection connection, String question, String answer, int value, long categoryId) {
        try (PreparedStatement statement =
                     connection.prepareStatement(CREATE_CLUE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, question);
            statement.setString(2, answer);
            statement.setInt(3, value);
            statement.setBoolean(4, false);
            statement.setLong(5, categoryId);
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new AppDbException.AppUpdateDbException("Failed to insert a clue, affected row count = " + affected);
            }
        } catch (SQLException e) {
            throw new AppDbException.AppUpdateDbException("Encountered problem creating a new clue ", e);
        }

    }

    private Clue readClue(ResultSet resultSet) throws SQLException {
        long clueId = resultSet.getLong("clue_id");
        String question = resultSet.getString("question");
        String answer = resultSet.getString("answer");
        int value = resultSet.getInt("value");
        boolean wasPicked = resultSet.getBoolean("was_picked");
        long categoryId = resultSet.getLong("category_id");
        return new Clue(clueId, answer, question, value, wasPicked, categoryId);
    }

}
