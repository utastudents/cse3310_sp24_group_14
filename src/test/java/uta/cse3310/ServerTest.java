package uta.cse3310;

import junit.framework.TestCase;

public class ServerTest extends TestCase {
    private Server server;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        server = new TestableServer(new InetSocketAddress(8080));
    }

    public void testOnOpenAddsClient() {
        // Simulate opening a connection (mocking manually)
        WebSocket fakeWebSocket = new FakeWebSocket();
        server.onOpen(fakeWebSocket, null);

        // Assert conditions
        assertEquals(1, server.clientsOnline.size());
        assertNotNull(server.clientsOnline.get(fakeWebSocket));
        System.out.println("Test OnOpen: New client should be added.");
    }

    private static class TestableServer extends Server {
        public TestableServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        protected Client createClient(WebSocket conn) {
            return new Client(new Player("TestPlayer"), null);
        }
    }

    private static class FakeWebSocket implements WebSocket {
        // Implement necessary methods as no-op or minimal functionality
    }
}
