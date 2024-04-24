package uta.cse3310;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Random;

public class Player {
    public String nick;
    public int score;
    public List<String> wordsFound;
    public Color markColor;                //player's unique highlighting color; color attribute in another location
    //private ArrayList<Position> markCells;  //current word player is highlighting, will be implemented in html; can't do in java
    public ArrayList<Message> messages;


    public Player(String nick) {
		Random rand = new Random();   //what's this for? @diganta

        this.nick = nick;
        this.score = score; //update score in wordgrid.java
		this.markColor = new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
		this.wordsFound = new ArrayList<>();//add words to array in wordgrid.java
		this.messages = new ArrayList<>();
    }
    
	//original getters and setters from design
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Message> getMessages(){
        return messages;
    }

    public void setMessages(ArrayList<Message> messages){
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "{" +
                "nick = " + getNick() +
                
                '}';
    }
	 
	//Added by Tien
    	public void displayScore(int score) {
        System.out.println("Score: " + score);
    	}
	
	//new getters & setters
	public Color getMarkColor(){
		return markColor;
	}
	
	public void setMarkColor(Color markColor){
		this.markColor = markColor;
	}

/*  
    Methods moved to word grid
*/
}
