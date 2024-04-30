package uta.cse3310;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    public enum GameStatus {
        WAITING("waiting"),
        IN_PROGRESS("in progress"),
        COMPLETED("completed");

        private final String status;

        GameStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return this.status;
        }
    }

    private String id;
    private String name;
    private List<Player> players;
    private int maxPlayers;
    private GameStatus gameStatus;

    public Game(String name, int maxPlayers) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.players = new ArrayList<>();
        this.maxPlayers = maxPlayers;
        this.gameStatus = GameStatus.WAITING;
    }

    public void addPlayer(Player player) {
        if (!isFull()) {
            players.add(player);
        }
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

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}
