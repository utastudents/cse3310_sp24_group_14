package uta.cse3310;
import uta.cse3310.Position;



public class Client {
    private Player player;
    private GameSession currentSession;

    public Client(Player player, GameSession currentSession) {
        this.player = player;
        this.currentSession = currentSession;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameSession getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(GameSession currentSession) {
        this.currentSession = currentSession;
    }


    // Sends a word input from the player to the server or game session
    public void sendInput(String word) {
        if (currentSession != null) {
            Position startPos = determineStartPosition(word);
            Position endPos = determineEndPosition(word);

            if (startPos == null || endPos == null) {
                System.out.println("Word positions could not be determined.");
                return;
            }

            boolean isValid = currentSession.validateWordSelection(startPos, endPos);
            if (isValid) {
                System.out.println("Word accepted: " + word);
                currentSession.updateScores(player, word.length()); // Assuming score is word length
            } else {
                System.out.println("Word rejected: " + word);
            }
        } else {
            System.out.println("No active game session.");
        }
    }

    private Position determineStartPosition(String word) {
        // Implement logic based on your game's grid to find the start position of the word
        return new Position(0, 0); // Placeholder
    }

    private Position determineEndPosition(String word) {
        // Implement logic based on your game's grid to find the end position of the word
        return new Position(0, word.length() - 1); // Placeholder
    }

    // Updates the client's view of the game state
    public void updateGameState(GameState gameState) {
        // Assume GameState includes all necessary information about the current game
        System.out.println("Game state updated: " + gameState.toString());
    }

    // Inner class to represent game states
    public class GameState {
        private int currentPlayerScore;
        private String currentWordList;
        private String gameStatus; // e.g., "Running", "Waiting for Players", "Completed"

        public GameState(int score, String wordList, String status) {
            this.currentPlayerScore = score;
            this.currentWordList = wordList;
            this.gameStatus = status;
        }

        public int getCurrentPlayerScore() {
            return currentPlayerScore;
        }

        public String getCurrentWordList() {
            return currentWordList;
        }

        public String getGameStatus() {
            return gameStatus;
        }

        @Override
        public String toString() {
            return "Score: " + currentPlayerScore + ", Words: " + currentWordList + ", Status: " + gameStatus;
        }
    }
}
