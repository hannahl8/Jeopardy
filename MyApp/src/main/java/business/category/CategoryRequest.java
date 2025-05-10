package business.category;

import business.clue.ClueRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryRequest {
    private long categoryId;
    private String name;
    private List<ClueRequest> clueRequests;

    public CategoryRequest() {
    }

    public CategoryRequest(long categoryId, String name, List<ClueRequest> clueRequests) {
        this.categoryId = categoryId;
        this.name = name;
        this.clueRequests = clueRequests;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClueRequest> getClueRequests() {
        return clueRequests;
    }

    public void setClueRequests(List<ClueRequest> clueRequests) {
        this.clueRequests = clueRequests;
    }
}
