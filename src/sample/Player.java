package sample;

/**
 * Created by mcelrea on 9/2/2016.
 */
public class Player {
    private String name;
    private int row;//row within a room
    private int col;//col within a room
    private int worldRow; //row within the world
    private int worldCol; //col within the world

    public Player(String n) {
        name = n;
        row = 10;
        col = 10;
        worldRow = 10;
        worldCol = 10;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void moveUp(Room room) {
        //check to make sure I stay in bounds
        if(row == 0) {
            //he's leaving the current room
            if(room.getCell(row,col) == 100) {
                worldRow--; //change the room
                row = 18; //place player at bottom of next Room
            }
            return; //exit, do not move
        }
        //check to make sure there is no wall above me
        else if(room.getCell(row-1,col) == 1) {
            return; //exit, do not move
        }
        //else allow player to move
        else {
            row--;
        }
    }

    public void moveDown(Room room) {
        //check to make sure I stay in bounds
        if(row == 19) {
            return; //exit, do not move
        }
        //check to make sure there is no wall below me
        else if(room.getCell(row+1,col) == 1) {
            return; //exit, do not move
        }
        //else allow player to move
        else {
            row++;
        }
    }

    public void moveLeft(Room room) {
        //check to make sure I stay in bounds
        if(col == 0) {
            return; //exit, do not move
        }
        //check to make sure there is no wall left of me
        else if(room.getCell(row,col-1) == 1) {
            return; //exit, do not move
        }
        //else allow player to move
        else {
            col--;
        }
    }

    public void moveRight(Room room) {
        //check to make sure I stay in bounds
        if(col == 19) {
            return; //exit, do not move
        }
        //check to make sure there is no wall right of me
        else if(room.getCell(row,col+1) == 1) {
            return; //exit, do not move
        }
        //else allow player to move
        else {
            col++;
        }
    }

    public int getWorldRow() {
        return worldRow;
    }

    public void setWorldRow(int worldRow) {
        this.worldRow = worldRow;
    }

    public int getWorldCol() {
        return worldCol;
    }

    public void setWorldCol(int worldCol) {
        this.worldCol = worldCol;
    }
}
