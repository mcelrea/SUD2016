package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Store {

    private ArrayList<Ability> abilities = new ArrayList<Ability>();
    int row;
    int col;

    public void addAbility(Ability a) {
        abilities.add(a);
    }

    public void purchaseAbility(Player player, int num) {
        //make sure ability exists
        if(num-1 < abilities.size()) {
            Ability a = abilities.get(num-1);
            if(player.getGold() >= a.getCost() &&
                    player.getStrength() >= a.getStrengthPrereq() &&
                    player.getDexterity() >= a.getDexterityPrereq() &&
                    player.getWisdom() >= a.getWisdomPrereq()) {
                player.setGold(player.getGold()-a.getCost());
                player.addAbility(a);
                Main.addCombatText("You purchased " + a.getName() + " for " +
                    a.getCost() + " gold", Color.GOLD);
            }
            else {
                Main.addCombatText("You do not meet the requirements to purchase that", Color.DARKSLATEGRAY);
            }
        }
    }

    public void draw(GraphicsContext gc) {
        //clear the screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,800,600);

        //choose a text color
        gc.setFill(Color.WHITE);

        gc.fillText("STORE", 400,15);

        //put every purchaseable ability on the screen
        for(int i=0; i < abilities.size(); i++) {
            gc.fillText((i+1) + ". " + abilities.get(i).getName(),
                                       200, 200+(i*25));
        }
    }
}
