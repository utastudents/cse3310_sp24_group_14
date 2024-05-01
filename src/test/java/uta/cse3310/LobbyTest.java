package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class LobbyTest extends TestCase {
    private Lobby lobby;

    public LobbyTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(LobbyTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        lobby = new Lobby();
    }

    public void testAddAndRemovePlayer() {
        Player player = new Player("John Doe");
        assertEquals(0, lobby.getActivePlayers().size());
        lobby.addPlayerToLobby(player);
        assertTrue(lobby.getActivePlayers().contains(player));
        assertEquals(1, lobby.getActivePlayers().size());
        lobby.removePlayerFromLobby(player);
        assertFalse(lobby.getActivePlayers().contains(player));
        assertEquals(0, lobby.getActivePlayers().size());
    }

    public void testCreateGame() {
        GameEventListener listener = new GameEventListener() {
            @Override
            public void onGameEnded(Game game) {
                System.out.println("Game ended.");
            }
        };
        Game game = lobby.createGame("Test Game", 4, listener);
        assertNotNull(game);
        assertEquals("Test Game", game.getName());
        assertEquals(4, game.getMaxPlayers());
        assertTrue(lobby.getGames().contains(game));
    }

    public void testJoinGame() {
        GameEventListener listener = new GameEventListener() {
            @Override
            public void onGameEnded(Game game) {
                System.out.println("Game ended.");
            }
        };
        Player player = new Player("John Doe");
        Game game = lobby.createGame("Test Game", 1, listener); // Creating a game with 1 max player
        boolean joined = lobby.joinGame(game.getId(), player);
        assertTrue(joined);
        assertTrue(game.getPlayers().contains(player));

        // Test joining a full game
        Player anotherPlayer = new Player("Jane Doe");
        boolean joinedFullGame = lobby.joinGame(game.getId(), anotherPlayer);
        assertFalse(joinedFullGame);
    }

    public void testRemoveGame() {
        GameEventListener listener = new GameEventListener() {
            @Override
            public void onGameEnded(Game game) {
                System.out.println("Game ended: " + game.getName());
            }
        };
        Game game1 = lobby.createGame("Test Game 1", 4, listener);
        Game game2 = lobby.createGame("Test Game 2", 4, listener);
        assertEquals(2, lobby.getGames().size());
        lobby.removeGame(game1.getId());
        assertEquals(1, lobby.getGames().size());
        assertFalse(lobby.getGames().contains(game1));
        assertTrue(lobby.getGames().contains(game2));
    }
}
