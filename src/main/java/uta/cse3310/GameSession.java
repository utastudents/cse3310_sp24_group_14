package uta.cse3310;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.awt.Color;

public class GameSession {

    public ArrayList<Player> players;
    public String[] Msg;
    public int GameId;
    public WordGrid Grid;
    public ArrayList<String> Wordbank;
    public int playercount = 0;
    public Position[][] buttons = new Position[20][20];
    boolean Start = false;

    GameSession() {
        Msg = new String[2];
        Msg[0] = "Ready Up to Begin Game!";
        Msg[1] = "";
    }


    public void startGame() {
        String word;
        ArrayList<String> allwords = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("resources/words.txt"));
            word = reader.readLine();
            while(word != null){
                if(word.length() > 2 && word.length() < 7)
                allwords.add(word);
                word = reader.readLine();
            }
    
            reader.close();
        } catch (IOException e) {

            e.printStackTrace();
        }


        Grid = new WordGrid(35, allwords);
        Wordbank = new ArrayList<>(Grid.wordsInGrid);
        Grid.placeWords();
        Grid.generateGrid();
        int i, j;

        for(i = 0; i < 35;i++){
            for(j= 0; j < 35; j++){
                buttons[i][j] = new Position(i, j);
            }
        }

        Start = true;
    }

    // This function returns an index for each player
    // It does not depend on the representation of Enums
    public int PlayerToIdx(Player P) {
        int i;
        for (i=0; i < 4; i++)
            if (P == players.get(i)) {
                return i;
            }
        return -1;
    }

    public void Update(UserEvent U) {

        //check if event game id matches this games id
        if(U.GameId == GameId){
            //check if positions/buttons selected match a word
            if (Grid.checkWordSelection(U.Button1, U.Button2)){

                //if word is in the grid check it's orientation
                //Highlight all positions that apply to the word in buttons
                int i, j;
                if(U.Button1.getX() == U.Button2.getX()){
                    j = U.Button1.getX();
                    for(i = U.Button1.getY(); i < U.Button2.getY(); i++){
                        buttons[j][i].highlight(U.PlayerIdx.getMarkColor());
                    }
                }
                else if(U.Button1.getY() == U.Button2.getY()){
                    j = U.Button1.getY();
                    for(i = U.Button1.getX(); i < U.Button2.getX(); i++){
                        (buttons[i][j]).highlight(U.PlayerIdx.getMarkColor());
                    }
                }
                else{
                    j = U.Button1.getY();
                    for(i = U.Button1.getX(); i < U.Button2.getX(); i++){
                        buttons[i][j].highlight(U.PlayerIdx.getMarkColor());
                        j++;
                    }
                }
                //award the word to the played
                //remove the word from the grid so no one else can use it
                
                U.PlayerIdx.wordsFound.add(Grid.getWord(U.Button1, U.Button2));
                Grid.wordsInGrid.remove(Grid.getWord(U.Button1, U.Button2));
                
            }
                

             }
    }
    public void enterLobby(Player player) {
        // Add player to the lobby
        players.add(player);
    }

    public void exitLobby(Player player) {
        // Remove player from the lobby
        players.remove(player);
    }

    public void joinGame(Player player) {
        // Add player to the game session
        players.add(player);
    }

    public void leaveGame(Player player) {
        // Remove player from the game session
        players.remove(player);
    }
    public boolean isFull(){
        if(playercount > 4){
            return true;
        }
        return false;
    }
    public void sendMessage(Player sender, String message) {
        //Send a message from the sender to all players
        Messaging broadcast = new Messaging();
        broadcast.Broadcast(sender, players, message);
        
    }
     
}
// In windows, shift-alt-F formats the source code
// In linux, it is ctrl-shift-I
