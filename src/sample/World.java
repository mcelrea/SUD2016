package sample;

import javafx.scene.canvas.GraphicsContext;

public class World {
    Room rooms[][] = new Room[15][15];

    public World() {
        //create the world
        rooms[9][10] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room9-10.txt");
        rooms[10][10] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room10-10.txt");
        rooms[9][11] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room9-11.txt");
        rooms[8][11] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room8-11.txt");
        rooms[7][11] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room7-11.txt");


        rooms[10][10].store = new Store();
        rooms[10][10].store.row = 7;
        rooms[10][10].store.col = 16;
        rooms[10][10].store.addAbility(new Ability("Dagger",
                10,
                "1d6",
                Ability.DEXTERITY,
                0,
                12,
                0));
        rooms[10][10].store.addAbility(new Ability("Long Sword",
                15,
                "1d8",
                Ability.STRENGTH,
                12,
                0,
                0));
        rooms[10][10].store.addAbility(new Ability("Acid Splash",
                10,
                "1d6",
                Ability.WISDOM,
                0,
                0,
                12));
    }

    public void drawRoom(int row, int col, Player player, GraphicsContext gc) {
        rooms[row][col].draw(player, gc);
    }

    public Room getRoom(int row, int col) {
        return rooms[row][col];
    }
}
