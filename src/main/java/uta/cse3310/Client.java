// Name: Tien Dat Do
// Date: March 21, 2024

// package Group-14;
package uta.cse3310;


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

    public class GameState {
        
    }
	
	//Methods
    public void connectToServer(Server server) {
        
    }

    public void sendInput(String word) {
        
    }

    public void updateGameState(GameState gameState) {
        
    }
}

