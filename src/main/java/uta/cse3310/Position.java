package uta.cse3310;
import java.awt.Color;

public class Position {
    private int row;
    private int col;
    public Color color;

    // Constructor to set the initial position
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
        //makes color transparent
        this.color = new Color(0, 0, 0, 0);
    }

    // Getter for the x-coordinate
    public int getX() {
        return row;
    }

    // Setter for the x-coordinate
    public void setX(int x) {
        this.row = x;
    }

    // Getter for the y-coordinate
    public int getY() {
        return col;
    }

    // Setter for the y-coordinate
    public void setY(int y) {
        this.col = y;
    }

    public void highlight(Color c){
        color = c;
    }

    
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

}
