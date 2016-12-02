package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Zombie extends Enemy{

    public Zombie(String name, int hpModifier, int strengthModifier,
                    int dexerityModifier, int wisdomModifier, int attackModifier) {
        actRate = 500; //1/2 second
        lastAct = System.currentTimeMillis();
        color = Color.BLUEVIOLET;
        xp = 5;
        File file = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\images\\wall.png");
        try {
            forwardImage = new Image(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.name = name;
        this.hpModifier = hpModifier;
        hp = Dice.rollDice(3,8) + hpModifier;
        maxHp = hp;
        strength = 8;
        this.strengthModifer = strengthModifier;
        dexterity = 4;
        this.dexterityModifier = dexerityModifier;
        wisdom = 1;
        this.wisdomModifier = wisdomModifier;
        this.damageModifier = attackModifier;

        abilities.add(new Ability("Kick",
                0,
                "2d4",
                Ability.STRENGTH,
                0,
                0,
                0));
        abilities.add(new Ability("Head Butt",
                0,
                "1d10",
                Ability.STRENGTH,
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
        int critMissChance = Dice.rollDie(20);
        //if a 20 is rolled it's a critical hit
        if(critMissChance == 20) {
            damage *= 2;//double the damage
            Main.addCombatText("Zombie critically hits!!!!!!!", Color.RED);
        }
        //if a 1 is rolled a Skelton critically misses
        else if(critMissChance == 1) {
            Main.addCombatText(getName() + " attempted to use [" + a.getName() + "], but critically missed.", Color.RED);
            return; //exit
        }
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

        if(damage > 0) {
            player.setHp(player.getHp() - damage);
            Main.addCombatText(getName() + " used [" + a.getName() + " ] for " + damage + " damage.", Color.RED);
        }
        else {
            Main.addCombatText(getName() + " attempted to use [" + a.getName() + "], but missed.", Color.RED);
        }
        Main.turn = Main.PLAYERTURN;
        lastAct = System.currentTimeMillis();
    }

    @Override
    public int getDroppedGold(Player player) {
        int chance = (int) (1 + Math.random() * 100);

        //20% chance of dropping gold
        if(chance <= 20) {
            int gold = (int) (4 + Math.random() * 5);
            Main.addCombatText("Skeleton dropped " + gold + " gold", Color.GOLD);
            return gold;
        }
        else {
            return 0;
        }
    }
}
