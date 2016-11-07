package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by mcelrea on 9/2/2016.
 */
public class Player {
    private String name;
    private int row;//row within a room
    private int col;//col within a room
    private int worldRow; //row within the world
    private int worldCol; //col within the world
    private int hp;
    private int maxHp;
    private int xp=0;
    private int strength; //more strength = hit harder
    private int strengthModifier; //how much harder
    private int dexterity; //more dexterity = hit harder with arrows, knives
    private int dexterityModifier; //how much harder
    private int constitution; //more health
    private int constitutionModifier; //how much more health
    private int wisdom; //spell damage
    private int wisdomModifier; //how much more damage
    private int armorClass; //how hard are we to hit
    private int gold;
    private int level = 1;
    private int xpLevels[] = {0,0,10,20,30,50,75,100};
    private Ability activeAbilities[] = new Ability[6];
    private ArrayList<Ability> inactiveAbilities = new ArrayList<Ability>();
    private Image forwardImage;

    public Player(String n) {
        name = n;
        row = 10;
        col = 10;
        worldRow = 10;
        worldCol = 10;
        File file = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUD P2\\src\\images\\characterForward.png");
        try {
            forwardImage = new Image(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        strength = Dice.rollForStartingStats();
        strengthModifier = strength/2;
        dexterity = Dice.rollForStartingStats();
        dexterityModifier = dexterity/2;
        wisdom = Dice.rollForStartingStats();
        wisdomModifier = wisdom/2;
        constitution = Dice.rollForStartingStats();
        constitutionModifier = constitution/2;
        armorClass = 10 + dexterityModifier;
        gold = Dice.rollDice(4,4) + 10;
        hp = Dice.rollDie(8) + constitution/2;
        maxHp = hp;
    }

    public void drawAbilities(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillText("Active Abilities", 100,175);
        //go through all active abilities
        for(int i=0; i < activeAbilities.length; i++) {
            if(activeAbilities[i] == null) {
                gc.fillText((i+1) + ". no ability", 100, 200+(i*25));
            }
            else {
                gc.fillText((i+1) + ". " + activeAbilities[i].getName(), 100, 200+(i*25));
            }
        }
    }

    public void addAbility(Ability a) {
        inactiveAbilities.add(a);

        for(int i=0; i < activeAbilities.length; i++) {
            if(activeAbilities[i] == null) {
                activeAbilities[i] = a;
                break;//exit, stop looking
            }
        }
    }

    public void useAbility(int abilityNum, Enemy enemy) {
        Ability a = activeAbilities[abilityNum-1];

        if(a != null) {
            //create a random number between minDamage and maxDamage
            //int damage = (int) (a.getMinDamage() + Math.random() * a.getMaxDamage());
            int damage = Dice.rollDice(a.getNumOfDie(), a.getDieSides());

            int critMissChance = Dice.rollDie(20);
            //if a 20 is rolled
            if(critMissChance == 20) {
                damage *= 2;//double the damage
                Main.addCombatText("Player critically hits!!!!", Color.BLUEVIOLET);
            }
            else if (critMissChance == 1) {
                Main.addCombatText("You use [" + a.getName() + "] but critically miss!", Color.BLUEVIOLET);
                return;
            }

            if(a.getModifierType() == Ability.STRENGTH) {
                damage += strengthModifier;
            }
            else if(a.getModifierType() == Ability.DEXTERITY) {
                damage += dexterityModifier;
            }
            else {
                damage += wisdomModifier;
            }

            enemy.setHp(enemy.getHp() - damage);
            Main.addCombatText("You use [" + a.getName() + "] to cause " + damage + " damage.", Color.BLUEVIOLET);
            Main.turn = Main.ENEMYTURN;
        }
    }








    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        //gc.fillText("@", Main.OFFSET+col*Main.CELLSIZE, Main.OFFSET+row*Main.CELLSIZE);
        gc.drawImage(forwardImage,Main.OFFSET+col*Main.CELLSIZE, Main.OFFSET+row*Main.CELLSIZE-20);

        //draw stat box
        gc.setFill(Color.BLACK);
        gc.fillText(name + " - " + level,510,95);
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(500,100,250,210,10,10);

        //write the stat text
        gc.setFill(Color.BLACK);
        gc.fillText("      Health: " + hp, 510, 125);
        gc.setFill(Color.BLACK);
        gc.fillText("          XP: " + xp, 510, 145);
        gc.setFill(Color.BLACK);
        gc.fillText("    Strength: " + strength, 510, 165);
        gc.setFill(Color.BLACK);
        gc.fillText("   Dexterity: " + dexterity, 510, 185);
        gc.setFill(Color.BLACK);
        gc.fillText("      Wisdom: " + wisdom, 510, 205);
        gc.setFill(Color.BLACK);
        gc.fillText("Constitution: " + constitution, 510, 225);
        gc.setFill(Color.BLACK);
        gc.fillText(" Armor Class: " + armorClass, 510, 245);
        gc.setFill(Color.BLACK);
        gc.fillText("        Gold: " + gold, 510, 265);
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
            //he's leaving the room
            if(room.getCell(row,col) == 100) {
                worldRow++; //change the room
                row = 1; //place player at bottom of next Room
            }
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
            //he's leaving the room
            if(room.getCell(row,col) == 100) {
                worldCol--; //move player to next room
                col = 18; //set player on right side of the screen
            }
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
            //he's leaving the room
            if(room.getCell(row,col) == 100) {
                worldCol++; //move player to next room
                col = 1; //set player on left side of the screen
            }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getStrengthModifier() {
        return strengthModifier;
    }

    public void setStrengthModifier(int strengthModifier) {
        this.strengthModifier = strengthModifier;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getDexterityModifier() {
        return dexterityModifier;
    }

    public void setDexterityModifier(int dexterityModifier) {
        this.dexterityModifier = dexterityModifier;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getConstitutionModifier() {
        return constitutionModifier;
    }

    public void setConstitutionModifier(int constitutionModifier) {
        this.constitutionModifier = constitutionModifier;
    }

    public int getWisdomModifier() {
        return wisdomModifier;
    }

    public void setWisdomModifier(int wisdomModifier) {
        this.wisdomModifier = wisdomModifier;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
