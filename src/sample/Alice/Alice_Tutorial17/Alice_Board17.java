package sample.Alice.Alice_Tutorial17;/**
 * Created by jinq7372 on 5/26/2017.
 */

/**
 * All imports related to java
 */
import java.util.ArrayList;      //allow to use ArrayLists class
import java.util.List;           //allow to use Lists class

/**
 * All imports related to javafx
 */
import javafx.scene.Parent;               //base class with all children in scene graph
import javafx.event.EventHandler;         //responds to events
import javafx.geometry.Point2D;           //
import javafx.scene.input.MouseEvent;     //respond to mouse input
import javafx.scene.layout.HBox;          //a type of layout/formatting
import javafx.scene.layout.VBox;          //another type of layout
import javafx.scene.paint.Color;          //class to fill in color
import javafx.scene.shape.Rectangle;      //

/**
 * extends Parent class, allow to show on game board
 * NOTE TO GROUP MEMBERS: you have to change Alice_Board17 and others to
 * the appropriate class name for your file.
 */
public class Alice_Board17 extends Parent {
    private VBox rows = new VBox();       //layout 10 flat boxes vertically
    private boolean enemy = false;        //whether it's the enemy board
    public int ships = 5;                 //the number of total user ships still alive

    /**
     * Constructor for setting up the board.
     * Takes parameters: whether board is the enemy's (boolean) and
     * and handler for processing mouse clicks on cells
     */
    public Alice_Board17(boolean enemy, EventHandler<? super MouseEvent> handler)
    {
        this.enemy = enemy;               //set variable equal to input
        //loop for establishing number of boxes, 10 by 10
        for(int y = 0; y < 10; y++)
        {
            HBox row = new HBox();        //place boxes horizontally
            for(int x = 0; x < 10; x++)
            {
                Cell c = new Cell(x, y, this);     //this refers to board
                c.setOnMouseClicked(handler);             //trigger on mouse click
                row.getChildren().add(c);                 //add cells as children to rows
            }
            rows.getChildren().add(row);                  //add row to list of rows
        }
        getChildren().add(rows);      //add to Parent, display with javafx
    }

    /**
     * Method function: placing a specified ship on the board with color showing where
     * Parameters: ship, x and y coordinates as integers.
     * Returns: boolean to show whether ship was placed.
     */
    public boolean placeShip(Alice_Ship17 ship, int x, int y)
    {
        //if statement: tests whether one can place the ship, returns false otherwise
        if(canPlaceShip(ship, x, y))
        {
            int length = ship.type;     //store length in local variable

            if(ship.vertical)
            {
                //if ship is vertical, color ship vertically along the length from y coordinate
                for(int i = y; i < y + length; i++)
                {
                    Cell cell = getCell(x, i);      //current cell in loop
                    cell.ship = ship;               //ship in cell is referenced to current ship
                    if(!enemy)                      //if not the enemy board
                    {
                        cell.setFill(Color.WHITE);     //fill in white
                        cell.setStroke(Color.GREEN);   //green border
                    }
                }
            }
            else
            {
                //if ship is horizontal. color ship horizontally along length from x coordinate
                for(int i = x; i < x + length; i++)
                {
                    Cell cell = getCell(i, y);      //current cell in loop
                    cell.ship = ship;               //ship in cell is referenced to current ship
                    if(!enemy)                      //if not the enemy board
                    {
                        cell.setFill(Color.WHITE);     //fill in white
                        cell.setStroke(Color.GREEN);   //green border
                    }
                }
            }

            return true;  //ship has been placed
        }

        return false;     //ship cannot be placed
    }

    /**
     * Method function: return the cell at x and y coordinates
     */
    public Cell getCell(int x, int y)
    {
        //returns the cell; get row first as a whole, set as HBox, and then get cell
        return (Cell) ((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    /**
     * Method function: checks whether one can place a ship according to user input.
     * Called by the placeShip method.
     * Parameters: ship, x and y coordinates as integers.
     * Returns: boolean to show whether ship can be placed.
     */
    private boolean canPlaceShip(Alice_Ship17 ship, int x, int y)
    {
        
    }

    /**
     * Class: each Cell represents an individual box on the grid
     */
    public class Cell extends Rectangle {
        public int x, y;                  //position of cell on grid
        public Alice_Ship17 ship = null;  //whether cell contains the ship
        public boolean wasShot = false;   //whether the cell has been shot by enemy

        private Alice_Board17 board;      //make sure references the correct board

        /**
         * Constructor with parameters
         * x and y coordinates (int) and the board (Board)
         */
        public Cell(int x, int y, Alice_Board17 board)
        {
            super(30, 30);                //calls Rectangle class with 30 by 30 pixels
            this.x = x;                   //sets variables equal to input
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTCORAL);        //fill in the default color for cell
            setStroke(Color.DARKSLATEBLUE);   //fill in the border for cell
        }

        /**
         * Method function: changes color appropriately when hit by enemy.
         * Sets different colors depending on whether cell contained a ship or not
         */
        public boolean shoot()
        {
            wasShot = true;               //ship is shot
            setFill(Color.BLACK);         //set to black if cell doesn't have ship

            //if cell contained ship, set to red
            if(ship != null) {
                ship.hit();               //decrease health, method in Ship class
                setFill(Color.RED);       //set color to red, override
                //if ship is dead
                if (!ship.isAlive()) {
                    board.ships--;       //reduce the number of ships
                }
                return true;             //ship was hit
            }
            return false;                //ship was not hit
        }
    }
}
