package uta.cse3310;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Random;

public class Player {
    private String nick;
    private int score;
    private List<String> wordsFound;
	private Color markColor;                     //player's unique highlighting color
	private ArrayList<Position> markCells;  //current word player is highlighting
    public ArrayList<Message> messages = new ArrayList<>();

    public Player(String nick, int score, List<String> wordsFound, Color highlightColor, ArrayList<Message> messages) {
        this.nick = nick;
        this.score = score;
        this.wordsFound = wordsFound;
		this.markColor = randomColor(); //color to highlight in for player
		this.markCells = new ArrayList<>();  //each time a player will highlight something new
        this.messages = messages;
    }
    
	//original getters and setters from design
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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


    // Methods
    public void selectWordStart(Position position) {
		int row = position.getX();
        int col = position.getY();
		markCells.clear();         //unhighlights remaining words
		markCells.add(new Position(row,col));
        
    }
	//added new method to highlight words inbetween first and last letter
	public void continueSelectWord(Position position){
		int row = position.getX();
        int col = position.getY();
		markCells.add(new Position(row,col));
	}

    public void selectWordEnd(Position position) {
        int row = position.getX();
        int col = position.getY();
		markCells.add(new Position(row,col));
		String word = getMarkedWord(); //creates a word from all selected cells
		if (wordsFound.contains(word) && !prevFound(word)){
			score += 10;
			wordsFound.add(word);
		}
		else {
			markCells.clear(); //erases highlight from word if it's not one from game
		}
    }
	
	private String getMarkedWord(){
		StringBuilder makeWord = new StringBuilder();
		for (Position position : markCells){
			int row = position.getX();
			int col = position.getY();
		//	makeWord.append(WordGrid.getLetterAt(row,col));
		}
		return makeWord.toString();
	}
	
	private boolean prevFound(String word){ //checks if word's already been found
		return wordsFound.contains(word);
	}
    
	private Color randomColor(){
		int yellow = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);
		int red = (int) (Math.random() * 256);
		
		return new Color(yellow, blue, red);
	}
}
