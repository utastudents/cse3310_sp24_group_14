package uta.cse3310;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server extends WebSocketServer {
    private final List<GameSession> gameSessions;
    private final Map<WebSocket, Client> clientsOnline;
    private WebSocket conn;  // WebSocket connection

    public Server(InetSocketAddress address) {
        super(address);
        gameSessions = new ArrayList<>();
        clientsOnline = new HashMap<>();
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully on port: " + getPort());
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection: " + conn.getRemoteSocketAddress());

        Player player = new Player("Guest" + conn.getRemoteSocketAddress().getPort());
        Client client = new Client(player, null);
        clientsOnline.put(conn, client);
        System.out.println("New client connected: " + player.getNick());
        assignClientToSession(client, conn);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed connection: " + conn.getRemoteSocketAddress() + " [" + reason + "]");
        Client client = clientsOnline.remove(conn);
        if (client != null && client.getCurrentSession() != null) {
            client.getCurrentSession().exitLobby(client.getPlayer());
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from " + conn.getRemoteSocketAddress() + ": " + message);
        // Handle messages like start new game, play a word, etc.
        processMessage(conn, message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("Error on " + conn + ": " + ex.getMessage());
        ex.printStackTrace();
    }

    private void assignClientToSession(Client client, WebSocket conn) {
        for (GameSession session : gameSessions) {
            if (!session.isFull()) {
                session.enterLobby(client.getPlayer());
                client.setCurrentSession(session);
                System.out.println("Player " + client.getPlayer().getNick() + " added to existing session.");
                return;
            }
        }

        GameSession newSession = createGameSession(new ArrayList<>());
        newSession.enterLobby(client.getPlayer());
        client.setCurrentSession(newSession);
        gameSessions.add(newSession);
        System.out.println("New session created and player added: " + client.getPlayer().getNick());
    }

    private GameSession createGameSession(List<Player> players) {
        GameSession newGame = new GameSession();
        for (Player player : players) {
            newGame.enterLobby(player);
        }
        return newGame;
    }

    public void processMessage(WebSocket conn, String message) {
        Client client = clientsOnline.get(conn);
        if (client == null) {
            System.out.println("Message from an unregistered connection: " + conn.getRemoteSocketAddress());
            return;
        }

        // Log received message
        System.out.println("Message from " + client.getPlayer().getNick() + ": " + message);

        // Split the command from the rest of the message
        String[] parts = message.split(" ", 2);
        String command = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
            case "start":
                if (client.getCurrentSession() != null) {
                    client.getCurrentSession().startGame();
                } else {
                    // Handle case where there is no current session (e.g., create one or error message)
                    System.out.println("No game session available for " + client.getPlayer().getNick());
                }
                break;
            case "select":
                if (client.getCurrentSession() != null && args.matches("\\d+ \\d+ \\d+ \\d+")) {
                    String[] positions = args.split(" ");
                    Position startPos = new Position(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
                    Position endPos = new Position(Integer.parseInt(positions[2]), Integer.parseInt(positions[3]));
                    // if (client.getCurrentSession().validateWordSelection(startPos, endPos)) { // causing maven to crash please fix
                    //     System.out.println("Word selection valid for " + client.getPlayer().getNick());
                    //     // Optionally update scores, handle game logic, etc.
                    //     client.getCurrentSession().updateScores(client.getPlayer(), calculateScore(startPos, endPos));
                    // } else {
                    //     System.out.println("Invalid word selection by " + client.getPlayer().getNick());
                    // }
                }
                else {
                    System.out.println("Invalid command or session not available for word selection.");
                }
                break;
            case "leave":
                if (client.getCurrentSession() != null) {
                    client.getCurrentSession().leaveGame(client.getPlayer());
                }
                break;
            case "chat":
                if (client.getCurrentSession() != null) {
                    client.getCurrentSession().sendMessage(client.getPlayer(), args);
                }
                break;
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }

    private int calculateScore(Position startPos, Position endPos) {
        return Math.abs(endPos.getX() - startPos.getX()) + Math.abs(endPos.getY() - startPos.getY());
    }

    public static void main(String[] args) {
        int port = 8887; // Your port number
        Server server = new Server(new InetSocketAddress(port));
        server.start(); // Start the server
    }
}
