package org.View;

public enum Difficulty {

    EASY(20),
    MEDIUM(40),
    HARD(60);

    private int numbersToDelete;

    Difficulty(int numbersToDelete) {
        this.numbersToDelete = numbersToDelete;
    }

    public int getNumbersToDelete() {
        return numbersToDelete;
    }

    public void setNumbersToDelete(int numbersToDelete) {
        this.numbersToDelete = numbersToDelete;
    }

    public static Difficulty fromLocalizedString(String input) {
        switch (input.toLowerCase()) {
            case "latwy":
            case "easy":
                return EASY;
            case "sredni":
            case "medium":
                return MEDIUM;
            case "trudny":
            case "hard":
                return HARD;
            default:
                throw new IllegalArgumentException("Nieobsługiwany poziom trudności: " + input);
        }
    }

}
