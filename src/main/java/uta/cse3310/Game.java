package uta.cse3310;

import java.io.IOException;
import java.net.http.WebSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.concurrent.*;

public class Game {
    private String id;
    private String name;
    private List<Player> players;
    private int maxPlayers;
    private boolean gameActive;
    private WordGrid wordGrid;
    private ScheduledExecutorService timer;
    private int gameDuration = 120;
    private GameEventListener listener;

    public Game(String name, int maxPlayers, GameEventListener listener) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
        this.gameActive = false;
        this.wordGrid = new WordGrid(getWords());
        this.listener = listener;
    }

    private List<Word> getWords() {
        List<Word> words = new ArrayList<>();
        try {
            // Adjust the path according to the actual location and your working directory
            Path path = Paths.get("resources/words.txt");
            List<String> allWords = Files.readAllLines(path);
            Collections.shuffle(allWords); // Shuffle to get random words

            allWords.stream()
                    .limit(15) // Limit to 15 words
                    .map(String::toUpperCase) // Convert each string to uppercase
                    .map(Word::new) // Convert each uppercase string to a Word object
                    .forEach(words::add);

        } catch (IOException e) {
            System.err.println("Failed to read words from the file: " + e.getMessage());
            e.printStackTrace();
        }

        return words;
    }

    public void addPlayer(Player player) {
        if (!isFull()) {
            players.add(player);
        }
    }

    public int getNumPlayers() {
        return players.size();
    }

    public boolean isFull() {
        return players.size() >= maxPlayers;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Word> getWordBank() {
        return this.wordGrid.getWords();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public WordGrid getWordGrid() {
        return wordGrid;
    }

    public boolean isGameActive() {
        return this.gameActive;
    }

    private void endGame() {
        this.gameActive = false;
        this.listener.onGameEnded(this);
        timer.shutdown();
    }

    public void startGame() {
        this.gameActive = true;
        timer = Executors.newSingleThreadScheduledExecutor();
        timer.schedule(this::endGame, gameDuration, TimeUnit.SECONDS);
    }
}
