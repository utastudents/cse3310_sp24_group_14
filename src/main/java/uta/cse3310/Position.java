//Last modified: 4/14/24 by Anthony Timberman
package uta.cse3310;

public class Position {
    private int row;
    private int col;

    // Constructor to set the initial position
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Getter for the x-coordinate
    public int getX() {
        return row;
    }

    // Setter for the x-coordinate
    public void setX(int x) {
        this.row = row;
    }

    // Getter for the y-coordinate
    public int getY() {
        return col;
    }

    // Setter for the y-coordinate
    public void setY(int y) {
        this.col = col;
    }

    
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

}
