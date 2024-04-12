// Name: Tien Dat Do
// Date: March 21, 2024
// TheWordSearchGame Player class

package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String nick;
    private int score;
    private List<String> wordsFound;
    public ArrayList<Message> messages = new ArrayList<>();

    public Player(String nick, int score, List<String> wordsFound, ArrayList<Message> messages) {
        this.nick = nick;
        this.score = score;
        this.wordsFound = wordsFound;
        this.messages = messages;
    }

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

    public List<String> getWordsFound() {
        return wordsFound;
    }

    public void setWordsFound(List<String> wordsFound) {
        this.wordsFound = wordsFound;
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

    public class Position {
       
    }

    // Methods
    public void selectWordStart(Position position) {
        
    }

    public void selectWordEnd(Position position) {
        
    }
    
}
