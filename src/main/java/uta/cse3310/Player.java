package uta.cse3310;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Random;

public class Player {
    private String id;
    private String username;
    private String gameId;
    private boolean isReady;
    private int score;
    private int highScore;
    private String color; // player's unique highlighting color; color attribute in another location
    private boolean isInLobby;
    private boolean isInGame;

    public Player(String id) {
        this.id = id;
        this.score = 0;
        this.highScore = 0;
        this.isReady = false;
        this.isInLobby = false;
        this.color = getRandomHexColor();
        this.isInGame = false;
    }

    public static String getRandomHexColor() {
        Random random = new Random();
        // Generate the red, green, and blue components
        int red = random.nextInt(256); // 0-255
        int green = random.nextInt(256); // 0-255
        int blue = random.nextInt(256); // 0-255

        // Format them as a hexadecimal string
        return String.format("#%02X%02X%02X", red, green, blue);
    }

    public String getColor() {
        return this.color;
    }

    public void toggleReady() {
        this.isReady = !isReady;
    }

    public boolean getIsInGame() {
        return this.isInGame;
    }

    public void setInGame(boolean inGame) {
        this.isInGame = inGame;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setInLobby(boolean inLobby) {
        isInLobby = inLobby;
    }

    public void incrementScore() {
        this.score++;
        this.highScore = Math.max(this.score, this.highScore);
    }

    /* GETTERS */

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public int getScore() {
        return this.score;
    }

    public boolean isInLobby() {
        return this.isInLobby;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public String getGameId() {
        return this.gameId;
    }

}
