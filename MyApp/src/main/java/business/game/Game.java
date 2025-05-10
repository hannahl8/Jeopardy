package business.game;

import java.util.Date;

public record Game(long gameId, String name, Date dateCreated) {
}
