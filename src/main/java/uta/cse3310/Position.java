package uta.cse3310;

public class Position {
    private int x;
    private int y;

    // Constructor to set the initial position
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getter for the x-coordinate
    public int getX() {
        return x;
    }

    // Setter for the x-coordinate
    public void setX(int x) {
        this.x = x;
    }

    // Getter for the y-coordinate
    public int getY() {
        return y;
    }

    // Setter for the y-coordinate
    public void setY(int y) {
        this.y = y;
    }

    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
