package uta.cse3310;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.List;
import java.util.ArrayList;

public class lobbytest extends TestCase {
    public lobbytest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(lobbytest.class);
    }

    public void testLobby() {
        // Create lobby
        Lobby lobby = new Lobby();

        // Test adding players
        assertTrue(lobby.addPlayer("Player 1"));
        assertTrue(lobby.addPlayer("Player 2"));
        assertFalse(lobby.addPlayer("Player 3")); // Lobby is full

        // Test removing players
        lobby.removePlayer("Player 1");
        List<String> players = lobby.getPlayers();
        assertFalse(players.contains("Player 1"));
        assertTrue(players.contains("Player 2"));

        // Test game started flag
        assertFalse(lobby.isGameStarted());
        lobby.setGameStarted(true);
        assertTrue(lobby.isGameStarted());

        // Additional tests can be added here if needed

        // Testing complete
        System.out.println("----------------TESTING LOBBY----------------");
        System.out.println("Players in the lobby after removal: ");
        for (String player : players) {
            System.out.println(player);
        }
        System.out.println("Is game started? " + lobby.isGameStarted());
        System.out.println("----------------TESTING COMPLETE-----------------");
    }
}

