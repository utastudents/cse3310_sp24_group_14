//Modified by Tien Dat Do 04/14/2024

package uta.cse3310;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameSession {
    public WordGrid wordGrid;
    private Map<Player, Integer> scores = new HashMap<>();
    private ArrayList<Player> players;
    private Timer timer;
    private ChatBox chatBox;

    public void startGame() {
        wordGrid = selectWordGrid();
        players = new ArrayList<>();
        chatBox = new ChatBox();
        // Initialize the timer for the game session (assuming a default duration of 10 minutes)
        startTimer(600);
    }

    private WordGrid selectWordGrid() {
        int testGridIndex = Integer.parseInt(System.getenv("TEST_GRID"));
        // Placeholder logic: Select a word grid based on the test grid index
        WordGrid selectedGrid = WordGrid.selectGrid(testGridIndex);
        return selectedGrid;
    }

    public void endGame() {
        stopTimer();
        displayPlayerScores();
        showWinner(getWinner());
        // Other tasks to conclude the game
    }

    private Player getWinner() {
        Player winner = null;
        int maxScore = Integer.MIN_VALUE;
        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                winner = entry.getKey();
            }
        }
        return winner;
    }

    public boolean validateWordSelection(Position startPos, Position endPos) {
        // Placeholder logic: Check if the selected positions form a valid word on the grid
        return wordGrid.isValidWord(startPos, endPos);
    }

    public void updateScores(Player player, int score) {
        scores.put(player, scores.getOrDefault(player, 0) + score);
    }

    public void startTimer(int duration) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                endGame();
            }
        }, duration * 1000);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void enterLobby(Player player) {
        // Add player to the lobby
        players.add(player);
    }

    public void exitLobby(Player player) {
        // Remove player from the lobby
        players.remove(player);
    }

    public void joinGame(Player player) {
        // Add player to the game session
        players.add(player);
    }

    public void leaveGame(Player player) {
        // Remove player from the game session
        players.remove(player);
    }

    public void displayChatHistory(Player player) {
        // Placeholder logic: Display chat history to the player
        chatBox.displayHistory(player);
    }

    public void censorMessage(String message) {
        // Placeholder logic: Censor inappropriate words in the message
        String censoredMessage = chatBox.censor(message);
        sendMessageToAllPlayers(censoredMessage);
    }

    public void collapseChatBox() {
        // Placeholder logic: Collapse the chat box
        chatBox.collapse();
    }

    public void sendMessage(Player sender, String message) {
        // Placeholder logic: Send a message from the sender to all players
        chatBox.sendToAll(sender, message);
    }

    public void highlightLetter(Position position, Player player) {
        // Placeholder logic: Highlight a letter in the grid for a player
        wordGrid.highlightLetter(position, player);
    }

    public void highlightWord(Position startPos, Position endPos, Player player) {
        // Placeholder logic: Highlight a word in the grid for a player
        wordGrid.highlightWord(startPos, endPos, player);
    }

    public void showWinner(Player winner) {
        // Display the winner of the game session
        System.out.println("Winner: " + winner.getName());
    }

    public void displayPlayerScores() {
        // Display player scores to all players
        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            Player player = entry.getKey();
            int score = entry.getValue();
            player.displayScore(score);
        }
    }

    public class GameEndTask extends TimerTask {
    private GameSession gameSession;

    public GameEndTask(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    @Override
    public void run() {
        gameSession.endGame();
    }
}

    public static void main(String args[]) {
        GameSession gameSession = new GameSession();
        gameSession.startGame();
    }
}
