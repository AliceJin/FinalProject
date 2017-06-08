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
    /** */
    private boolean findShip = false;      //whether AI needs to continue finding ship
    private boolean contHit = false;       //found ship to continue hitting
    //private boolean neighborCell = false;  //there are still neighboring cells to check
    //private Cell[] neighborsLeft = new Cell[4];     //array of neighbors left to check
    ArrayList<Cell> neighborsLeft = new ArrayList<Cell>();     //arraylist of neighbors left to check
    private int n = 0;     //number of neighbor cell in arraylist
    private int currentX;
    private int currentY;
    private int nextX;
    private int nextY;

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
     *  Method function: controlling enemy moves
     *  NOTE by Alice: I'll do this part, although Cody can do the losing message in the last if statement
     */
    private void enemyMove()
    {
        while(enemyTurn)
        {
            if(findShip)   //neighboring mode
            {
                    while(n < neighborsLeft.size())   //run through all the neighbors
                    {
                        if(neighborsLeft.get(n).wasShot)        //if shot, next iteration of the loop
                        {
                            n++;         //go to next neighbor element
                            continue;
                        }
                        enemyTurn = neighborsLeft.get(n).shoot();     //shoot and set enemyTurn equal

                        if(!enemyTurn)      //AI missed
                        {
                            n++;          //move on to next neighbor
                            break;        //exit without changing findShip
                        }
                        else          //AI hit
                        {
                            nextX = neighborsLeft.get(n).xCor();    //set next cell to be the cell that was hit
                            nextY = neighborsLeft.get(n).yCor();
                            contHit = true;                         //move on to hit in certain direction
                            n = 0;                                  //reset n
                            break;                                  //exit while loop
                        }
                    }
                while(contHit)    //continue to hit along ship until ship sunk
                {
                    //get next cell along the length of the ship
                    int hitX = nextX + nextX - currentX;
                    int hitY = nextY + nextY - currentY;
                    if(playerBoard.isValidPoint((double) hitX,(double) hitY))      //check if valid point
                    {
                        Cell cellTest = playerBoard.getCell(hitX, hitY);
                        enemyTurn = cellTest.shoot();           //shoot the next cell
                        if(enemyTurn)    //if next cell has been hit
                        {
                            //increment each cell further along ship
                            currentX = nextX;
                            currentY = nextY;
                            nextX = cellTest.xCor();
                            nextY = cellTest.yCor();

                        }
                        else
                        {
                            contHit = false;           //exit loop after ship completely sunk
                            findShip = false;          //no need to continue neighboring mode anymore
                            break;                     //exit while loop
                        }
                    }
                    else
                    {
                        contHit = false;
                        findShip = false;
                        break;
                    }
                }
            }
            else           //random mode
            {
                //random coordinates between 0 and 9, inclusive
                int x = random.nextInt(10);
                int y = random.nextInt(10);

                Cell cell = playerBoard.getCell(x, y);    //get player cell at the random coordinates
                if(cell.wasShot)
                {
                    continue;       //don't do anything if cell already shot
                }
                enemyTurn = cell.shoot();                 //hits ship; if hit, enemy can continue turn and hit again

                if(enemyTurn)
                {
                    findShip = true;                      //if it's a hit, find further cells neighboring to hit
                    currentX = x;                         //currently on this cell
                    currentY = y;
                    neighborsLeft = playerBoard.getNeighbors2(x, y);    //get neighbors if enemy turn.
                }
                else
                {
                    findShip = false;                     //no more need to find neighboring cells.
                }
            }

            if(playerBoard.ships == 0)                //if all player's ships shot
            {
                System.out.println("You Lose");
                System.exit(0);
            }
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
            primaryStage.setResizable(false);           //user can't resize the window
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
