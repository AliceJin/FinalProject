package sample.Alice.Alice_Tutorial17; /**
 * Created by jinq7372 on 5/26/2017.
 */

import javafx.scene.Parent;    //base class with all children in scene graph

/**
 * extends Parent class, allow to show on game board
 */
public class Alice_Ship17 extends Parent {

    /**
     * initial variables for the user's ships
     */
    public int type;   //length of the ship
    public boolean vertical = true;    //ship orientation vertical or horizontal
    private int health;   //current health/lives of the ships

    /**
     * constructor with parameters
     * of the length (int) and the orientation (boolean) of the ship
     */
    public Alice_Ship17(int type, boolean vertical)
    {
        //sets type and vertical as the input values
        this.type = type;
        this.vertical = vertical;
        //initial health is the total number of squares of the ship
        health = type;
    }

    /**
     * Method function: decrease ship health by one whenever the user's ships are hit.
     * Code to make sure health doesn't go to zero is included in user interface.
     */
    public void hit()
    {
        health--;     //changes value of health, doesn't return anything
    }

    /**
     * Method function: checks if ship is alive by whether health is greater than 0.
     * Returns a boolean based on a conditional statement.
     */
    public boolean isAlive()
    {
        return health > 0;        //return true if greater than zero
    }
}
