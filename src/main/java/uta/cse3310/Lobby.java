// Name: Uriel Lujan
// Date: March 17, 2024
// TheWordSearchGame Lobby class

// package Group-14;

package uta.cse3310;


import java.util.ArrayList;

public class Lobby
{
    private ArrayList<Player> waitingPlayers = new ArrayList<>();

    // public Lobby()
    // {
    
    // }
    
    public void addPlayerToLobby(Player player)
    {
        waitingPlayers.add(player);
    }

    public void removePlayerFromLobby(Player player)
    {
        waitingPlayers.remove(player);
    }

    public void displayWaitingPlayers() // return type suggested returning 'Player' object but void is better
    {
        for(Player element : waitingPlayers)
        {
            System.out.println(element);
        }
    }

    public GameSession startGameForPlayers(ArrayList<Player> players)
    {
        GameSession newGame = new GameSession();
        return newGame;
    }
}
