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
    private String color;                //player's unique highlighting color; color attribute in another location
    private boolean isInLobby;


    public Player(String id) {
        this.id = id;
        this.score = 0;
        this.highScore = 0;
        this.isReady = false;
        this.isInLobby = false;
    }
    
    public void toggleReady() {
        this.isReady = !isReady;
    }

    public void increaseScore() {
        this.score++;
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

    /* GETTERS */

    public String getId() {
        return this.username;
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
