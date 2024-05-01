package uta.cse3310;

import java.awt.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class GameTest extends TestCase {
    private Game game;
    private Player player1;
    private Player player2;

    public GameTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(GameTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        GameEventListener listener = new GameEventListener() {
            @Override
            public void onGameEnded(Game game) {
                System.out.println("Game ended.");
            }
        };
        game = new Game("Test Game", 2, listener);
        player1 = new Player("John Doe");
        player2 = new Player("Jane Doe");
    }

    public void testAddPlayer() {
        assertEquals(0, game.getNumPlayers());
        game.addPlayer(player1);
        assertEquals(1, game.getNumPlayers());
        game.addPlayer(player2);
        assertEquals(2, game.getNumPlayers());
    }

    public void testIsFull() {
        game.addPlayer(player1);
        game.addPlayer(player2);
        assertTrue(game.isFull());
    }

    public void testGameActivation() {
        assertFalse(game.isGameActive());
        game.startGame();
        assertTrue(game.isGameActive());
    }
}
