package business.clue;

import java.sql.Connection;
import java.util.List;

public interface ClueDao {

    public List<Clue> findAll();

    public List<Clue> findByCategoryId(long categoryId);

    public List<Clue> findRandomByCategoryId(long categoryId, int limit);

    public Clue findByClueId(long clueId);

    public void create(Connection connection, String question, String answer, int value, long categoryId);
}
