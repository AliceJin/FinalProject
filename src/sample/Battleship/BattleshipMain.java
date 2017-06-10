package sample.Battleship;
/**
 * Created by huiying on 5/26/17.
 */

/**
 * All imports related to java
 */
import java.util.ArrayList;             //arraylist methods
import java.util.Random;                //random numbers

/**
 * All imports related to javafx
 */
import javafx.application.Application;
import javafx.geometry.Pos;             //allow positioning within layout
import javafx.scene.Parent;             //sets up a Parent class
import javafx.scene.Scene;              //scene for display
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;  //respond to left or right click on mouse
import javafx.scene.layout.BorderPane;  //layout with 5 major divisions
import javafx.scene.layout.VBox;        //boxes placed vertically
import javafx.scene.text.Text;          //allow to display text
import javafx.stage.Stage;

import sample.Battleship.Board.Cell;    //import Cell from the Board program
import sample.Battleship.ConfirmBox;

/**
 * Class equivalent to BattleshipMain in the YouTube tutorial
 */
public class BattleshipMain extends Application {

    /**
     * * variables
     */

    private boolean running = false;      //false is user still placing ships, true if user is playing the game
    private Board enemyBoard, playerBoard;    //enemy and player boards

    private int shipsToPlace = 5;         //lets user place 5 ships
    private boolean enemyTurn = false;    //whether it is enemy's turn

    private Random random = new Random();  //alternative to Math.random()

    /**
     *  variables for smartAI in enemyMove() method
     * */
    private boolean findShip = false;      //whether AI needs to continue to find ship
    private boolean contHit = false;       //found ship to continue hitting in certain direction
    private ArrayList<Cell> neighborsLeft = new ArrayList<Cell>();     //arraylist of neighbors left to check
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
     **/
    private Parent createContent()
    {


        BorderPane root = new BorderPane();    //layout with 5 places: top, bottom, middle, left, right
        root.setPrefSize(700, 900);  //600 by 800 pixels for size

        //root.setRight(new Text(" "));   //set text for right portion

        //sets 2 boards with different handlers, respond to mouse click
        enemyBoard = new Board(true, event -> {
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
            if(enemyBoard.ships == 0)
            {
                System.out.println("You Win");
                String win = "YOU WIN!!!!!";
                closeProgram(win);
            }

            //if it's the enemy's turn, smart AI
            if(enemyTurn)
            {
                enemyMove();
            }
        });

        playerBoard = new Board(false, event ->
        {
            if(running)
                return;       //return nothing, player can't touch own board during game

            Cell cell = (Cell) event.getSource();  //obtain cell user clicked on
            if(playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY),
                    cell.x, cell.y))   //vertical if left click, which is MouseButton.PRIMARY
            {
                if(--shipsToPlace == 0)   //decrease ships number and check if zero
                {
                    startGame();         //start the game when user placed all the ships
                }
            }
        });

        VBox vbox = new VBox(50, enemyBoard, playerBoard);  //50 pixels of space inbetween
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
        while(enemyTurn)
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
            System.out.println("Im sorry you lose");
            String status = "You lose :(" ;
            closeProgram(status);
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

            if(enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y))
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

    Stage window;
    Scene scene1, scene2;
    Scene scene3 = new Scene(createContent());   //content for scene is created
    @Override
    public void start(Stage primaryStage)
    {

        window = primaryStage;
        //Button 1
        Label label1 = new Label("Welcome to the Battleship Game!");
        Button button1 = new Button("Instructions");
        button1.setOnAction(e -> window.setScene(scene2));

        //Button S
        Button buttons = new Button("Start Game");
        buttons.setOnAction(e -> window.setScene(scene3));


        //Layout 1 - children laid out in vertical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1, buttons);
        scene1 = new Scene(layout1, 400, 400);
        scene1.getStylesheets().add("sample/Battleship/BattleshipTheme.css");

        //Button 2
        Label label2 = new Label("Instructions \n \n This is a typical Battleship game with the main goal" +
                "\n being to sink all of the enemy ships before yours are" +
                "\n sunk." +
                "\n \n In this game, the bottom interface is your field while" +
                "\n the top interface is the enemy's." +
                "\n \n In order to place your ships, simply click on the bottom" +
                "\n interface. To orientate your ships vertically, left click" +
                "\n and to orientate them horizontally, right click." +
                "\n \n In this version of the game, whenever a hit is made, " +
                "\n the player that made the shot gets to shoot again. " +
                "\n This means if the player successfully hits the AI," +
                "\n the player gets to shoot until they miss without any " +
                "\n fear of being hit themselves and vice versa for the AI");

        Button button2 = new Button("Back");
        button2.setOnAction(e -> window.setScene(scene1));

        //Layout 2
        VBox layout2 = new VBox(20);
        //includes all the
        layout2.getChildren().addAll(label2, button2);
        //sets the parameters of scene 2
        scene2 = new Scene(layout2, 450, 400);


        //Displays scene 1 at first
        window.setScene(scene1);

        window.setTitle("Battleship");        //title for window
        window.setScene(scene1);               //set default scene
        //window.setResizable(false);           //user can't resize the window
        window.show();//displays the window

        scene3.getStylesheets().add("sample/Battleship/BattleshipTheme.css");




    }

    private void closeProgram(String status){
        Boolean answer = ConfirmBox.display(status, "Do you want to exit?");
        if(answer)
            System.exit(0);
    }

    /**
     * launches the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
