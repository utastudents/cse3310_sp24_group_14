package uta.cse3310;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WordSearchApp extends WebSocketServer {

    private Vector<WordSearchGame> activeGames = new Vector<>();
    private int gameId = 1;
    private int connectionId = 0;
    private Instant startTime;
    private Statistics stats;

    public WordSearchApp(int port) {
        super(new InetSocketAddress(port));
    }

    public WordSearchApp(InetSocketAddress address) {
        super(address);
    }

    public WordSearchApp(int port, Draft_6455 draft) {
        super(new InetSocketAddress(port), Collections.singletonList(draft));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connectionId++;
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

        ServerEvent event = new ServerEvent();
        WordSearchGame game = findOrCreateGame();
        event.setYouAre(game.getCurrentPlayerType());
        event.setGameId(game.getGameId());

        conn.setAttachment(game);

        Gson gson = new Gson();
        conn.send(gson.toJson(event));

        updateStatsAndBroadcast(game);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " has closed");
        WordSearchGame game = conn.getAttachment();
        if (game != null) {
            game = null;
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Gson gson = new GsonBuilder().create();
        UserEvent userEvent = gson.fromJson(message, UserEvent.class);
        WordSearchGame game = conn.getAttachment();
        game.update(userEvent);

        updateStatsAndBroadcast(game);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println(conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            // Handle specific errors if needed
        }
    }

    @Override
    public void onStart() {
        setConnectionLostTimeout(0);
        stats = new Statistics();
        startTime = Instant.now();
    }

    private WordSearchGame findOrCreateGame() {
        WordSearchGame game = null;
        for (WordSearchGame existingGame : activeGames) {
            if (existingGame.getCurrentPlayerType() == PlayerType.XPLAYER) {
                game = existingGame;
                break;
            }
        }
        if (game == null) {
            game = new WordSearchGame(stats);
            game.setGameId(gameId++);
            game.setCurrentPlayerType(PlayerType.XPLAYER);
            activeGames.add(game);
        } else {
            game.setCurrentPlayerType(PlayerType.OPLAYER);
            game.startGame();
        }
        return game;
    }

    private void updateStatsAndBroadcast(WordSearchGame game) {
        stats.setRunningTime(Duration.between(startTime, Instant.now()).toSeconds());

        Gson gson = new Gson();
        String jsonGameState = gson.toJson(game);
        broadcast(jsonGameState);
    }

    private String escape(String s) {
        return s.replaceAll("\"", "\\\"");
    }

    public static void main(String[] args) {
        int port = 9080; // Default port for HTTP server
        String httpPortEnv = System.getenv("HTTP_PORT");
        if (httpPortEnv != null) {
            port = Integer.parseInt(httpPortEnv);
        }

        HttpServer httpServer = new HttpServer(port, "./html");
        httpServer.start();
        System.out.println("HTTP server started on port: " + port);

        port = 9180; // Default port for WebSocket server
        String webSocketPortEnv = System.getenv("WEBSOCKET_PORT");
        if (webSocketPortEnv != null) {
            port = Integer.parseInt(webSocketPortEnv);
        }

        WordSearchApp webSocketServer = new WordSearchApp(port);
        webSocketServer.setReuseAddr(true);
        webSocketServer.start();
        System.out.println("WebSocket server started on port: " + port);
    }
}
