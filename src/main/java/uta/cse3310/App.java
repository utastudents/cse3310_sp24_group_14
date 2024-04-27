package uta.cse3310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.ArrayList;
import java.time.Instant;
import java.time.Duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;

public class App extends WebSocketServer {

  // All games currently underway on this server are stored in
  // the vector ActiveGames
  private Vector<GameSession> ActiveGames = new Vector<GameSession>(); // changed by Kawther, hook up with GameSession

  private int GameId = 1;
  private int ClientId = 0; // This is a global client ID, starts at 1

  private int connectionId = 0;
  private Instant startTime;
  private Lobby lobby;
  public ArrayList<Player> players = new ArrayList<Player>();

  // private Statistics stats;

  public App(int port) {
    super(new InetSocketAddress(port));
    lobby = new Lobby(); // create lobby
  }

  public App(InetSocketAddress address) {
    super(address);
  }

  public App(int port, Draft_6455 draft) {
    super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {

    connectionId++;

    System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

    // This is the first time we have heard from this client, so let's send him some
    // information;

    ServerEvent E = new ServerEvent();

    // We won't have a game ID for a while. Setting it to -1 for now
    // This may need to be moved to a different class, but in the interest
    // of not rippling changes into code I do not understand, it is here.
    E.GameId = -1;
    ClientId++;
    E.ClientId = ClientId;
    // This line is a hack, to make it easier in the javascript to ID
    // what class is in the msg
    E.ServerEvent = "ServerEvent";

    // Make E, an instance of the class ServerEvent into a json string
    Gson gson = new Gson();
    String jsonString = gson.toJson(E);

    // And send it to only the client that just opened the connection
    conn.send(jsonString);

    // Some info that will help in debugging
    System.out
        .println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " + connectionId + " "
            + escape(jsonString));

    // Adding a null pointer for a local data in the WebSocket instance is probably
    // not needed
    // but I am going to do it anyway. This is an indicator that this client has not
    // been put in the lobby and is not in a game. Yet.
    conn.setAttachment(null);

    // There is not much more that can be done. The client web page has loaded, and
    // the websocket connection is created.
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    System.out.println(conn + " has closed");
    // Retrieve the game tied to the websocket connection
    GameSession GS = conn.getAttachment();
    GS = null;
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    System.out
        .println("< " + Duration.between(startTime, Instant.now()).toMillis() + " " + "-" + " " + escape(message));

    GameSession GS = null;
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    UserEvent U = gson.fromJson(message, UserEvent.class);


    // This is where all messages come in from the client. Different messages are
    // received based upon where
    // we are in the game. To make it easier to identify the messages, a field in
    // the message is set equal
    // to the class name.

    if (message.indexOf("NameEvent") > 0) {
      NameEvent N = gson.fromJson(message, NameEvent.class);

      // Now we have a name
      System.out.println(N.name);

      // Put it in the lobby
      lobby.addPlayer(N.name, N.ClientId);

    } else if (message.indexOf("something") > 0) {  //do i change "something" to JoinGame? prof put it like that so i'm not sure if i should change it
      // here is where we get the information to join a game
      // need to call conn.setAttachment here and pass it the
      // GameSession object
        //kawther notes : from tic tac toe
        //ServerEvent E = new ServerEvent(); 
        //E.ClientId = GS.ClientId;   
        //E.GameId = GS.GameId;

        conn.setAttachment(GS);

        // Note only send to the single connection
        String jsonString = gson.toJson(GS);
        conn.send(jsonString);
        System.out
          .println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " + connectionId + " "
              + escape(jsonString));

      // Update the running time
      //stats.setRunningTime(Duration.between(startTime, Instant.now()).toSeconds());


    } else if (message.indexOf("aaaaa") > 0) { //i'm not sure what to change the "" to in the indexOf("aaaaa"); gets words player clicks
      // Get our Game Session
      GS = conn.getAttachment();
      GS.Update(U);

    } else if (message.indexOf("StartGame") > 0) {
        // Start game when there are at least two players & max 4
        if (lobby.getPlayers().size() >= 2 && !GS.isFull() ) {
          GameSession G = conn.getAttachment(); //gets correct game for websocket
          if(G != null) {
            G.startGame();
            ActiveGames.add(G);
            //lobby.clearPlayers();  // Should I remove players from the lobby because they're now in an active game?
          }
        }
    } else if(message.indexOf("ChatMessage") > 0) { //for chat feature
        // getting sender and message content from JSON message
        try {
          JsonObject json = JsonParser.parseString(message).getAsJsonObject();
          String sentBy = json.get("sentBy").getAsString();                     //getting player name
          String chatContent = json.get("chatContent").getAsString();           //getting message they sent
        
          // Find out which player sent message
          Player sender = null;
          for (Player p : players) {
            if (p.getNick().equals(sentBy)) {
              sender = p;
              break;
            }
          }

          // Broadcast the message to all players in game
          if (sender != null) {
            Messaging.Broadcast(sender, players, chatContent);
          }
        } 
        catch (JsonParseException e) {
          System.err.println("Error with parsing the JSON message: " + e.getMessage());
        }
      }
    
    /* //already provided by prof in "aaaaa"
      else if (message.indexOf("WordSelection") = 0) { //when player is playing game/highlighting words
        for (GameSession g : ActiveGames) {
          if (g.GameId == U.GameId) {
            g.Update(U);
            break;
          }
        }
      
      }
      */
    // After the incoming messag is processed, we need to send updated
    // state information to all of the players. With what I know right now,
    // this consists of the "Lobby" and the "GameSession"

    if (GS != null) {
      String jsonString;
      jsonString = gson.toJson(GS);
      System.out
          .println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " +
              "*" + " " + escape(jsonString));
      broadcast(jsonString);
    }

    if (lobby != null) {
      String jsonString;
      jsonString = gson.toJson(lobby);
      System.out
          .println("> " + Duration.between(startTime, Instant.now()).toMillis() + " " +
              "*" + " " + escape(jsonString));
      broadcast(jsonString);
    }

  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
    System.out.println(conn + ": " + message);
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
    if (conn != null) {
      // some errors like port binding failed may not be assignable to a specific
      // websocket
    }
  }

  @Override
  public void onStart() {
    setConnectionLostTimeout(0);
    // stats = new Statistics();
    startTime = Instant.now();
  }

  private String escape(String S) {
    // turns " into \"
    String retval = new String();
    // this routine is very slow.
    // but it is not called very often
    for (int i = 0; i < S.length(); i++) {
      Character ch = S.charAt(i);
      if (ch == '\"') {
        retval = retval + '\\';
      }
      retval = retval + ch;
    }
    return retval;
  }

  public static void main(String[] args) {

    String HttpPort = System.getenv("HTTP_PORT");
    int port = 9014;
    if (HttpPort != null) {
      port = Integer.valueOf(HttpPort);
    }

    // Set up the http server

    HttpServer H = new HttpServer(port, "./html");
    H.start();
    System.out.println("http Server started on port: " + port);

    // create and start the websocket server

    port = 9114;
    String WSPort = System.getenv("WEBSOCKET_PORT");
    if (WSPort != null) {
      port = Integer.valueOf(WSPort);
    }

    App A = new App(port);
    A.setReuseAddr(true);
    A.start();
    System.out.println("websocket Server started on port: " + port);

  }
}
