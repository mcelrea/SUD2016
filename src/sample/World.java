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
        rooms[9][9] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room9-9.txt");
        rooms[10][9] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room10-9.txt");
        rooms[10][8] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\RoomFiles\\room10-8.txt");

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
        rooms[10][10].store.addAbility(new Ability("Scimitar",
                14,
                "2d4",
                Ability.DEXTERITY,
                0,
                17,
                0));
        rooms[10][10].store.addAbility(new Ability("Fireball",
                20,
                "2d5",
                Ability.WISDOM,
                0,
                0,
                18));
        rooms[10][10].store.addAbility(new Ability("Greatsword",
                20,
                "2d5",
                Ability.STRENGTH,
                20,
                0,
                0));



        rooms[10][8].store = new Store();
        rooms[10][8].store.row = 3;
        rooms[10][8].store.col = 5;
        rooms[10][8].store.addAbility(new Ability("Ice Bolt",
                35,
                "2d6",
                Ability.WISDOM,
                0,
                12,
                25));
        rooms[10][8].store.addAbility(new Ability("Edged Rapier",
                43,
                "3d4",
                Ability.DEXTERITY,
                12,
                25,
                0));
        rooms[10][8].store.addAbility(new Ability("Battle Axe",
                40,
                "3d5",
                Ability.STRENGTH,
                25,
                14,
                0));
    }

    public void drawRoom(int row, int col, Player player, GraphicsContext gc) {
        rooms[row][col].draw(player, gc);
    }

    public Room getRoom(int row, int col) {
        return rooms[row][col];
    }
}
