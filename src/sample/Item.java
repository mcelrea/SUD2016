package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Item {
    private String name;
    private String description;
    private int vitality; //more vitality = more health
    private int xp;
    private int strength; //more strength = hit harder
    private int luck; //more luck = more critical hits
    private int magicka; //need magicka to cast spells
    private int intelligence; //more intel = stronger magick
    private int wisdom; //more wisdom = more magicka
    private int row;
    private int col;
    private String symbol;
    private Ability ability;
    private Image image;

    public Item(String name, String description, String path) {
        this.name = name;
        this.description = description;
        File file = new File(path);
        try {
            image = new Image(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(image,
                Main.OFFSET+col*20,
                Main.OFFSET+row*20-20);
    }
}
