package business;

import business.category.CategoryDao;
import business.category.CategoryDaoJdbc;
import business.clue.ClueDao;
import business.clue.ClueDaoJdbc;
import business.game.DefaultGameService;
import business.game.GameDao;
import business.game.GameDaoJdbc;
import business.game.GameService;
import business.player.DefaultPlayerService;
import business.player.PlayerDao;
import business.player.PlayerDaoJdbc;
import business.player.PlayerService;

public class ApplicationContext {

    private ClueDao clueDao;
    private CategoryDao categoryDao;
    private GameDao gameDao;
    private GameService gameService;
    private PlayerDao playerDao;
    private PlayerService playerService;

    public static ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {
        clueDao = new ClueDaoJdbc();
        categoryDao = new CategoryDaoJdbc();
        gameDao = new GameDaoJdbc();
        gameService = new DefaultGameService(gameDao, categoryDao, clueDao);
        playerDao = new PlayerDaoJdbc();
        playerService = new DefaultPlayerService(playerDao);
        ((DefaultGameService) gameService).setClueDao(clueDao);
        ((DefaultGameService) gameService).setCategoryDao(categoryDao);
        ((DefaultGameService) gameService).setGameDao(gameDao);
        ((DefaultPlayerService) playerService).setPlayerDao(playerDao);
    }

    public ClueDao getClueDao() {
        return clueDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public GameDao getGameDao() {
        return gameDao;
    }

    public GameService getGameService() {
        return gameService;
    }

    public PlayerDao getPlayerDao() {
        return playerDao;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

}
