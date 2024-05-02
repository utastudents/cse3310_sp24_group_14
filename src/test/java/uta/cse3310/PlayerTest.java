package uta.cse3310;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {

    public void testPlayerInitialization() {
        Player player = new Player("123");
        assertEquals("123", player.getId());
        assertNotNull(player.getColor());
        assertFalse(player.isInLobby());
        assertEquals(0, player.getScore());
        assertEquals(0, player.getHighScore());
        assertFalse(player.getIsInGame());
    }

    public void testColorUniqueness() {
        Player player1 = new Player("123");
        Player player2 = new Player("456");
        assertNotSame(player1.getColor(), player2.getColor());
    }

    public void testScoreAndHighScore() {
        Player player = new Player("123");
        player.setScore(10);
        assertEquals(10, player.getScore());
        player.incrementScore();
        assertEquals(11, player.getScore());
        assertEquals(11, player.getHighScore());
        player.setScore(5);
        assertEquals(5, player.getScore());
        assertEquals(11, player.getHighScore()); // highScore should not decrease
    }

    public void testInGameStatus() {
        Player player = new Player("123");
        assertFalse(player.getIsInGame());
        player.setInGame(true);
        assertTrue(player.getIsInGame());
    }
}
