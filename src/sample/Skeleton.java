package sample;

import javafx.scene.paint.Color;

public class Skeleton extends Enemy{

    public Skeleton(int hp, String name) {
        super(hp, name, "S");
        actRate = 1000; //1 second
        lastAct = System.currentTimeMillis();
        color = Color.BLUEVIOLET;
    }

    @Override
    public void act(World world, Player player) {
        if(lastAct + actRate <= System.currentTimeMillis()) {
            Room currentRoom = world.getRoom(player.getWorldRow(),player.getWorldCol());
            while(true) {
                //generate a random number between 1-4
                int choice = (int) (1 + Math.random() * 4);
                if (choice == 1 && currentRoom.getCell(getRow(),getCol()-1) != 1) {
                    setCol(getCol() - 1);//left
                    break;
                } else if (choice == 2 && currentRoom.getCell(getRow(),getCol()+1) != 1) {
                    setCol(getCol() + 1);//right
                    break;
                } else if (choice == 3 && currentRoom.getCell(getRow()+1,getCol()) != 1) {
                    setRow(getRow() + 1);//down
                    break;
                } else if(choice == 4 && currentRoom.getCell(getRow()-1,getCol()) != 1){
                    setRow(getRow() - 1);//up
                    break;
                }
            }
            lastAct = System.currentTimeMillis();
        }
    }

    @Override
    public void attack(Player player) {

    }
}
