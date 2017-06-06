package sample.Cody.Cats;

import java.util.ArrayList; // all of the important imports that were needed for this code to actually not crash
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox ;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Board1 extends Parent {
    private VBox rows = new VBox();
    private boolean enemy = false; //flag whether the board is friendly or enemy
    public int ships = 5; // the number of ships per board. Shows who wins or not.

    public Board1(boolean enemy, EventHandler<? super MouseEvent> handler) { //used for mouse clicks on cells
        this.enemy = enemy;
        for (int y = 0; y<10; y++){  // the for loops create a 10X10 grid of cells in which the player can click on
            HBox row = new HBox();
            for (int x =0; x < 10; x++){
                Cell c = new Cell(x,y,this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }
    public boolean placeShip(Ship1 ship, int x, int y){ //method used to place the ship
        if (canPlaceShip(ship, x, y)){ //Checks the location to see if a ship can be placed at the location
            int length = ship.type;
            boolean vertical = ship.vertical; //storing the ship vertical

            if (vertical){ //if it is vertical
                for (int i = y; i < y+length; i++){ //assigns the y values to a ship
                    Cell cell = getCell(x,i);
                    cell.ship = ship;
                    if (!enemy){
                        cell.setFill(Color.WHITE); //changes the color of the ships so that they contrast to the color of the cell
                        cell.setFill(Color.GREEN);

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
            return true; //the ship is placed
        }
        return false; // the ship is not placed
    }
    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);  //returns the cell; get row first as a whole, set as HBox, and then get cell
    }

    private Cell[] getNeighbors(int x, int y) {
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Cell> neighbors = new ArrayList<Cell>();

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int)p.getX(), (int)p.getY()));
            }
        }

        return neighbors.toArray(new Cell[0]);
    }

    private boolean canPlaceShip(Ship1 ship, int x, int y) {
        int length = ship.type; // makes length a local variable

        if (ship.vertical) { //checks if the ship is vertical
            for (int i = y; i < y + length; i++) {// checks if the position is a valid point
                if (!isValidPoint(x, i))
                    return false;

                Cell cell = getCell(x, i);
                if (cell.ship != null) //if a ship already exist at the point return false
                    return false;

                for (Cell neighbor : getNeighbors(x, i)) {
                    if (!isValidPoint(x, i))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }
        else {
            for (int i = x; i < x + length; i++) { //if the cell is not in the grid it returns false
                if (!isValidPoint(i, y))
                    return false;

                Cell cell = getCell(i, y);
                if (cell.ship != null)
                    return false;

                for (Cell neighbor : getNeighbors(i, y)) {
                    if (!isValidPoint(i, y))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }

        return true;
    }

    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public class Cell extends Rectangle { //every square in the board is a cell
        public int x,y; //the position of the cell
        public Ship1 ship = null;
        public boolean wasShot = false; //tracks if each cell has been shot

        private Board1 board;

        public Cell(int x , int y, Board1 board) { // the position of the cell on the board
            super(30, 30); //the size of the board
            this.x = x;  // sets the variables equal to input
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTGRAY); //sets the grids colors
            setStroke(Color.BLACK);
        }


        public boolean shoot(){
            wasShot = true; //if a cell is shot changes its color to black
            setFill(Color.BLACK);

            if (ship != null) {
                ship.hit();
                setFill(Color.RED); // if a ship was shot changes its color to red
                if (!ship.isAlive()){
                    board.ships--;
                }
                return true;
            }
            return false;

        }
    }
}