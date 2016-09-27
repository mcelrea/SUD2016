package sample;

public class Skeleton extends Enemy{

    public Skeleton(int hp, String name) {
        super(hp, name, "S");
        actRate = 1000; //1 second
        lastAct = System.currentTimeMillis();
    }

    @Override
    public void act(World world, Player player) {
        if(lastAct + actRate <= System.currentTimeMillis()) {
            setCol(getCol() - 1);//move left
            lastAct = System.currentTimeMillis();
        }
    }

    @Override
    public void attack(Player player) {

    }
}
