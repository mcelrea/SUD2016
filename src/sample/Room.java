package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by mcelrea on 9/6/2016.
 */
public class Room {
    int myRoom[][];
    ArrayList<Item> items = new ArrayList<Item>();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    Image wallImageGrey;
    Store store;

    public Room(String path) {
        myRoom = new int[20][20];
        File file = new File(path);
        File imageFile = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\images\\wall.png");
        try {
            wallImageGrey = new Image(new FileInputStream(imageFile));
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String nextLine;
            int count = 0;
            int row = 0;
            int col = 0;
            while((nextLine = reader.readLine()) != null) {
                if(count != 0) {
                    for (int i = 0; i < nextLine.length(); i++) {
                        if(nextLine.substring(i,i+1).equals("1")) {
                            myRoom[row][col] = 1;
                        }
                        else if(nextLine.substring(i,i+1).equals(" ")) {
                            myRoom[row][col] = 0;
                        }
                        else if(nextLine.substring(i,i+1).equals("E")) {
                            myRoom[row][col] = 100;
                        }
                        else if(nextLine.substring(i,i+1).equals("r")) {
                            Item ringOfStrength = new Item("Ring of Strength",
                                    "A small gold ring glitters on the floor beneath you.",
                                    "C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\images\\ring.png");
                            ringOfStrength.setRow(row);
                            ringOfStrength.setCol(col);
                            ringOfStrength.setSymbol("O");
                            ringOfStrength.setAbility(new Ability("Swipe",
                                                                  10,
                                                                  "1d6",
                                                                  Ability.DEXTERITY,
                                                                  0,
                                                                  12,
                                                                  0));
                            //ringOfStrength.getAbility().setMinDamage(1);
                            //ringOfStrength.getAbility().setMaxDamage(3);
                            addItem(ringOfStrength);
                        }
                        else if(nextLine.substring(i,i+1).equals("S")) {
                            Skeleton skel = new Skeleton("Skeleton",0,-4,-1,-1,0);
                            skel.setRow(row);
                            skel.setCol(col);
                            enemies.add(skel);
                        }
                        else if(nextLine.substring(i,i+1).equals("Z")) {
                            Zombie zombie = new Zombie("Zombie",0,-4,-1,-1,0);
                            zombie.setRow(row);
                            zombie.setCol(col);
                            enemies.add(zombie);
                        }
                        col++;
                    }
                    row++;
                    col=0;
                }
                count++;
            }
        } catch(Exception e) {
            System.out.println("COULD NOT LOAD FILE");
            e.printStackTrace();
        }
    }

    public void debugRoom() {
        for (int i=0; i < myRoom.length; i++) {
            for(int j=0; j < myRoom[i].length; j++) {
                if(myRoom[i][j] == 0)
                    System.out.print(" ");
                else
                    System.out.print(myRoom[i][j]);
            }
            System.out.println();
        }
    }

    public void draw(Player player, GraphicsContext gc) {

        //go through the entire room array (table)
        for(int row=0; row < myRoom.length; row++) {
            for(int col=0; col < myRoom[row].length; col++) {
                if(myRoom[row][col] == 1) {
                    gc.setFill(Color.BLACK);
                    //gc.fillText("1",Main.OFFSET+col*20,Main.OFFSET+row*20);
                    gc.drawImage(wallImageGrey,Main.OFFSET+col*20,Main.OFFSET+row*20-20);
                }
            }
        }

        //go through all the items in the room and draw them
        gc.setFill(Color.BLACK);
        for(int i=0; i < items.size(); i++) {
            items.get(i).draw(gc);
            //if the player is on top of the item
            if(items.get(i).getRow() == player.getRow() &&
                    items.get(i).getCol() == player.getCol()) {
                gc.setFill(Color.BLACK);
                gc.fillText(items.get(i).getDescription(),10,460);
                gc.fillText("Press 'E' to pick up", 10, 490);
            }
        }

        //go through all the enemies
        for(int i=0; i < enemies.size(); i++) {
            enemies.get(i).draw(gc);
        }

        //draw the store
        if(store != null) {
            gc.setFill(Color.DARKOLIVEGREEN);
            gc.fillText("$",Main.OFFSET+store.col*20,Main.OFFSET+store.row*20);
        }
    }

    public void pickUpItem(Player player) {
        Item item;
        for(int i=0; i < items.size(); i++) {
            //if the player (row,col) matches the items(row, col)
            if(player.getRow() == items.get(i).getRow() &&
                    player.getCol() == items.get(i).getCol()) {
                item = items.get(i); //grab  the item
                items.remove(i); //remove the item from the room
                i--;
                player.setXp(player.getXp() + item.getXp());
                player.setStrength(player.getStrength() + item.getStrength());
                player.setWisdom(player.getWisdom() + item.getWisdom());
                if(item.getAbility() != null) {
                    player.addAbility(item.getAbility());
                }
            }
        }
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public int getCell(int row, int col) {
        return myRoom[row][col];
    }

    public Store getStore(Player p) {
        if(store != null) {
            if(p.getRow() == store.row && p.getCol() == store.col) {
                return store; //return the store, the player is standing on it
            }
        }
        return null;//there is no store
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void enemiesAct(World world, Player player) {
        for(int i=0; i < enemies.size(); i++) {
            enemies.get(i).act(world,player);
        }
    }

    public Enemy getEnemyCollision(Player player) {
        for(int i=0; i < enemies.size(); i++) {
            if(player.getRow() == enemies.get(i).getRow() &&
                    player.getCol() == enemies.get(i).getCol()) {
                return enemies.get(i);
            }
        }
        return null; //player is not on any enemy
    }

    public Enemy getEnemyCollisionBySize(Player player) {
        int playerX = Main.OFFSET+player.getCol()*20;
        int playerY = Main.OFFSET+player.getRow()*20;

        for(int i=0; i < enemies.size(); i++) {
            int enemyX = Main.OFFSET+enemies.get(i).getCol()*20;
            int enemyY = Main.OFFSET+enemies.get(i).getRow()*20;
            if(playerX >= enemyX && playerX < enemyX + enemies.get(i).width &&
                    playerY >= enemyY && playerY < enemyY + enemies.get(i).height) {
                return enemies.get(i);
            }
        }

        return null;
    }
}
