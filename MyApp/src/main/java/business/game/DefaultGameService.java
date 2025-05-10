package business.game;

import business.AppDbException;
import business.JdbcUtils;
import business.category.Category;
import business.category.CategoryDao;
import business.category.CategoryRequest;
import business.clue.Clue;
import business.clue.ClueDao;
import business.clue.ClueRequest;

import java.sql.Connection;
import java.util.List;

public class DefaultGameService implements GameService {

    private GameDao gameDao;
    private CategoryDao categoryDao;
    private ClueDao clueDao;

    public DefaultGameService(GameDao gameDao, CategoryDao categoryDao, ClueDao clueDao) {
        this.gameDao = gameDao;
        this.categoryDao = categoryDao;
        this.clueDao = clueDao;
    }

    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void setClueDao(ClueDao clueDao) {
        this.clueDao = clueDao;
    }

    @Override
    public long createGame(GameRequest gameRequest) {
        try (Connection connection = JdbcUtils.getConnection()) {
            return performCreateGame(connection, gameRequest);
        } catch (Exception e) {
            throw new AppDbException("Failed to create game", e);
        }
    }

    @Override
    public GameDetails getGameDetails(long gameId) {
        Game game = gameDao.findByGameId(gameId);
        List<Category> categories = categoryDao.findByGameId(gameId);
        List<Clue> clues = categories.stream()
                .map(category -> clueDao.findByCategoryId(category.categoryId()))
                .flatMap(List::stream)
                .toList();
        return new GameDetails(game, categories, clues);

    }

    private long performCreateGame(Connection connection, GameRequest gameRequest) {
        try {
            connection.setAutoCommit(false);
            long gameId = gameDao.create(connection, gameRequest.getName());
            for (CategoryRequest categoryRequest : gameRequest.getCategoryRequests()) {
                long categoryId = categoryDao.create(connection, categoryRequest.getName(), gameId);
                for (ClueRequest clueRequest : categoryRequest.getClueRequests()) {
                    clueDao.create(connection, clueRequest.getQuestion(), clueRequest.getAnswer(), clueRequest.getValue(), categoryId);
                }
            }
            connection.commit();
            return gameId;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e2) {
                throw new AppDbException("Failed to rollback game creation", e2);
            }
            return 0;
        }
    }
}