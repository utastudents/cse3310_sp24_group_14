package uta.cse3310; //uta.cse3310

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import uta.cse3310.GameSession;
import uta.cse3310.Player;

import java.util.ArrayList;

public class GameUnitTest extends TestCase{
    public GameUnitTest(String testName) {
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(GameUnitTest.class);
    }
    public void testGame(){
        // testing game functions
        Player player1 = new Player("John Doe");
        Player player2 = new Player("Jane Doe");



        GameSession g1 = new GameSession();
        //testing method  StartGame
        g1.startGame();




        // printing for demonstration
        System.out.println("----------------TESTING MESSAGING----------------");

        System.out.println("Wordbank: ");
        for(int i = 0; i < g1.Wordbank.size(); i++){
            System.out.println(g1.Wordbank.get(i));
        }
        System.out.println("Players: ");
        for(int i = 0; i < g1.playercount; i++){
            System.out.println(g1.players.get(i));
        }
        System.out.println("----------------TESTING COMPLETE-----------------");
        // messaging testing ends here
    }
}

