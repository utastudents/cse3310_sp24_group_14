package uta.cse3310;

public class Cell {
    private char letter;
    private boolean isPartOfWord;
    private boolean isSelected;
    private int row; // Row position in the grid
    private int col; // Column position in the grid
    private String color; // For frontend to handle coloring based on player interaction

    // Constructor
    public Cell(char letter, int row, int col) {
        this.letter = letter;
        this.row = row;
        this.col = col;
        this.isPartOfWord = false;
        this.isSelected = false;
        this.color = ""; // Default color
    }

    // Getters and setters
    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public boolean isPartOfWord() {
        return isPartOfWord;
    }

    public void setPartOfWord(boolean partOfWord) {
        this.isPartOfWord = partOfWord;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
