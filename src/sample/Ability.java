package sample;

public class Ability {
    private String name;
    private String description;
    private double cost;
    private int dieSides; //how many sides to the damage die
    private int numOfDie; //how many damage die will be rolled
    public static final int DEXTERITY=1,STRENGTH=2,WISDOM=3;
    private int modifierType;   //what stat modifies the damage
    private int strengthPrereq; //how much strength is required to use it
    private int dexterityPrereq;//how much dexterity is required to use it
    private int wisdomPrereq;   //how much wisdom is required to use it

    public Ability(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Ability(String name, int cost, String damage, int mType, int sPrereq, int dPrereq, int wPrereq) {
        this.name = name;
        this.cost = cost;
        modifierType = mType;
        strengthPrereq = sPrereq;
        dexterityPrereq = dPrereq;
        wisdomPrereq = wPrereq;
        //go from "2d6" to numOfDie = 2, dieSides = 6
        int dLoc = damage.indexOf("d");
        numOfDie = Integer.parseInt(damage.substring(0,dLoc));
        dieSides = Integer.parseInt(damage.substring(dLoc+1));
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getDieSides() {
        return dieSides;
    }

    public void setDieSides(int dieSides) {
        this.dieSides = dieSides;
    }

    public int getNumOfDie() {
        return numOfDie;
    }

    public void setNumOfDie(int numOfDie) {
        this.numOfDie = numOfDie;
    }

    public int getModifierType() {
        return modifierType;
    }

    public void setModifierType(int modifierType) {
        this.modifierType = modifierType;
    }

    public int getStrengthPrereq() {
        return strengthPrereq;
    }

    public void setStrengthPrereq(int strengthPrereq) {
        this.strengthPrereq = strengthPrereq;
    }

    public int getDexterityPrereq() {
        return dexterityPrereq;
    }

    public void setDexterityPrereq(int dexterityPrereq) {
        this.dexterityPrereq = dexterityPrereq;
    }

    public int getWisdomPrereq() {
        return wisdomPrereq;
    }

    public void setWisdomPrereq(int wisdomPrereq) {
        this.wisdomPrereq = wisdomPrereq;
    }
}
