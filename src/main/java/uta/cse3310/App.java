package uta.cse3310;

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
import com.google.gson.JsonParseException;

public class App extends WebSocketServer {

    private HttpServer httpServer; // Assuming you have a HttpServer class that needs to be managed
    public Lobby lobby = new Lobby();
    public Map<WebSocket, Player> activeConnections = new HashMap<WebSocket, Player>();

    public App(int port, HttpServer httpServer) {
        super(new InetSocketAddress(port));
        this.httpServer = httpServer;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
        handleNewConnection(conn);
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

        JsonObject response = new JsonObject();
        switch (action) {
            case "enterLobby":
                handleEnterLobby(conn, messageObject);
                break;
            case "sendMessage":
                handleSendMessage(conn, messageObject);
                break;

        }

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
        if (player != null) {
            player.setUsername(username);
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
            gameInfo.addProperty("status", game.getGameStatus().toString());
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
        int httpPort = System.getenv("HTTP_PORT") != null ? Integer.parseInt(System.getenv("HTTP_PORT")) : 9002;
        int websocketPort = System.getenv("WEBSOCKET_PORT") != null ? Integer.parseInt(System.getenv("WEBSOCKET_PORT"))
                : 9102;

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
