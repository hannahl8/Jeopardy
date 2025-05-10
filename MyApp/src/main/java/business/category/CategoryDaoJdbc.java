package business.category;

import business.AppDbException.AppUpdateDbException;
import business.AppDbException.AppQueryDbException;
import business.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoJdbc implements CategoryDao {

    private static final String FIND_ALL_SQL = """
            SELECT category_id, name, game_id
            FROM category
            """;

    private static final String FIND_BY_CATEGORY_ID_SQL = """
            SELECT category_id, name, game_id
            FROM category
            WHERE category_id = ?
            """;

    private static final String FIND_BY_NAME_SQL = """
            SELECT category_id, name, game_id
            FROM category
            WHERE name = ?
            """;

    private static final String CREATE_CATEGORY_SQL = """
            INSERT INTO category (name, game_id)
            VALUES (?, ?)
            """;

    private static final String FIND_BY_GAME_ID_SQL = """
            SELECT category_id, name, game_id
            FROM category
            WHERE game_id = ?
            """;

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Category category = readCategory(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new AppQueryDbException("Encountered a problem finding all categories", e);
        }
        return categories;
    }

    @Override
    public Category findByCategoryId(long categoryId) {
        Category category = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category = readCategory(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new AppQueryDbException("Encountered a problem finding category " + categoryId, e);
        }
        return category;
    }

    @Override
    public Category findByName(String name) {
        Category category = null;

        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category = readCategory(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new AppQueryDbException("Encountered a problem finding category " + name, e);
        }

        return category;
    }

    @Override
    public long create(Connection connection,
                       String name,
                       long gameId) {
        try (PreparedStatement statement =
                     connection.prepareStatement(CREATE_CATEGORY_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setLong(2, gameId);
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new AppUpdateDbException("Failed to insert a category, affected row count = " + affected);
            }
            long categoryId;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                categoryId = rs.getLong(1);
            } else {
                throw new AppUpdateDbException("Failed to retrieve categoryId auto-generated key");
            }
            return categoryId;
        } catch (SQLException e) {
            throw new AppUpdateDbException("Encountered problem creating a new category ", e);
        }
    }

    @Override
    public List<Category> findByGameId(long gameId) {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_GAME_ID_SQL)) {
            statement.setLong(1, gameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Category category = readCategory(resultSet);
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            throw new AppQueryDbException("Encountered a problem finding categories with game id " + gameId, e);
        }
        return categories;
    }

    private Category readCategory(ResultSet resultSet) throws SQLException {
        long categoryId = resultSet.getLong("category_id");
        String name = resultSet.getString("name");
        long gameId = resultSet.getLong("game_id");
        return new Category(categoryId, name, gameId);
    }
}
