package business.game;

import business.category.CategoryRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameRequest {
    private long gameId;
    private String name;
    private List<CategoryRequest> categoryRequests;

    public GameRequest() {
    }

    public GameRequest(long gameId, String name, List<CategoryRequest> categoryRequests) {
        this.gameId = gameId;
        this.name = name;
        this.categoryRequests = categoryRequests;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryRequest> getCategoryRequests() {
        return categoryRequests;
    }

    public void setCategoryRequests(List<CategoryRequest> categoryRequests) {
        this.categoryRequests = categoryRequests;
    }
}
