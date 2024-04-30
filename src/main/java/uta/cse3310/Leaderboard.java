

package uta.cse3310;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard {
    private List<Player> players;

    public Leaderboard() {
        this.players = new ArrayList<>();
    }

    /**
     * Updates or adds a player's score to the leaderboard.
     * If the player is new, they are added. If they already exist, their score is updated.
     *
     * @param player The player to update.
     */
    public void updateScore(Player player) {
        // Check if the player already exists in the leaderboard
        for (Player p : players) {
            if (p.getId().equals(player.getId())) {
                // If exists, update the player's score
                p.setScore(player.getScore());
                sortLeaderboard();
                return;
            }
        }
        // If not exists, add new player and sort
        players.add(player);
        sortLeaderboard();
    }

    /**
     * Retrieves the list of players sorted by score in descending order.
     *
     * @return Sorted list of players.
     */
    public List<Player> getLeaderboard() {
        return Collections.unmodifiableList(players); // This makes the list read-only to the caller
    }

    /**
     * Sorts the leaderboard by player score in descending order.
     */
    private void sortLeaderboard() {
        Collections.sort(players, Comparator.comparingInt(Player::getScore).reversed());
    }
}
