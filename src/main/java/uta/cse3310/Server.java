// Names: Uriel Lujan
// Date: March 17, 2024
// TheWordSearchGame Server class

package uta.cse3310;

import java.util.ArrayList;

public class Server {
    private ArrayList<GameSession> gameSessions;
    private ArrayList<Client> clientsOnline;

    public Server() {
        gameSessions = new ArrayList<>();
        clientsOnline = new ArrayList<>();
    }

    public void startServer() {
        // Logic to start server operations, such as listening for client connections
        System.out.println("Server started. Waiting for players...");
    }

    public void handleConnection(Client client) {
        // Add client to the online list and create a new session if necessary
        clientsOnline.add(client);
        System.out.println("New client connected: " + client.getPlayer().getNick());
        assignClientToSession(client);
    }

    private void assignClientToSession(Client client) {
        // Logic to assign a client to an existing game session or create a new one
        for (GameSession session : gameSessions) {
            // if (!session.isFull()) {
            //     session.addPlayer(client.getPlayer());
            //     client.setCurrentSession(session);
            //     System.out.println("Player " + client.getPlayer().getNick() + " added to existing session.");
            //     return;
            // }
        }

        // Create new session if no available session
        GameSession newSession = createGameSession(new ArrayList<>());
    //    newSession.addPlayer(client.getPlayer());
        client.setCurrentSession(newSession);
        gameSessions.add(newSession);
        System.out.println("New session created and player added: " + client.getPlayer().getNick());
    }

    public GameSession createGameSession(ArrayList<Player> players) {
        GameSession newGame = new GameSession();
        for (Player player : players) {
        //    newGame.addPlayer(player);
        }
        return newGame;
    }

    public void broadcastMessage(String message) {
        // Send a message to all clients
        for (Client client : clientsOnline) {
            client.sendInput(message);
        }
        System.out.println("Broadcast message sent to all clients: " + message);
    }

    public void endServer() {
        // Logic to cleanly shut down the server
        System.out.println("Server is shutting down...");
        for (GameSession session : gameSessions) {
            session.endGame();
        }
        clientsOnline.clear();
        gameSessions.clear();
        System.out.println("Server shutdown complete.");
    }
}
