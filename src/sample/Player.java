package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by mcelrea on 9/2/2016.
 */
public class Player {
    private String name;
    private int row;//row within a room
    private int col;//col within a room
    private int worldRow; //row within the world
    private int worldCol; //col within the world
    private int hp=6;
    private int maxHp=6;
    private int vitality=1; //more vitality = more health
    private int xp=0;
    private int strength=1; //more strength = hit harder
    private int damageRating=1;
    private int luck=1; //more luck = more critical hits
    private int magicka=3; //need magicka to cast spells
    private int intelligence=1; //more intel = stronger magick
    private int wisdom=1; //more wisdom = more magicka
    private int level = 1;
    private int xpLevels[] = {0,0,10,20,30,50,75,100};

    public Player(String n) {
        name = n;
        row = 10;
        col = 10;
        worldRow = 10;
        worldCol = 10;
        updateStats();
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillText("@", Main.OFFSET+col*Main.CELLSIZE, Main.OFFSET+row*Main.CELLSIZE);

        //draw stat box
        gc.setFill(Color.BLACK);
        gc.fillText(name + " - " + level,510,95);
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(500,100,250,210,10,10);

        //write the stat text
        gc.setFill(Color.BLACK);
        gc.fillText("      Health: " + hp, 510, 125);
        gc.setFill(Color.BLACK);
        gc.fillText("     Magicka: " + magicka, 510, 145);
        gc.setFill(Color.BLACK);
        gc.fillText("          XP: " + xp, 510, 165);
        gc.setFill(Color.BLACK);
        gc.fillText("    Strength: " + strength, 510, 200);
        gc.setFill(Color.BLACK);
        gc.fillText("Intelligence: " + intelligence, 510, 220);
        gc.setFill(Color.BLACK);
        gc.fillText("      Wisdom: " + wisdom, 510, 240);
        gc.setFill(Color.BLACK);
        gc.fillText("    Vitality: " + vitality, 510, 260);
        gc.setFill(Color.BLACK);
        gc.fillText("        Luck: " + luck, 510, 280);
        gc.setFill(Color.BLACK);
        gc.fillText("   D. Rating: " + damageRating, 510, 300);

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

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
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

    public int getDamageRating() {
        return damageRating;
    }

    public void setDamageRating(int damageRating) {
        this.damageRating = damageRating;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getMagicka() {
        return magicka;
    }

    public void setMagicka(int magicka) {
        this.magicka = magicka;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void updateStats() {
        hp = (vitality*2) + (level*2) + 2;
        maxHp = (vitality*2) + (level*2) + 2;
        magicka = (wisdom * 2);
        damageRating = (strength * 2);

        for(int i=0; i < xpLevels.length; i++) {
            if(xp < xpLevels[i]) {
                level = i-1;
                break;
            }
        }
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
}
