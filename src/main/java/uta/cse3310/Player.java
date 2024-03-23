// Name: Tien Dat Do
// Date: March 21, 2024
// TheWordSearchGame Player class

// package Group-14;
package uta.cse3310;


import java.util.List;

public class Player {
    private String nick;
    private int score;
    private List<String> wordsFound;

    public Player(String nick, int score, List<String> wordsFound) {
        this.nick = nick;
        this.score = score;
        this.wordsFound = wordsFound;
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

    public class Position {
       
    }

    // Methods
    public void selectWordStart(Position position) {
        
    }

    public void selectWordEnd(Position position) {
        
    }
}


