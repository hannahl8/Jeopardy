package business.category;

import java.sql.Connection;
import java.util.List;

public interface CategoryDao {

    public List<Category> findAll();

    public List<Category> findByGameId(long gameId);

    public Category findByCategoryId(long categoryId);

    public Category findByName(String categoryName);

    public long create(Connection connection, String name, long gameId);

}
