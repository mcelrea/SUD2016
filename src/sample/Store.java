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

    public void draw(GraphicsContext gc, Player player) {
        //clear the screen
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,800,600);

        //draw player gold
        gc.setFill(Color.GOLD);
        gc.fillText("Gold: " + player.getGold(), 620, 25);

        //choose a text color
        gc.setFill(Color.WHITE);

        gc.fillText("STORE", 400,15);

        //put every purchaseable ability on the screen
        gc.fillText("Item               Cost    Strength  Dexterity  Wisdom",10,150);
        for(int i=0; i < abilities.size(); i++) {
            gc.setFill(Color.WHITE);
            gc.fillText((i+1) + "." + abilities.get(i).getName() + "(" +
                                      abilities.get(i).getNumOfDie() + "d" +
                                      abilities.get(i).getDieSides() + ")",
                                       10, 200+(i*25));
            gc.setFill(Color.GOLD);
            gc.fillText("" + abilities.get(i).getCost(),300,200+(i*25));

            if(abilities.get(i).getStrengthPrereq() <= player.getStrength()) {
                gc.setFill(Color.GREEN);
            }
            else {
                gc.setFill(Color.RED);
            }
            gc.fillText("" + abilities.get(i).getStrengthPrereq(),450,200+(i*25));

            if(abilities.get(i).getDexterityPrereq() <= player.getDexterity()) {
                gc.setFill(Color.GREEN);
            }
            else {
                gc.setFill(Color.RED);
            }
            gc.fillText("" + abilities.get(i).getDexterityPrereq(),590,200+(i*25));

            if(abilities.get(i).getWisdomPrereq() <= player.getWisdom()) {
                gc.setFill(Color.GREEN);
            }
            else {
                gc.setFill(Color.RED);
            }
            gc.fillText("" + abilities.get(i).getWisdomPrereq(),730,200+(i*25));
        }
    }
}
