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

    public Game createGame(String name, int maxPlayers) {
        Optional<Game> existingGame = findGameByName(name);
        if (existingGame.isPresent()) {
            throw new IllegalArgumentException("Game with name " + name + " already exists.");
        }
        Game newGame = new Game(name, maxPlayers);
        games.add(newGame);
        return newGame;
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

    public boolean joinGame(String name, Player player) {
        Optional<Game> game = findGameByName(name);
        if (game.isPresent() && !game.get().isFull()) {
            game.get().addPlayer(player);
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

    private Optional<Game> findGameByName(String name) {
        return games.stream()
                .filter(game -> game.getName().equals(name))
                .findFirst();
    }

    public Leaderboard getLeaderboard() {
        return this.leaderboard;
    }
}
