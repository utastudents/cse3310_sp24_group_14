package uta.cse3310;

// import org.junit.Before;
// import org.junit.Test;
// import org.mockito.Mockito;

import java.util.ArrayList;

// import static org.junit.Assert.*;
// import static org.mockito.Mockito.*;

public class ServerTest {
    private Server server;
    private Client mockClient;
    private Player mockPlayer;

//    @Before
    // public void setUp() {
    //     server = new Server();
    //     mockClient = mock(Client.class);
    //     mockPlayer = mock(Player.class);
    //     when(mockClient.getPlayer()).thenReturn(mockPlayer);
    //     when(mockPlayer.getNick()).thenReturn("TestPlayer");
    // }

//    @Test
    public void testHandleConnection() {
        // server.handleConnection(mockClient);
        // assertNotNull(server.getCurrentSessionForPlayer(mockPlayer));
        // assertEquals("Server should have exactly one client online after connection", 1, server.getClientsOnline().size());
    }

//    @Test
    public void testCreateGameSession() {
        ArrayList<Player> players = new ArrayList<>();
        players.add(mockPlayer);
        //GameSession session = server.createGameSession(players);
        // assertNotNull("Game session should be created", session);
        // assertTrue("Game session should contain the player", session.getPlayers().contains(mockPlayer));
    }

//    @Test
    public void testBroadcastMessage() {
    //    server.handleConnection(mockClient);
    //    server.broadcastMessage("Hello, world!");
    //    verify(mockClient, times(1)).sendInput("Hello, world!");
    }

//    @Test
    public void testEndServer() {
        // server.handleConnection(mockClient);
        // server.endServer();
        // assertTrue("Server should have no clients online after shutdown", server.getClientsOnline().isEmpty());
        // assertTrue("Server should have no game sessions after shutdown", server.getGameSessions().isEmpty());
    }
}
