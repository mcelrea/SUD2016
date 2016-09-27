package sample;

public class Skeleton extends Enemy{

    public Skeleton(int hp, String name) {
        super(hp, name, "S");
    }

    @Override
    public void act(World world, Player player) {
        setCol(getCol()-1);//move left
    }

    @Override
    public void attack(Player player) {

    }
}
