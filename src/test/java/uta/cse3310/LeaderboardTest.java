package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.Map;
import java.util.HashMap;

public class LeaderboardTest extends TestCase {
    public LeaderboardTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LeaderboardTest.class);
    }
    

    public void testLeaderboard() {
        // Create leaderboard
        Leaderboard leaderboard = new Leaderboard();

        // Test updating scores
        leaderboard.updateScore("Player 1", 100);
        leaderboard.updateScore("Player 2", 200);

        // Test getting individual score
        assertEquals(100, leaderboard.getScore("Player 1"));
        assertEquals(200, leaderboard.getScore("Player 2"));
        assertEquals(0, leaderboard.getScore("Nonexistent Player"));

        // Test getting all scores
        Map<String, Integer> allScores = leaderboard.getAllScores();
        assertEquals(2, allScores.size());
        assertTrue(allScores.containsKey("Player 1"));
        assertTrue(allScores.containsKey("Player 2"));
        assertEquals(Integer.valueOf(100), allScores.get("Player 1"));
        assertEquals(Integer.valueOf(200), allScores.get("Player 2"));

        // Testing complete
        System.out.println("----------------TESTING LEADERBOARD----------------");
        System.out.println("All scores in the leaderboard: ");
        for (Map.Entry<String, Integer> entry : allScores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("----------------TESTING COMPLETE-----------------");
    }
}
