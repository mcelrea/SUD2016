package sample;

/**
 * Created by mcelrea on 9/2/2016.
 */
public class Player {
    private String name;
    private int row;
    private int col;

    public Player(String n) {
        name = n;
        row = 20;
        col = 20;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void moveUp() {
        row--;
    }

    public void moveDown() {
        row++;
    }

    public void moveLeft() {
        col--;
    }

    public void moveRight() {
        col++;
    }
}
