package uta.cse3310;

import junit.framework.TestCase;

public class ClientTest extends TestCase {
    private Client client;
    private GameSession mockSession;
    private Player player;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        player = new Player("TestPlayer", 0, new ArrayList<>());
        mockSession = new MockGameSession();
        client = new Client(player, mockSession);
    }

    public void testSendInputValidWord() {
        // Assume the mock session always validates any word correctly
        ((MockGameSession) mockSession).setWordValidation(true);
        client.sendInput("hello");
        
        // Asserting the word was accepted
        assertTrue("Word should be accepted", ((MockGameSession) mockSession).wasWordValidated());
        System.out.println("Test SendInput with valid word passed.");
    }

    public void testSendInputInvalidWord() {
        // Assume the mock session always invalidates the word
        ((MockGameSession) mockSession).setWordValidation(false);
        client.sendInput("invalid");

        // Asserting the word was rejected
        assertFalse("Word should be rejected", ((MockGameSession) mockSession).wasWordValidated());
        System.out.println("Test SendInput with invalid word passed.");
    }

    public void testUpdateGameState() {
        GameState gameState = new Client.GameState(100, "WordList", "Running");
        client.updateGameState(gameState);

        // Verify the output
        assertEquals("Running", gameState.getGameStatus());
        assertEquals(100, gameState.getCurrentPlayerScore());
        assertEquals("WordList", gameState.getCurrentWordList());
        System.out.println("Test updateGameState passed.");
    }

    // Helper mock class for GameSession
    private class MockGameSession extends GameSession {
        private boolean isValidWord;
        private boolean wordValidated = false;

        public void setWordValidation(boolean isValid) {
            this.isValidWord = isValid;
        }

        @Override
        public boolean validateWordSelection(Position startPos, Position endPos) {
            wordValidated = true;
            return isValidWord;
        }

        public boolean wasWordValidated() {
            return wordValidated;
        }

        @Override
        public void updateScores(Player player, int score) {
            // Mock implementation to simply print the score update
            System.out.println("Score updated for " + player.getNick() + ": " + score);
        }
    }
}
