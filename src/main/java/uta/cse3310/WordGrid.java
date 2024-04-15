//Last modified: 4/14/24 by Anthony Timberman
package uta.cse3310;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.random.*;

public class WordGrid {
    char[][] grid;
    ArrayList<String> wordsInGrid = new ArrayList<String>();
    Map<String, ArrayList<Position>> wordPositions = new HashMap<String, ArrayList<Position>>();



    public WordGrid(int size, ArrayList<String> words) {

        // Initialize the grid and place words

        grid = new char[size][size];
        float density = (float) 0.00;
        float add;
        Random rand = new Random();
        int i;
        String word;

        while(density <= .67){

            i = rand.nextInt(words.size());
            word = words.get(i);

            //check if word too big
            if (word.length() > size){
                continue;
            }
            add = ((float)word.length())/(float)((size*size));
            //check if there are no words in grid
            if(this.wordsInGrid.isEmpty()){
                wordsInGrid.add(word);
                density += add;
                continue;
            }
            //check if the word does not already exist in the grid
            else if (!(wordsInGrid.contains(word))) {
                wordsInGrid.add(word);
                density += add;
            }            
        }
    }

    public void generateGrid() {
        // Implementation
        int i, j;
        Random rand = new Random();
        char fill;

        for(i = 0; i < grid.length; i++){
            for(j = 0; j < grid.length; j++){

                if(grid[i][j] == '\0'){
                    //if grid is empty generate random character and place it in there
                    fill = (char)(rand.nextInt(26) + 97);
                    grid[i][j] = fill;
                }
            }
        }
        System.out.print(grid);
    }



    public void placeWords() {

        // Implementation
        int size = grid.length;
        Random rand = new Random();
        int quadrant;
        int orientation;
        int row = 0;
        int col = 0;
        Boolean fitTest = false;
        Boolean overlapTest = true;
        int i;
        Position start, end;

        for(String x: wordsInGrid){
            // determine if word will be placed vert/horiz/diag (0/1/2 respectively)
            orientation = rand.nextInt(3);
            quadrant = rand.nextInt(4);


            //words will be equaly distributed among quadrants of map
            while (fitTest == false || overlapTest == false){
            //top left
                orientation = rand.nextInt(3);
                quadrant = rand.nextInt(4);
                if(quadrant == 0){
                //while the word does not pass tests


                    //initialize tests
                    fitTest = false;
                    overlapTest = true;

                    //randomize word's starting position
                    row = rand.nextInt(size/2);
                    col = rand.nextInt(size/2);

                    if(grid[row][col] != '\0'){
                        overlapTest = false;
                        continue;
                    }

                    //check the orientation of the word

                    //vertical
                    if (orientation == 0){
                        if(size >= (row + x.length())){
                            fitTest = true;
                        //if word fits, check if it overlaps
                            for(i = 0; i < x.length(); i++){
                                if (grid[row+i][col] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }
                    //horizontal
                    else if (orientation == 1){
                        if(size >= (col + x.length())){
                            fitTest = true;
                            for(i = 0; i < x.length(); i++){
                                if (grid[row][col+i] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }    
                    //diagonal
                    else if (orientation == 2){
                        if((size >= (row + x.length())) && (size >= (col + x.length()))){
                            fitTest = true;
                            for(i = 0; i < x.length(); i++){
                                if (grid[row+i][col+i] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }
                }

            //top right
                else if(quadrant == 1){
                //while the word does not pass tests
                

                    //initialize tests
                    fitTest = false;
                    overlapTest = true;

                    //randomize word's starting position
                    row = rand.nextInt(size/2);
                    col = rand.nextInt(size/2) + (size/2);

                    if(grid[row][col] != '\0'){
                        overlapTest = false;
                        continue;
                    }

                    //check the orientation of the word

                    //vertical
                    if (orientation == 0){
                        if(size >= (row + x.length())){
                            fitTest = true;
                        //if word fits, check if it overlaps
                            for(i = 0; i < x.length(); i++){
                                if (grid[row+i][col] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }
                    //horizontal
                    else if (orientation == 1){
                        if(size >= (col + x.length())){
                            fitTest = true;
                            for(i = 0; i < x.length(); i++){
                                if (grid[row][col+i] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }
                    //diagonal
                    else if (orientation == 2){
                        if((size >= (row + x.length())) && (size >= (col + x.length()))){
                            fitTest = true;
                            for(i = 0; i < x.length(); i++){
                                if (grid[row+i][col+i] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }

                        }
                    }
                }
            
            //bottom left
                else if(quadrant == 2){
                //while the word does not pass tests


                    //initialize tests
                    fitTest = false;
                    overlapTest = true;

                    //randomize word's starting position
                    row = rand.nextInt(size/2) + (size/2);
                    col = rand.nextInt(size/2);

                    if(grid[row][col] != '\0'){
                        overlapTest = false;
                        continue;
                    }

                    //check the orientation of the word

                    //vertical
                    if (orientation == 0){
                        if(size >= (row + x.length())){
                            fitTest = true;                  
                        //if word fits, check if it overlaps
                            for(i = 0; i < x.length(); i++){
                                if (grid[row+i][col] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }
                    //horizontal
                    else if (orientation == 1){
                        if(size >= (col + x.length())){
                            fitTest = true;
                            for(i = 0; i < x.length(); i++){
                                if (grid[row][col+i] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }
                    //diagonal
                    else if (orientation == 2){
                        if((size >= (row + x.length())) && (size >= (col + x.length()))){
                            fitTest = true;
                            for(i = 0; i < x.length(); i++){
                                if (grid[row+i][col+i] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }


                }
                                //once the word passes the tests, add it to the grid, and add it's position to the map
            //bottom right
                else{
                //while the word does not pass tests

                    //initialize tests
                    fitTest = false;
                    overlapTest = true;

                    //randomize word's starting position
                    row = rand.nextInt(size/2) + (size/2);
                    col = rand.nextInt(size/2) + (size/2);

                    //check the orientation of the word
                    if(grid[row][col] != '\0'){
                        overlapTest = false;
                        continue;
                    }


                    //vertical
                    if (orientation == 0){
                        if(size >= (row + x.length())){
                            fitTest = true;
                        //if word fits, check if it overlaps
                            for(i = 0; i < x.length(); i++){
                                if (grid[row+i][col] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }
                    //horizontal
                    else if (orientation == 1){
                        if(size >= (col + x.length())){
                            fitTest = true;
                            for(i = 0; i < x.length(); i++){
                                if (grid[row][col+i] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }
                    //diagonal
                    else if (orientation == 2){
                        if((size >= (row + x.length())) && (size >= (col + x.length()))){
                            fitTest = true;
                            for(i = 0; i < x.length(); i++){
                                if (grid[row+i][col+i] != '\0') {
                                    overlapTest = false;
                                    break;
                                }
                            }
                        }
                    }
                }
             //once the word passes the tests, add it to the grid, and add it's position to the map
            }
            //vertical
            if(orientation == 0){
                for(i = 0; i < x.length(); i++){
                    grid[row+i][col] = x.charAt(i);
                }
                start = new Position(row, col);
                end = new Position(row+x.length(), col);

                ArrayList<Position> startend = new ArrayList<Position>();
                startend.add(start);
                startend.add(end);
                wordPositions.put(x, startend);
            }
            //horizontal
            else if(orientation == 1){
                for(i = 0; i < x.length(); i++){
                    grid[row][col+i] = x.charAt(i);
                }
                start = new Position(row, col);
                end = new Position(row, col+x.length());
                ArrayList<Position> startend = new ArrayList<Position>();
                startend.add(start);
                startend.add(end);
                wordPositions.put(x, startend);
            }
            //diagonal
            else if(orientation == 2){
                    for(i = 0; i < x.length(); i++){
                        grid[row+i][col+i] = x.charAt(i);
                    }
                    start = new Position(row, col);
                    end = new Position((row+x.length()-1), (col+x.length()-1));
                    ArrayList<Position> startend = new ArrayList<Position>();
                    startend.add(start);
                    startend.add(end);
                    
                    wordPositions.put(x, startend);
                }
            
            fitTest = false;
        }
    }

    public boolean checkWordSelection(Position startPos, Position endPos) {
        // Implementation
        ArrayList<Position> checkPos = new ArrayList<Position>();
        checkPos.add(startPos);
        checkPos.add(endPos);

        if(wordPositions.containsValue(checkPos)){
            return true;
        }
        return false;

    }

    public String getWord(Position startPos, Position endPos) {

        String word = "Not Found";
        ArrayList<Position> checkPos = new ArrayList<Position>();
        checkPos.add(startPos);
        checkPos.add(endPos);

        for(Entry<String, ArrayList<Position>> entry : wordPositions.entrySet()){
            if(entry.getValue()==checkPos){
                word = entry.getKey();
                break;
            }

        }

        return word;
    }

    public void highlightWord(String word) {
        // Implementation
        ArrayList<Position> startend = wordPositions.get(word);
        Position start = startend.get(0);
        Position end = startend.get(1);
        int i,j;

        for(i=start.getX(); i <= end.getX(); i++){
            for(j=start.getY(); j <= end.getY(); j++){
                //highlight the character
                grid[i][j] = '*';
            }
        }
    }

    public boolean isWordPlaced(String word) {

        return wordPositions.containsKey(word);
    }

    public char getLetterAt(int row, int col){
        char x = grid[row][col];
        
        return x;
    }
}
