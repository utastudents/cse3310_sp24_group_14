package uta.cse3310;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WordGrid {
    private static final int SIZE = 20; // Grid size
    private Cell[][] grid;
    private Random random = new Random();
    private ArrayList<Word> words;

    public WordGrid(List<Word> words) {
        this.grid = new Cell[SIZE][SIZE];
        this.words = new ArrayList<>(words);
        initializeGrid();
        placeWords(words);
        fillEmptySpaces();
    }

    public ArrayList<Word> getWords() {
        return this.words;
    }

    private void initializeGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell(' ', i, j); // Initialize with empty cells
            }
        }
    }

    private void placeWords(List<Word> words) {
        Collections.shuffle(words); // Shuffle words to randomize order of placement
        for (Word word : words) {
            placeWord(word.getText());
        }
    }

    private void placeWord(String word) {
        boolean placed = false;
        while (!placed) {
            int direction = random.nextInt(8);
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);

            placed = canPlaceWord(word, row, col, direction);
            if (placed) {
                applyWord(word, row, col, direction);
            }
        }
    }

    private boolean canPlaceWord(String word, int row, int col, int direction) {
        int dr = 0;
        int dc = 0;
        switch (direction) {
            case 0:
                dr = 0;
                dc = 1;
                break; // Left to Right
            case 1:
                dr = 0;
                dc = -1;
                break; // Right to Left
            case 2:
                dr = 1;
                dc = 0;
                break; // Top to Bottom
            case 3:
                dr = -1;
                dc = 0;
                break; // Bottom to Top
            case 4:
                dr = 1;
                dc = 1;
                break; // Diagonal down right
            case 5:
                dr = -1;
                dc = -1;
                break;// Diagonal up left
            case 6:
                dr = 1;
                dc = -1;
                break; // Diagonal down left
            case 7:
                dr = -1;
                dc = 1;
                break; // Diagonal up right
        }

        int r = row, c = col;
        for (int i = 0; i < word.length(); i++) {
            // Check if the position is out of bounds
            if (r < 0 || r >= SIZE || c < 0 || c >= SIZE) {
                return false;
            }

            // Check if the cell is already occupied with a different letter
            if (grid[r][c].getLetter() != ' ' && grid[r][c].getLetter() != word.charAt(i)) {
                return false;
            }

            r += dr;
            c += dc;
        }

        return true; // The word fits in the specified direction and position
    }

    private void applyWord(String word, int row, int col, int direction) {
        int dr = 0;
        int dc = 0;
        switch (direction) {
            case 0:
                dr = 0;
                dc = 1;
                break;
            case 1:
                dr = 0;
                dc = -1;
                break;
            case 2:
                dr = 1;
                dc = 0;
                break;
            case 3:
                dr = -1;
                dc = 0;
                break;
            case 4:
                dr = 1;
                dc = 1;
                break;
            case 5:
                dr = -1;
                dc = -1;
                break;
            case 6:
                dr = 1;
                dc = -1;
                break;
            case 7:
                dr = -1;
                dc = 1;
                break;
        }

        int r = row, c = col;
        for (int i = 0; i < word.length(); i++) {
            grid[r][c].setLetter(word.charAt(i)); // Set the letter
            grid[r][c].setPartOfWord(true); // Mark this cell as part of a valid word
            r += dr;
            c += dc;
        }
    }

    private void fillEmptySpaces() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].getLetter() == ' ') {
                    grid[i][j].setLetter((char) ('A' + random.nextInt(26))); // Fill with random letters
                }
            }
        }
    }

    public boolean isWordValid(Point start, Point end) {
        if (!isValidPoint(start) || !isValidPoint(end)) {
            return false;
        }

        int dx = Integer.compare(end.x, start.x);
        int dy = Integer.compare(end.y, start.y);

        StringBuilder wordBuilder = new StringBuilder();
        int x = start.x, y = start.y;

        // Build the word from start to end
        while (x != end.x + dx || y != end.y + dy) {
            wordBuilder.append(grid[x][y].getLetter());
            x += dx;
            y += dy;
        }

        String wordToCheck = wordBuilder.toString();

        for (Word word : words) {
            if ((word.getText().equals(wordToCheck)
                    || new StringBuilder(wordToCheck).reverse().toString().equals(word.getText())) && !word.isFound()) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidPoint(Point p) {
        return p.x >= 0 && p.x < SIZE && p.y >= 0 && p.y < SIZE;
    }

    public void markWordFound(Point start, Point end, String playerColor) {
        if (!isWordValid(start, end)) {
            return; // Only proceed if the word is valid
        }

        int dx = Integer.compare(end.x, start.x);
        int dy = Integer.compare(end.y, start.y);

        int x = start.x, y = start.y;

        // Apply player color and mark the word as found in each cell
        while (x != end.x + dx || y != end.y + dy) {
            grid[x][y].setColor(playerColor); // Set the cell color to the player's color
            grid[x][y].setPartOfWord(true); // Mark this cell as part of a valid word
            x += dx;
            y += dy;
        }

        // Find and mark the word as found
        String foundWord = buildWord(start, end);
        for (Word word : words) {
            if (word.getText().equals(foundWord)
                    || new StringBuilder(foundWord).reverse().toString().equals(word.getText())) {
                word.setFound(true); // Mark the word as found
                break;
            }
        }
    }

    private String buildWord(Point start, Point end) {
        int dx = Integer.compare(end.x, start.x);
        int dy = Integer.compare(end.y, start.y);
        StringBuilder wordBuilder = new StringBuilder();
        int x = start.x, y = start.y;

        while (x != end.x + dx || y != end.y + dy) {
            wordBuilder.append(grid[x][y].getLetter());
            x += dx;
            y += dy;
        }
        return wordBuilder.toString();
    }

    public Cell[][] getGrid() {
        return grid;
    }

}
