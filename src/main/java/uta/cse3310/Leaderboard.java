

package uta.cse3310;

import java.util.HashMap;
import java.util.Map;

public class Leaderboard {
    private Map<String, Integer> scores;

    public Leaderboard() {
        this.scores = new HashMap<>();
    }

    public void updateScore(String playerName, int score) {
        scores.put(playerName, score);
    }

    public int getScore(String playerName) {
        return scores.getOrDefault(playerName, 0);
    }

    public Map<String, Integer> getAllScores() {
        return scores;
    }
}