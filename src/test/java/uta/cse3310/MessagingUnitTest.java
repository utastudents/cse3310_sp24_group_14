package uta.cse3310; //uta.cse3310

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import uta.cse3310.GameSession;
import uta.cse3310.Player;

import java.util.ArrayList;

public class MessagingUnitTest extends TestCase{
    public MessagingUnitTest(String testName) {
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(MessagingUnitTest.class);
    }

    public void testMessaging(){
        // testing messaging functions to see if data is properly going from point A to point B

        ArrayList<Player> players = new ArrayList<Player>();

        ArrayList<Message> p1 = new ArrayList<>();
        ArrayList<Message> p2 = new ArrayList<>();

        // Player player1 = new Player("John Doe", 0, null, null, p1);
        // Player player2 = new Player("Jane Doe", 0, null, null, p2);
        Player player1 = new Player("John Doe");
        Player player2 = new Player("Jane Doe");

        players.add(player1);
        players.add(player2);

        // testing messaging method Send()
        Messaging.Send(player1, player2, "To Jane, from John");
        Messaging.Send(player2, player1, "To John, from Jane");

        // testing messaging method Broadcast()
        Messaging.Broadcast(player1, players, "Hello, I'm John!");
        Messaging.Broadcast(player2, players, "Hello, I'm Jane!");

        // printing for demonstration
        System.out.println("----------------TESTING MESSAGING----------------");
        System.out.println("John Doe's messages: ");
        for(int i = 0; i < player1.messages.size(); i++){
            System.out.println(player1.messages.get(i));
        }
        System.out.println("Jane Doe's messages: ");
        for(int i = 0; i < player2.messages.size(); i++){
            System.out.println(player2.messages.get(i));
        }
        System.out.println("----------------TESTING COMPLETE-----------------");

        // messaging testing ends here
    }
}