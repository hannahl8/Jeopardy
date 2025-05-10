package business.game;

public interface GameService {
    long createGame(GameRequest gameRequest);

    GameDetails getGameDetails(long gameId);
}
