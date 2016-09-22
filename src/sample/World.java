package sample;

import javafx.scene.canvas.GraphicsContext;

public class World {
    Room rooms[][] = new Room[15][15];

    public World() {
        //create the world
        rooms[9][10] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room9-10.txt");
        rooms[10][10] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room10-10.txt");
        rooms[9][11] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room9-11.txt");
    }

    public void drawRoom(int row, int col, Player player, GraphicsContext gc) {
        rooms[row][col].draw(player, gc);
    }

    public Room getRoom(int row, int col) {
        return rooms[row][col];
    }
}
