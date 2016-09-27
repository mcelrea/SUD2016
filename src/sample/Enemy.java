package sample;

import javafx.scene.canvas.GraphicsContext;

public abstract class Enemy {
    private int hp;
    private String name;
    private String symbol;
    private int row;
    private int col;
    protected long actRate; //1000 ms = 1 second
    protected long lastAct; //1000 ms = 1 second

    public Enemy(int hp, String name, String symbol) {
        this.hp = hp;
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
        gc.fillText(symbol,Main.OFFSET+col*20,Main.OFFSET+row*20);
    }

    public abstract void act(World world, Player player);
    public abstract void attack(Player player);
}
