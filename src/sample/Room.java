package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by mcelrea on 9/6/2016.
 */
public class Room {
    int myRoom[][];
    ArrayList<Item> items = new ArrayList<Item>();

    public Room(String path) {
        myRoom = new int[20][20];
        File file = new File(path);
        try {
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
                                    "A small gold ring glitters on the floor beneath you.");
                            ringOfStrength.setStrength(1);
                            ringOfStrength.setRow(row);
                            ringOfStrength.setCol(col);
                            ringOfStrength.setSymbol("O");
                            addItem(ringOfStrength);
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
                    gc.fillText("1",Main.OFFSET+col*20,Main.OFFSET+row*20);
                }
            }
        }

        //go through all the items in the room and draw them
        gc.setFill(Color.BLACK);
        for(int i=0; i < items.size(); i++) {
            gc.fillText(items.get(i).getSymbol(),
                        Main.OFFSET + items.get(i).getCol()*20,
                        Main.OFFSET + items.get(i).getRow()*20);
            //if the player is on top of the item
            if(items.get(i).getRow() == player.getRow() &&
                    items.get(i).getCol() == player.getCol()) {
                gc.setFill(Color.BLACK);
                gc.fillText(items.get(i).getDescription(),10,460);
                gc.fillText("Press 'E' to pick up", 10, 490);
            }
        }
    }

    public void pickUpItem(Player player) {
        Item item;
        for(int i=0; i < items.size(); i++) {

        }
    }

    public int getCell(int row, int col) {
        return myRoom[row][col];
    }


    public void addItem(Item item) {
        items.add(item);
    }
}
