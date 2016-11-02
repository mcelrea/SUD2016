package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Skeleton extends Enemy{

    public Skeleton(String name, int hpModifier, int strengthModifier,
                    int dexerityModifier, int wisdomModifier, int attackModifier) {
        actRate = 1000; //1 second
        lastAct = System.currentTimeMillis();
        color = Color.BLUEVIOLET;
        xp = 3;
        File file = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\images\\skeleton.png");
        try {
            forwardImage = new Image(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.hpModifier = hpModifier;
        hp = Dice.rollDice(3,6) + hpModifier;
        maxHp = hp;
        strength = 3;
        this.strengthModifer = strengthModifier;
        dexterity = 8;
        this.dexterityModifier = dexerityModifier;
        wisdom = 3;
        this.wisdomModifier = wisdomModifier;
        this.damageModifier = attackModifier;

        abilities.add(new Ability("Swipe",
                                  0,
                                  "1d4",
                                  Ability.STRENGTH,
                                  0,
                                  0,
                                  0));
        abilities.add(new Ability("Putrid Bile",
                                  0,
                                  "1d4",
                                  Ability.WISDOM,
                                  0,
                                  0,
                                  0));
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

        //choose either (1) Swipe or (2) Putrid Bile
        int choice = (int) (1 + Math.random() * 2);
        Ability a;
        if(choice == 1) {
            a = abilities.get(0);
        }
        else {
            a = abilities.get(1);
        }

        int damage = Dice.rollDice(a.getNumOfDie(),a.getDieSides());
        damage += damageModifier;
        if(a.getModifierType() == Ability.STRENGTH) {
            damage += strengthModifer;
        }
        else if(a.getModifierType() == Ability.DEXTERITY) {
            damage += dexterityModifier;
        }
        else {
            damage += wisdomModifier;
        }

        player.setHp(player.getHp() - damage);
        Main.addCombatText(getName() + " used [" + a.getName() +" ] for " + damage + " damage.");
        Main.turn = Main.PLAYERTURN;
        lastAct = System.currentTimeMillis();
    }
}
