package business.clue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClueRequest {
    private long clueId;
    private String question;
    private String answer;
    private int value;

    public ClueRequest() {
    }

    public ClueRequest(long clueId, String question, String answer, int value) {
        this.clueId = clueId;
        this.question = question;
        this.answer = answer;
        this.value = value;
    }

    public long getClueId() {
        return clueId;
    }

    public void setClueId(long clueId) {
        this.clueId = clueId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
