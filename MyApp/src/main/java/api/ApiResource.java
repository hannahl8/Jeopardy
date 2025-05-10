package api;

import business.ApplicationContext;
import business.clue.Clue;
import business.clue.ClueDao;
import business.category.Category;
import business.category.CategoryDao;
import business.game.*;
import business.player.Player;
import business.player.PlayerDao;
import business.player.PlayerRequest;
import business.player.PlayerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationPath("/")
@Path("/")
public class ApiResource {

    private final ClueDao clueDao = ApplicationContext.INSTANCE.getClueDao();
    private final CategoryDao categoryDao = ApplicationContext.INSTANCE.getCategoryDao();
    private final GameDao gameDao = ApplicationContext.INSTANCE.getGameDao();
    private final GameService gameService = ApplicationContext.INSTANCE.getGameService();
    private final PlayerDao playerDao = ApplicationContext.INSTANCE.getPlayerDao();
    private final PlayerService playerService = ApplicationContext.INSTANCE.getPlayerService();

    // clues
    @GET
    @Path("clues")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Clue> clues(@Context HttpServletRequest httpRequest) {
        try {
            return clueDao.findAll();
        } catch (Exception e) {
            throw new ApiException("clues lookup failed", e);
        }
    }

    // clues/{clue-id}
    @GET
    @Path("clues/{clue-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Clue clueById(@PathParam("clue-id") long clueId,
                         @Context HttpServletRequest httpRequest) {
        try {
            Clue result = clueDao.findByClueId(clueId);
            if (result == null) {
                throw new ApiException(String.format("No such clue id: %d", clueId));
            }
            return result;
        } catch (Exception e) {
            throw new ApiException(String.format("Clue lookup by clue-id %d failed", clueId), e);
        }
    }

    // categories
    @GET
    @Path("categories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> categories(@Context HttpServletRequest httpRequest) {
        try {
            return categoryDao.findAll();
        } catch (Exception e) {
            throw new ApiException("categories lookup failed", e);
        }
    }

    // categories/{category-id}
    @GET
    @Path("categories/{category-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category categoryById(@PathParam("category-id") long categoryId,
                                 @Context HttpServletRequest httpRequest) {
        try {
            Category result = categoryDao.findByCategoryId(categoryId);
            if (result == null) {
                throw new ApiException(String.format("No such category id: %d", categoryId));
            }
            return result;
        } catch (Exception e) {
            throw new ApiException(String.format("Category lookup by category-id %d failed", categoryId), e);
        }
    }

    // categories/game/{game-id}
    @GET
    @Path("categories/game/{game-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> categoriesByGameId(@PathParam("game-id") long gameId,
                                             @Context HttpServletRequest httpRequest) {
        try {
            return categoryDao.findByGameId(gameId);
        } catch (Exception e) {
            throw new ApiException("categories lookup via gameId failed", e);
        }
    }

    // categories/{category-id}/clues
    @GET
    @Path("categories/{category-id}/clues")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Clue> cluesByCategoryId(@PathParam("category-id") long categoryId,
                                        @Context HttpServletRequest httpRequest) {
        try {
            Category category = categoryDao.findByCategoryId(categoryId);
            if (category == null) {
                throw new ApiException(String.format("No such category id: %d", categoryId));
            }
            return clueDao.findByCategoryId(category.categoryId());
        } catch (Exception e) {
            throw new ApiException(String.format("Clues lookup by category-id %d failed", categoryId), e);
        }
    }

    // categories/{category-id}/suggested-clues
    // categories/{category-id}/suggested-clues?limit=#
    @GET
    @Path("categories/{category-id}/suggested-clues")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Clue> suggestedClues(@PathParam("category-id") long categoryId,
                                     @QueryParam("limit") @DefaultValue("3") int limit,
                                     @Context HttpServletRequest request) {
        try {
            return clueDao.findRandomByCategoryId(categoryId, limit);
        } catch (Exception e) {
            throw new ApiException("products lookup via categoryName failed", e);
        }
    }

    // categories/name/{category-name}
    @GET
    @Path("categories/name/{category-name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category categoryByName(@PathParam("category-name") String name,
                                   @Context HttpServletRequest httpRequest) {
        try {
            Category result = categoryDao.findByName(name);
            if (result == null) {
                throw new ApiException(String.format("No such category name: %s", name));
            }
            return result;
        } catch (Exception e) {
            throw new ApiException(String.format("Category lookup by category-name %s failed", name), e);
        }
    }

    // categories/name/{category-name}/clues
    @GET
    @Path("categories/name/{category-name}/clues")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Clue> cluesByCategoryName(@PathParam("category-name") String name,
                                          @Context HttpServletRequest httpRequest) {
        try {
            Category category = categoryDao.findByName(name);
            if (category == null) {
                throw new ApiException(String.format("No such category name: %s", name));
            }
            return clueDao.findByCategoryId(category.categoryId());
        } catch (Exception e) {
            throw new ApiException(String.format("Clues lookup by category-name %s failed", name), e);
        }
    }

    // categories/name/{category-name}/suggested-clues
    // categories/name/{category-name}/suggested-clues?limit=#
    @GET
    @Path("categories/name/{category-name}/suggested-clues")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Clue> suggestedCluesByCategoryName(@PathParam("category-name") String name,
                                                   @QueryParam("limit") @DefaultValue("3") int limit,
                                                   @Context HttpServletRequest request) {
        try {
            Category category = categoryDao.findByName(name);
            if (category == null) {
                throw new ApiException(String.format("No such category name: %s", name));
            }
            return clueDao.findRandomByCategoryId(category.categoryId(), limit);
        } catch (Exception e) {
            throw new ApiException(String.format("Suggested clues lookup by category-name %s failed", name), e);
        }
    }

    // games
    @GET
    @Path("games")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Game> games(@Context HttpServletRequest httpRequest) {
        try {
            return gameDao.findAll();
        } catch (Exception e) {
            throw new ApiException("games lookup failed", e);
        }
    }

    // games/{game-id}
    @GET
    @Path("games/{game-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game gameById(@PathParam("game-id") long gameId,
                         @Context HttpServletRequest httpRequest) {
        try {
            Game result = gameDao.findByGameId(gameId);
            if (result == null) {
                throw new ApiException(String.format("No such game id: %d", gameId));
            }
            return result;
        } catch (Exception e) {
            throw new ApiException(String.format("Game lookup by game-id %d failed", gameId), e);
        }
    }

    // games/name/{game-name}
    @GET
    @Path("games/name/{game-name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Game gameByName(@PathParam("game-name") String name,
                           @Context HttpServletRequest httpRequest) {
        try {
            Game result = gameDao.findByName(name);
            if (result == null) {
                throw new ApiException(String.format("No such game name: %s", name));
            }
            return result;
        } catch (Exception e) {
            throw new ApiException(String.format("Game lookup by game-name %s failed", name), e);
        }
    }

    // games/{game-id}/details
    @GET
    @Path("games/{game-id}/details")
    @Produces(MediaType.APPLICATION_JSON)
    public GameDetails gameByIdDetails(@PathParam("game-id") long gameId,
                                       @Context HttpServletRequest httpRequest) {
        try {
            GameDetails gameDetails = gameService.getGameDetails(gameId);
            if (gameDetails == null) {
                throw new ApiException(String.format("No such game id: %d", gameId));
            }
            return gameDetails;
        } catch (Exception e) {
            throw new ApiException(String.format("Game details lookup by game-id %d failed", gameId), e);
        }
    }

    // games/create
    @POST
    @Path("games/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GameDetails createGame(GameRequest gameRequest) {
        try {
            long gameId = gameService.createGame(gameRequest);
            if (gameId > 0) {
                return gameService.getGameDetails(gameId);
            } else {
                throw new ApiException.ValidationFailure("Unknown error creating game");
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("Game creation failed", e);
        }
    }


    // create player
    @POST
    @Path("games/{game-id}/players")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Player createPlayer(@PathParam("game-id") long gameId,
                               PlayerRequest playerRequest,
                               @Context HttpServletRequest httpRequest) {
        try {
            long playerId = playerService.createPlayer(playerRequest);
            if (playerId > 0) {
                return playerService.getPlayer(playerId);
            } else {
                throw new ApiException.ValidationFailure("Unknown error creating player");
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("Player creation failed", e);
        }
    }

    // players
    @GET
    @Path("games/{game-id}/players")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Player> players(@PathParam("game-id") long gameId,
                                @Context HttpServletRequest httpRequest) {
        try {
            return playerDao.findAllPlayers(gameId);
        } catch (Exception e) {
            throw new ApiException("players lookup failed", e);
        }
    }

    // delete player
    @DELETE
    @Path("games/{game-id}/players/{player-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deletePlayer(@PathParam("game-id") long gameId,
                             @PathParam("player-id") long playerId,
                             @Context HttpServletRequest httpRequest) {
        try {
            playerService.deletePlayer(playerId);
        } catch (Exception e) {
            throw new ApiException("Player deletion failed", e);
        }
    }



}
