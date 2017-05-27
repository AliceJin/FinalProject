package sample.Alice.Alice_Tutorial17;/**
 * Created by jinq7372 on 5/26/2017.
 */

/**
 * All imports related to java
 */
import java.util.Random;                //random numbers

/**
 * All imports related to javafx
 */
import javafx.application.Application;
import javafx.geometry.Pos;             //
import javafx.scene.Parent;             //sets up a Parent class
import javafx.scene.Scene;              //scene for display
import javafx.scene.input.MouseButton;  //
import javafx.scene.layout.BorderPane;  //
import javafx.scene.layout.VBox;        //boxes placed vertically
import javafx.scene.text.Text;          //
import javafx.stage.Stage;

import sample.Alice.Alice_Tutorial17.Alice_Board17.Cell;     //import Cell from the Board program

/**
 * Class equivalent to BattleshipMain in the YouTube tutorial
 */
public class Alice_B2.java extends Application{

/**
 * variables
 */

private boolean running = false;      //false is user still placing ships, true if user is playing the game
private Alice_Board17 enemyBoard, playerBoard;    //enemy and player boards

private int shipsToPlace = 5;         //lets user place 5 ships
private boolean enemyTurn = false;    //whether it is enemy's turn

private Random random = new Random();  //alternative to Math.random()

/**
 * launches the application
 */
public static void main(String[]args)
        {
        launch(args);
        }

/**
 * Basic setup and layout for the window
 */

@Override
public void start(Stage primaryStage)
        {
            Scene scene = new Scene(createContent());
        }
        }
