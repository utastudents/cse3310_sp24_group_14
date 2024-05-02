package uta.cse3310;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.*;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.time.Instant;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonParseException;

<<<<<<< HEAD
// adding this for the GitHub hash - Uriel
import java.lang.Runtime.Version;


=======
>>>>>>> abf4267d9229bcc8ce14c4396ec4623e437071a0
public class App extends WebSocketServer implements GameEventListener {

    private HttpServer httpServer; // Assuming you have a HttpServer class that needs to be managed
    public Lobby lobby = new Lobby();
    public Map<WebSocket, Player> activeConnections = new HashMap<WebSocket, Player>();
<<<<<<< HEAD
    String Version;
    private int GameId = 1;
    private int ClientId = 0; // This is a global client ID, starts at 1
=======
>>>>>>> abf4267d9229bcc8ce14c4396ec4623e437071a0

    public App(int port, HttpServer httpServer) {
        super(new InetSocketAddress(port));
        this.httpServer = httpServer;
    }

    @Override
    public void onGameEnded(Game game) {

        for (Player player : game.getPlayers()) {
            WebSocket playerConn = findConnectionByPlayer(player);
            player.setInGame(false);
            lobby.getLeaderboard().updateScore(player);
            lobby.removeGame(game.getId());
            sendLobbyInfo(playerConn, player.getUsername());
        }

    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
<<<<<<< HEAD
        Version = System.getenv("VERSION"); // tony's github hash VERSION - Uriel
        if (Version ==null){
            Version = "no version";
        }
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
        System.out.println(Version);
        handleNewConnection(conn);

        // some serverevent stuff for the VERSION handle
        ServerEvent E = new ServerEvent();
        E.GameId = -1;
        ClientId++;
        E.ClientId = ClientId;
        E.ServerEvent = "ServerEvent";
        E.Version = Version;
        Gson gson = new Gson();
        String jsonString = gson.toJson(E);
        conn.send(jsonString);


=======
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
        handleNewConnection(conn);
>>>>>>> abf4267d9229bcc8ce14c4396ec4623e437071a0
    }

    public void handleNewConnection(WebSocket conn) {
        String uid = UUID.randomUUID().toString();
        Player player = new Player(uid);
        this.activeConnections.put(conn, player);

        JsonObject json = new JsonObject();
        json.addProperty("action", "initialConnection");
        json.addProperty("uid", uid);

        conn.send(json.toString());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " has closed");
        Player disconnectedPlayer = activeConnections.remove(conn);
        lobby.removePlayerFromLobby(disconnectedPlayer);
        broadcastPlayerLeft(disconnectedPlayer); // Notify all players about the player who left
        System.out.println(disconnectedPlayer.getUsername() + " disconnected");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        JsonObject messageObject = JsonParser.parseString(message).getAsJsonObject();

        String action = messageObject.get("action").getAsString();

        switch (action) {
            case "enterLobby":
                handleEnterLobby(conn, messageObject);
                break;
            case "sendMessage":
                handleSendMessage(conn, messageObject);
                break;
            case "createGame":
                handleCreateGame(conn, messageObject);
                break;
            case "joinGame":
                handleJoinGame(conn, messageObject);
                break;
            case "startGame":
                handleStartGame(conn, messageObject);
                break;
            case "checkWord":
                handleCheckWord(conn, messageObject);
        }

    }

    private void handleCheckWord(WebSocket conn, JsonObject messageObject) {
        String gameId = messageObject.get("gameId").getAsString();
        Game game = lobby.findGameById(gameId);
        if (game == null) {
            return; // Game not found, handle error appropriately
        }

        String username = messageObject.get("username").getAsString();
        Player player = lobby.findPlayerByUsername(username);
        Point start = new Point(messageObject.get("start").getAsJsonObject().get("row").getAsInt(),
                messageObject.get("start").getAsJsonObject().get("col").getAsInt());
        Point end = new Point(messageObject.get("end").getAsJsonObject().get("row").getAsInt(),
                messageObject.get("end").getAsJsonObject().get("col").getAsInt());

        WordGrid grid = game.getWordGrid();
        if (grid.isWordValid(start, end)) {
            grid.markWordFound(start, end, player.getColor());
            player.incrementScore();
            broadcastUpdateGame(game); // Broadcast updated game state to all clients
        }
    }

    private void handleStartGame(WebSocket conn, JsonObject messageObject) {
        String gameId = messageObject.get("gameId").getAsString();
        Game game = lobby.findGameById(gameId);

        if (game != null) {
            game.startGame();
            broadcastUpdateGame(game);
        } else {
            System.err.println("Game with ID " + gameId + " not found.");
        }
    }

    private void broadcastUpdateGame(Game game) {
        Gson gson = new Gson();
        JsonObject gameUpdateInfo = new JsonObject();
        gameUpdateInfo.addProperty("action", "updateGame");
        gameUpdateInfo.addProperty("gameId", game.getId());

        JsonArray playersInfo = new JsonArray();
        for (Player player : game.getPlayers()) {
            JsonObject playerInfo = new JsonObject();
            player.setInGame(true);
            playerInfo.addProperty("username", player.getUsername());
            playerInfo.addProperty("score", player.getScore());
            playerInfo.addProperty("color", player.getColor()); // Initial score, assuming 0 at start
            playersInfo.add(playerInfo);
        }
        gameUpdateInfo.add("players", playersInfo);

        // Grid data including colors
        JsonArray gridData = new JsonArray();
        WordGrid wordGrid = game.getWordGrid(); // Assuming you have a method getGrid()
        for (Cell[] row : wordGrid.getGrid()) {
            JsonArray gridRow = new JsonArray();
            for (Cell cell : row) {
                JsonObject cellObject = new JsonObject();
                cellObject.addProperty("letter", cell.getLetter());
                cellObject.addProperty("color", cell.getColor()); // Assuming Cell has getColor()
                gridRow.add(cellObject);
            }
            gridData.add(gridRow);
        }
        gameUpdateInfo.add("gridData", gridData);

        // Add word bank
        JsonArray wordBank = new JsonArray();
        for (Word word : wordGrid.getWords()) {
            JsonObject wordObject = new JsonObject();
            wordObject.addProperty("text", word.getText());
            wordObject.addProperty("found", word.isFound());
            wordBank.add(wordObject);
        }
        gameUpdateInfo.add("wordBank", wordBank);

        String messageStr = gson.toJson(gameUpdateInfo);
        // Broadcast to all players in the game
        game.getPlayers().forEach(player -> {
            WebSocket playerConn = findConnectionByPlayer(player);
            if (playerConn != null && playerConn.isOpen()) {
                playerConn.send(messageStr);
            }
        });
    }

    private WebSocket findConnectionByPlayer(Player player) {
        return activeConnections.entrySet().stream()
                .filter(entry -> entry.getValue().equals(player))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private void handleJoinGame(WebSocket conn, JsonObject messageObject) {
        String gameId = messageObject.get("gameId").getAsString();
        String username = messageObject.get("username").getAsString();
        Game game = lobby.findGameById(gameId);
        Player player = getPlayerByUsername(username);

        if (game != null && player != null) {
            game.addPlayer(player);
            broadcastUpdatePregame(game);
        }
    }

    private void broadcastUpdatePregame(Game game) {
        Gson gson = new Gson();
        JsonObject updateMessage = new JsonObject();
        updateMessage.addProperty("action", "updatePregame");
        updateMessage.addProperty("gameId", game.getId());
        updateMessage.addProperty("numPlayers", game.getPlayers().size());
        updateMessage.addProperty("roomSize", game.getMaxPlayers());

        JsonArray playersJson = new JsonArray();
        for (Player player : game.getPlayers()) {
            JsonObject playerInfo = new JsonObject();
            playerInfo.addProperty("username", player.getUsername());
            playersJson.add(playerInfo);
        }
        updateMessage.add("players", playersJson);

        String messageStr = gson.toJson(updateMessage);
        // Iterate over all active connections and send to those who are part of the
        // game
        activeConnections.forEach((socket, player) -> {
            if (game.getPlayers().contains(player) && socket.isOpen()) {
                socket.send(messageStr);
            }
        });
    }

    private void handleCreateGame(WebSocket conn, JsonObject messageObject) {
        String roomName = messageObject.get("roomName").getAsString();
        int roomSize = messageObject.get("roomSize").getAsInt();
        lobby.createGame(roomName, roomSize, this);
        broadcastGamesList();
    }

    private void broadcastGamesList() {
        Gson gson = new Gson();
        JsonArray gamesInfo = new JsonArray();
        for (Game game : lobby.getGames()) {
            JsonObject gameInfo = new JsonObject();
            gameInfo.addProperty("gameId", game.getId());
            gameInfo.addProperty("gameName", game.getName());
            gameInfo.addProperty("numPlayers", game.getPlayers().size());
            gameInfo.addProperty("maxPlayers", game.getMaxPlayers());
            gamesInfo.add(gameInfo);
        }

        JsonObject message = new JsonObject();
        message.addProperty("action", "updateGamesList");
        message.add("games", gamesInfo);
        broadcast(gson.toJson(message)); // Broadcast the entire list of games
    }

    private void broadcastPlayerLeft(Player player) {
        Gson gson = new Gson();
        JsonObject message = new JsonObject();
        message.addProperty("action", "playerLeft");
        message.addProperty("username", player.getUsername());
        broadcast(gson.toJson(message)); // Broadcast the player left message to all clients
    }

    private void handleEnterLobby(WebSocket conn, JsonObject messageObject) {
        String username = messageObject.get("username").getAsString();
        Player player = activeConnections.get(conn);
        Player existingPlayer = lobby.findPlayerByUsername(username);
        if (player != null && existingPlayer == null) {
            player.setUsername(username);
            lobby.getLeaderboard().updateScore(player);
            lobby.addPlayerToLobby(player); // Add player to active lobby list
            sendLobbyInfo(conn, player.getUsername());
            broadcastPlayerJoined(player); // Notify all players about the new player
        }
    }

    private void broadcastPlayerJoined(Player player) {
        Gson gson = new Gson();
        JsonObject joinMessage = new JsonObject();
        joinMessage.addProperty("action", "playerJoined");
        joinMessage.addProperty("username", player.getUsername());
        broadcast(gson.toJson(joinMessage));
    }

    private void handleSendMessage(WebSocket conn, JsonObject messageObject) {
        String senderUsername = activeConnections.get(conn).getUsername();
        Player sender = getPlayerByUsername(senderUsername); // Retrieve the Player object
        if (sender == null) {
            System.err.println("Sender not found: " + senderUsername);
            return; // Handle error or exit if player not found
        }

        String messageContent = messageObject.get("data").getAsJsonObject().get("message").getAsString();
        String timestamp = Instant.now().toString();

        // Create message object with Player instance
        Message newMessage = new Message(sender, messageContent);
        lobby.getGlobalChatroom().postMessage(newMessage);

        // Create JSON response to broadcast
        JsonObject msg = new JsonObject();
        msg.addProperty("action", "updateChat");
        msg.addProperty("sender", sender.getUsername());
        msg.addProperty("message", messageContent);

        // Broadcast message to all clients
        broadcast(msg.toString());
    }

    // Utility to broadcast messages to all connected clients
    public void broadcast(String message) {
        for (WebSocket socket : activeConnections.keySet()) {
            Player player = activeConnections.get(socket);
            if (socket.isOpen() && player.isInLobby()) {
                socket.send(message);
            }
        }
    }

    private void sendLobbyInfo(WebSocket conn, String username) {
        Gson gson = new Gson();
        JsonObject response = new JsonObject();
        response.addProperty("action", "enterLobby");
        response.addProperty("username", username);

        // Information about the games in the lobby
        JsonArray gamesInfo = new JsonArray();
        for (Game game : lobby.getGames()) {
            JsonObject gameInfo = new JsonObject();
            gameInfo.addProperty("gameId", game.getId());
            gameInfo.addProperty("gameName", game.getName());
            gameInfo.addProperty("numPlayers", game.getPlayers().size());
            gameInfo.addProperty("maxPlayers", game.getMaxPlayers());
            gamesInfo.add(gameInfo);
        }
        response.add("games", gamesInfo);

        // Including global chat messages
        JsonArray chatMessages = new JsonArray();
        for (Message message : lobby.getGlobalChatroom().getMessages()) {
            JsonObject msg = new JsonObject();
            msg.addProperty("sender", message.getSender().getUsername());
            msg.addProperty("message", message.getContent());
            msg.addProperty("timestamp", message.getTimestamp());
            chatMessages.add(msg);
        }
        response.add("chatMessages", chatMessages);

        // Including global leaderboard information
        JsonArray leaderboardInfo = new JsonArray();
        for (Player player : lobby.getLeaderboard().getLeaderboard()) {
            JsonObject playerInfo = new JsonObject();
            playerInfo.addProperty("username", player.getUsername());
            playerInfo.addProperty("score", player.getScore());
            leaderboardInfo.add(playerInfo);
        }
        response.add("leaderboard", leaderboardInfo);

        // Including active players in the lobby
        JsonArray activePlayers = new JsonArray();
        for (Player player : lobby.getActivePlayers()) {
            JsonObject playerInfo = new JsonObject();
            playerInfo.addProperty("username", player.getUsername());
            activePlayers.add(playerInfo);
        }
        response.add("activePlayers", activePlayers);

        // Send the constructed lobby information to the client
        conn.send(gson.toJson(response));
    }

    private Player getPlayerByUsername(String username) {
        for (Player player : activeConnections.values()) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null; // Return null if no player found
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
    }

    public static void main(String[] args) throws Exception {
<<<<<<< HEAD
        int httpPort = System.getenv("HTTP_PORT") != null ? Integer.parseInt(System.getenv("HTTP_PORT")) : 9014; // our team's port
        int websocketPort = System.getenv("WEBSOCKET_PORT") != null ? Integer.parseInt(System.getenv("WEBSOCKET_PORT"))
                : 9114;
=======
        int httpPort = System.getenv("HTTP_PORT") != null ? Integer.parseInt(System.getenv("HTTP_PORT")) : 9002;
        int websocketPort = System.getenv("WEBSOCKET_PORT") != null ? Integer.parseInt(System.getenv("WEBSOCKET_PORT"))
                : 9102;
>>>>>>> abf4267d9229bcc8ce14c4396ec4623e437071a0

        HttpServer httpServer = new HttpServer(httpPort, "./html");
        App websocketApp = new App(websocketPort, httpServer);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                websocketApp.stop();
                httpServer.stop();
                System.out.println("Servers have been stopped successfully.");
            } catch (Exception e) {
                System.err.println("Failed to stop servers: " + e.getMessage());
            }
        }));

        httpServer.start();
        System.out.println("HTTP Server started on port: " + httpPort);

        websocketApp.start();
        System.out.println("WebSocket Server started on port: " + websocketPort);
    }
}
