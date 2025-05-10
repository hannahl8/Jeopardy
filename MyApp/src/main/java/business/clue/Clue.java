package business.clue;

public record Clue(long clueId, String question, String answer, int value, boolean wasPicked, long categoryId) {
}
