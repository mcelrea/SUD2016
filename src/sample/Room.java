package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by mcelrea on 9/6/2016.
 */
public class Room {
    int myRoom[][];

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
                        if(nextLine.substring(i,i+1).equals(" ")) {
                            myRoom[row][col] = 0;
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
}
