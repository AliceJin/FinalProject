package sample.Alice.Alice_Tutorial17; /**
 * Created by jinq7372 on 5/26/2017.
 */

/**
 * All imports related to java
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;                //random numbers

/**
 * All imports related to javafx
 */
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;             //allow positioning within layout
import javafx.scene.Parent;             //sets up a Parent class
import javafx.scene.Scene;              //scene for display
import javafx.scene.input.MouseButton;  //respond to left or right click on mouse
import javafx.scene.layout.BorderPane;  //layout with 5 major divisions
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;        //boxes placed vertically
import javafx.scene.text.Text;          //allow to display text
import javafx.stage.Stage;

import sample.Alice.Alice_Tutorial17.Alice_Board17.Cell;     //import Cell from the Board program
/**
 * Class equivalent to BattleshipMain in the YouTube tutorial
 */
public class Alice_B2 extends Application{

    /**
     * * variables
     */

    private boolean running = false;      //false is user still placing ships, true if user is playing the game
    private Alice_Board17 enemyBoard, playerBoard;    //enemy and player boards

    private int shipsToPlace = 5;         //lets user place 5 ships
    private boolean enemyTurn = false;    //whether it is enemy's turn

    private Random random = new Random();  //alternative to Math.random()

    /**
     *  variables for smartAI in enemyMove() method
     * */
    private boolean findShip = false;      //whether AI needs to continue to find ship
    private boolean contHit = false;       //found ship to continue hitting in certain direction
    private ArrayList<Cell> neighborsLeft = new ArrayList<Cell>();     //arraylist of neighbors left to check
    //private ArrayList<Cell> neighborsCurrent = new ArrayList<Cell>();   //
    //private ArrayList<Cell> neighborsEmpty = new ArrayList<Cell>();   //ex - arraylist of neighboring cells to avoid
    private int n = 0;        //number of neighbor cell in arraylist currently at during loop
    private int currentX;     //current x coordinates
    private int currentY;     //current y coordinates
    private int nextX;        //x coordinates of next cell to hit
    private int nextY;        //y coordinates of next cell to hit

    /**
     * Method function: creates layout of the boards and menu.
     * determines how the program responds as the game progresses.
     * Display winning message and end game.
     *
     * NOTE by Alice: you guys should work on this part mainly
     **/
    private Parent createContent()
        {
            BorderPane root = new BorderPane();    //layout with 5 places: top, bottom, middle, left, right
            root.setPrefSize(600, 800);  //600 by 800 pixels for size

            root.setRight(new Text("Right Sidebar - Controls"));   //set text for right portion

            //sets 2 boards with different handlers, respond to mouse click
            enemyBoard = new Alice_Board17(true, event -> {
                if(!running)
                {
                    return;      //return nothing, game hasn't started
                }

                Cell cell = (Cell) event.getSource();  //obtain cell user clicked on
                if(cell.wasShot)
                {
                    return;      //return nothing, cell had already been shot
                }

                //if hit enemy ship, set enemyTurn to false and allow user to keep on playing
                enemyTurn = !cell.shoot();

                //all enemy ships sunk and user wins.
                //NOTE by Alice: You guys can make this part more exciting & display directly on window
                //also make a replay option possible like how the guy said in the tutorial
                if(enemyBoard.ships == 0)
                {
                    System.out.println("You Win");
                    System.exit(0);         //exits immediately
                }

                //if it's the enemy's turn, smart AI
                //NOTE by Alice: I will be primarily working with this part
                if(enemyTurn)
                {
                    enemyMove();
                }
            });

            playerBoard = new Alice_Board17(false, event ->
            {
                if(running)
                    return;       //return nothing, player can't touch own board during game

                Cell cell = (Cell) event.getSource();  //obtain cell user clicked on
                if(playerBoard.placeShip(new Alice_Ship17(shipsToPlace, event.getButton() == MouseButton.PRIMARY),
                        cell.x, cell.y))   //vertical if left click, which is MouseButton.PRIMARY
                {
                    if(--shipsToPlace == 0)   //decrease ships number and check if zero
                    {
                        startGame();         //start the game when user placed all the ships
                    }
                }
            });

            VBox vbox = new VBox(50, enemyBoard,playerBoard);  //50 pixels of space inbetween
            vbox.setAlignment(Pos.CENTER);         //set to center of screen

            root.setCenter(vbox);                  //set to center of window layout

            return root;           //return customized BorderPane
        }


    /**
     *  Method function: controlling enemy moves with a semi-smart AI
     *  Will randomly select points to hit until a single cell is hit.
     *  Will check all valid neighboring cells until the ship orientation can be deduced.
     *  Will continue to hit in single direction until AI misses.
     */
    private void enemyMove()
    {
        while(enemyTurn)   //while it's still the enemy's turn
        {
            if(findShip)   //neighboring mode
            {
                    while(n < neighborsLeft.size())   //run through all the neighbors of current cell
                    {
                        if(neighborsLeft.get(n).wasShot)    //if shot, next iteration of the loop or stop if done
                        {
                            n++;                            //go to the next neighbor element
                            if(n < neighborsLeft.size())    //check if there are still neighbors to hit
                            {
                                continue;       //if there are; continue & shoot next cell
                            }
                            else   //all neighboring cells have been checked
                            {
                                n = 0;               //reset n
                                findShip = false;    //go back to random mode
                                break;               //leave while loop
                            }
                        }
                        enemyTurn = neighborsLeft.get(n).shoot();     //shoot and set enemyTurn equal to result

                        if(!enemyTurn)      //AI missed
                        {
                            n++;                           //move on to next neighbor
                            if(n < neighborsLeft.size())   //check if there are still neighbors to hit
                            {
                                break;        //exit without changing findShip, continue check with same cell
                            }
                            else   //all neighboring cells have been checked
                            {
                                n = 0;               //reset n
                                findShip = false;    //go back to random mode
                                break;               //leave while loop
                            }
                        }
                        else                //AI hit
                        {
                            nextX = neighborsLeft.get(n).xCor();    //set next cell to be the cell that was hit
                            nextY = neighborsLeft.get(n).yCor();
                            contHit = true;                         //contHit mode - hit along the ship orientation
                            n = 0;                                  //reset n
                            break;                                  //exit while loop
                        }
                    }
                while(contHit)    //continue to hit along ship in one direction until a miss
                {
                    //System.out.println("contHit");

                    //get next cell along the length of the ship - figure out proper direction
                    int hitX = nextX + nextX - currentX;
                    int hitY = nextY + nextY - currentY;
                    if(playerBoard.isValidPoint((double) hitX,(double) hitY))      //check if valid point
                    {
                        Cell cellTest = playerBoard.getCell(hitX, hitY);           //get cell
                        enemyTurn = cellTest.shoot();                              //shoot the cell
                        if(enemyTurn)    //if next cell has been hit
                        {
                            /**
                             * not working
                             */

                            /**
                            neighborsCurrent = playerBoard.getNeighbors2(hitX, hitY);
                            System.out.println("size of current: " + neighborsCurrent.size());
                            for(int i = 0; i < neighborsCurrent.size(); i++)
                            {
                                if(playerBoard.isValidPoint(neighborsCurrent.get(i).xCor(),
                                        neighborsCurrent.get(i).yCor()) && !neighborsCurrent.get(i).wasShot)
                                {
                                    //only add if it's a valid cell and hasn't been shot
                                    neighborsEmpty.add(neighborsCurrent.get(i));
                                    System.out.println("size of empty: " + neighborsEmpty.size());
                                }
                            }
                            neighborsEmpty.remove(cellTest);   //remove hit cell from list
                             **/


                            /**
                             *
                             */
                            //increment each cell further along ship
                            currentX = nextX;
                            currentY = nextY;
                            nextX = cellTest.xCor();    //next coordinates are set as the cell that just been hit
                            nextY = cellTest.yCor();

                        }
                        else     //failed to hit ship
                        {
                            contHit = false;           //no need for contHit mode
                            findShip = false;          //no need to continue neighboring mode anymore
                            break;                     //exit while loop
                        }
                    }
                    else   //not a valid point - ship near board boundary
                    {
                        contHit = false;        //no need for contHit
                        findShip = false;       //no need to continue neighboring mode
                        break;                  //exit while loop
                    }
                }
            }
            else           //random mode - default mode
            {
                //random coordinates between 0 and 9, inclusive
                int x = random.nextInt(10);
                int y = random.nextInt(10);

                Cell cell = playerBoard.getCell(x, y);    //get player cell at the random coordinates
                if(cell.wasShot)
                {
                    continue;       //don't do anything if cell already shot
                }
                /**
                if(neighborsEmpty.indexOf(cell) >= 0)
                {
                    continue;       //don't do anything if cell is a neighbor of a already hit cell
                }
                 **/
                enemyTurn = cell.shoot();                 //hits ship; if hit, enemy can continue turn and hit again

                if(enemyTurn)
                {
                    findShip = true;                      //if it's a hit, switch to neighboring mode
                    currentX = x;                         //current coordinates of cell
                    currentY = y;
                    neighborsLeft = playerBoard.getNeighbors2(x, y);    //get neighbors of the cell
                }
                else
                {
                    findShip = false;                     //no more need to find neighboring cells to hit
                }
            }
        }
        if(playerBoard.ships == 0)                //if all player's ships shot
        {
            System.out.println("You Lose");       //losing message
            System.exit(0);
        }
    }

    /**
     * Method function: placing the enemy ships randomly
     */
    private void startGame()
    {
        int type = 5;             //total number of ships

        while(type > 0)
        {
            //random coordinates between 0 and 9, inclusive
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if(enemyBoard.placeShip(new Alice_Ship17(type, Math.random() < 0.5), x, y))
            {
                //vertical ship placed if Math.random yields less than 0.5
                type--;       //size of ships decreases as loop continues
            }
        }
        running = true;       //game has started
    }

    /**
     * Basic setup and layout for the window
         */
        @Override
        public void start(Stage primaryStage)
        {
            Scene scene = new Scene(createContent());   //content for scene is created
            primaryStage.setTitle("Battleship");        //title for window
            primaryStage.setScene(scene);               //set default scene
            //primaryStage.setResizable(false);           //user can't resize the window
            primaryStage.show();                        //displays the window
        }

        /**
         * launches the application
         */
        public static void main(String[]args)
        {
        launch(args);
        }
    }
