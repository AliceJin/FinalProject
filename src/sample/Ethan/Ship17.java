package sample.Ethan;

/**
 * Created by Ethan on 5/26/2017.
 */

import javafx.scene.Parent; //simply is the base class that contains the children

/**
 * extends from the parent class so that the data within Ship17
 * may be accessed from the application file directlty
 */

public class Ship17 extends Parent {
    /**
     * Setting the initial variables for the user's initial ships
     */

    // the length of the ship
    public int type;

    //the orientation of the ship, specifically vertical or horizontal
    public boolean vertical = true;

    //the current health of the ship
    private int health;

    /**
     * Constructs the actual ships themselves and sets the
     * values of the type, orientation, and health based
     * on the user's inputs
     */

    public Ship17(int type, boolean vertical) {
        //sets the type of ship and orientation
        this.type = type;
        this.vertical = vertical;
        //sets the amount of health equal to the original length of the ship, i.e. ship 5 long = 5 health
        health = type;

    }

    /**
     * Methods that decreases the total amount of health
     * the ship has every single time the shpis are hit
     * but stop once that ship no longer has any more
     * health
     */

    //deacreases the health by 1 and only 1 each time
    public void hit() {
        health--;
    }
    
    //checks to see if the health value is greater than 0
    public boolean isAlive() {
        return health > 0;
    }

}
