//Modified by Tien Dat Do 04/14/2024

package uta.cse3310;

import java.util.Map;
import java.util.HashMap;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//added by Uriel 4/22/2024
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Vector;
import java.time.Instant;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// export HTTP_PORT = 9014;
// export WEBSOCKET_PORT = 9114;

public class GameSession {
    public WordGrid wordGrid;
    private Map<Player, Integer> scores = new HashMap<>();
    private ArrayList<Player> players;
    private Timer timer;
    private ChatBox chatBox;

    // public GameSession(int port) {
    //     super(new InetSocketAddress(port));
    //   }
    
    //   public GameSession(InetSocketAddress address) {
    //     super(address);
    //   }
    
    //   public GameSession(int port, Draft_6455 draft) {
    //     super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
    //   }

    // @Override
    // public void onOpen(WebSocket conn, ClientHandshake handshake) {

    // // connectionId++;

    // System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

    // // ServerEvent E = new ServerEvent();

    // // search for a game needing a player
    // // Game G = null;
    // // for (Game i : ActiveGames) {
    // //   if (i.Players == uta.cse3310.PlayerType.XPLAYER) {
    // //     G = i;
    // //     System.out.println("found a match");
    // //   }
    // }

    public void startGame() {
        // wordGrid = selectWordGrid();
        players = new ArrayList<>();
        chatBox = new ChatBox();
        // Initialize the timer for the game session (assuming a default duration of 10 minutes)
        startTimer(600);
    }

    //  private WordGrid selectWordGrid() { // commented out because it is causing maven to crash please fix
    //      int testGridIndex = Integer.parseInt(System.getenv("TEST_GRID"));
    //      // Placeholder logic: Select a word grid based on the test grid index
    //      WordGrid selectedGrid = WordGrid.selectGrid(testGridIndex);
    //      return selectedGrid;
    //  }

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

    public boolean isFull(){
        // TODO: return if session is full
        return true;
    }

    //  public boolean validateWordSelection(Position startPos, Position endPos) { // causing maven to crash please fix
    //      // Placeholder logic: Check if the selected positions form a valid word on the grid
    //      return wordGrid.isValidWord(startPos, endPos);
    //  }

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

    // public void censorMessage(String message) { // causing maven to crash please fix
    //     // Placeholder logic: Censor inappropriate words in the message
    //     String censoredMessage = chatBox.censor(message);
    //     sendMessageToAllPlayers(censoredMessage);
    // }

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
        //  wordGrid.highlightWord(startPos, endPos, player);
     }

    public void showWinner(Player winner) {
        // Display the winner of the game session
        System.out.println("Winner: " + winner.getNick());
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

    // public static void main(String args[]) {
    //     int port = 9014;
    //     GameSession gameSession = new GameSession();
    //     gameSession.startGame();


    //     String HttpPort = System.getenv("HTTP_PORT");
    //     // int port = 9014;
    //     if (HttpPort!=null) {
    //         port = Integer.valueOf(HttpPort);
    //       }

    //     HttpServer H = new HttpServer(port, "./html");
    //     H.start();
    //     System.out.println("http Server started on port: " + port);

    //     // create and start the websocket server
    //     port = 9114;
    //     String WSPort = System.getenv("WEBSOCKET_PORT");
    //     if (WSPort!=null) {
    //         port = Integer.valueOf(WSPort);
    //       }
    //     System.out.println("websocket Server started on port: " + port);
    // }
}
