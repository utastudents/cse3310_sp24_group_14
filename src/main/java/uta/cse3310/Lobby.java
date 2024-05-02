package uta.cse3310;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Lobby {
    private List<Game> games;
    private Leaderboard leaderboard;
    private Chatroom globalChatroom;
    private Set<Player> activePlayers;

    public Lobby() {
        this.games = new ArrayList<>();
        this.leaderboard = new Leaderboard();
        this.globalChatroom = new Chatroom();
        this.activePlayers = new HashSet<>();
    }

    public Game createGame(String name, int maxPlayers, GameEventListener listener) {
        Game newGame = new Game(name, maxPlayers, listener);
        games.add(newGame);
        return newGame;
    }

    public boolean removeGame(String gameId) {
        return games.removeIf(game -> game.getId().equals(gameId));
    }

    public void addPlayerToLobby(Player player) {
        player.setInLobby(true);
        activePlayers.add(player);
    }

    public void removePlayerFromLobby(Player player) {
        player.setInLobby(false);
        activePlayers.remove(player);
    }

    public Set<Player> getActivePlayers() {
        return activePlayers;
    }

    public boolean joinGame(String id, Player player) {
        Game game = findGameById(id);
        if (game != null && !game.isFull()) {
            game.addPlayer(player);
            return true;
        }
        return false;
    }

    public List<Game> getGames() {
        return new ArrayList<>(games);
    }

    public Chatroom getGlobalChatroom() {
        return this.globalChatroom;
    }

    public Player findPlayerByUsername(String username) {
        for (Player player : activePlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null; 
    }

    private Game findGameByName(String name) {
        for (Game game : games) {
            if (game.getName().equals(name)) {
                return game;
            }
        }
        return null; // Return null if no game is found with the given name
    }

    public Game findGameById(String id) {
        for (Game game : games) {
            if (game.getId().equals(id)) {
                return game;
            }
        }
        return null; // Return null if no game is found with the given name
    }

    public Leaderboard getLeaderboard() {
        return this.leaderboard;
    }
}
