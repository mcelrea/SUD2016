package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class Enemy {
    protected int hp;
    protected int maxHp;
    protected String name;
    protected String symbol;
    protected int row;
    protected int col;
    protected long actRate; //1000 ms = 1 second
    protected long lastAct; //1000 ms = 1 second
    protected Color color = Color.BLACK;
    protected Image forwardImage;
    protected int xp;
    protected int healthDieCount;
    protected int healthDieSides;
    protected int hpModifier;
    protected int strength;
    protected int strengthModifer;
    protected int dexterity;
    protected int dexterityModifier;
    protected int wisdom;
    protected int wisdomModifier;
    protected int damageDieCount;
    protected int damageDieSides;
    protected int damageModifier;
    protected ArrayList<Ability> abilities = new ArrayList<Ability>();

    public Enemy() {

    }

    protected Enemy(String name, String healthDie, int hpModifier,
                    int strength, int dexterity, int wisdom,
                    String damage, int damageModifier) {
        this.name = name;
        this.hpModifier = hpModifier;
        this.strength = strength;
        this.dexterity = dexterity;
        this.wisdom = wisdom;
        this.damageModifier = damageModifier;

        //create random health
        int dLoc = damage.indexOf("d");
        healthDieCount = Integer.parseInt(damage.substring(0,dLoc));
        healthDieSides = Integer.parseInt(damage.substring(dLoc+1));
        hp = Dice.rollDice(healthDieCount,healthDieSides) + hpModifier;

        //create damage die
        dLoc = damage.indexOf("d");
        damageDieCount = Integer.parseInt(damage.substring(0,dLoc));
        damageDieSides = Integer.parseInt(damage.substring(dLoc+1));
    }

    public Enemy(int hp, String name, String symbol) {
        this.hp = hp;
        this.maxHp = hp;
        this.name = name;
        this.symbol = symbol;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        //gc.fillText(symbol,Main.OFFSET+col*20,Main.OFFSET+row*20);
        gc.drawImage(forwardImage,Main.OFFSET+col*20,Main.OFFSET+row*20-20);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public abstract void act(World world, Player player);
    public abstract void attack(Player player);
    public abstract int getDroppedGold(Player player);

    public int getHealthDieCount() {
        return healthDieCount;
    }

    public void setHealthDieCount(int healthDieCount) {
        this.healthDieCount = healthDieCount;
    }

    public int getHealthDieSides() {
        return healthDieSides;
    }

    public void setHealthDieSides(int healthDieSides) {
        this.healthDieSides = healthDieSides;
    }

    public int getHpModifier() {
        return hpModifier;
    }

    public void setHpModifier(int hpModifier) {
        this.hpModifier = hpModifier;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrengthModifer() {
        return strengthModifer;
    }

    public void setStrengthModifer(int strengthModifer) {
        this.strengthModifer = strengthModifer;
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

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getWisdomModifier() {
        return wisdomModifier;
    }

    public void setWisdomModifier(int wisdomModifier) {
        this.wisdomModifier = wisdomModifier;
    }

    public int getDamageDieCount() {
        return damageDieCount;
    }

    public void setDamageDieCount(int damageDieCount) {
        this.damageDieCount = damageDieCount;
    }

    public int getDamageDieSides() {
        return damageDieSides;
    }

    public void setDamageDieSides(int damageDieSides) {
        this.damageDieSides = damageDieSides;
    }

    public int getDamageModifier() {
        return damageModifier;
    }

    public void setDamageModifier(int damageModifier) {
        this.damageModifier = damageModifier;
    }
}
