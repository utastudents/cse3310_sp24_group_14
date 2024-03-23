// Names: Uriel Lujan
// Date: March 17, 2024
// TheWordSearchGame Server class

// package Group-14;
package uta.cse3310;

import java.util.ArrayList;

public class Server
{
    private ArrayList<GameSession> gameSessions;
    private ArrayList<Player> playersOnline;

    public void startServer()
    {

    }

    public void handleConnection(Client client)
    {

    }
    
    public GameSession createGameSession(ArrayList<Player> players)
    {
        GameSession newGame = new GameSession();
        return newGame;
    }

    public void broadcastMessage(String message)
    {

    }

}