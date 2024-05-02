package uta.cse3310;

public class Word {
    private String text;
    private boolean found;

    public Word(String text) {
        this.text = text;
        this.found = false; // Initially not found
    }

    public String getText() {
        return text;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}