package uta.cse3310;
import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private List<String> players;
    private static final int MAX_PLAYERS = 4; // Maximum players allowed in the lobby
    private boolean gameStarted;

    public Lobby() {
        this.players = new ArrayList<>();
        this.gameStarted = false;
    }


    public boolean addPlayer(String playerName) {
        if (players.size() < MAX_PLAYERS) {
            players.add(playerName);
            return true;
        } else {
            return false; // Lobby is full
        }
    }

    public void removePlayer(String playerName) {
        players.remove(playerName);
    }

    public List<String> getPlayers() {
        return players;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
}