package business.game;

import business.category.Category;
import business.clue.Clue;

import java.util.List;

public record GameDetails(Game game, List<Category> categories, List<Clue> clues) {
}
