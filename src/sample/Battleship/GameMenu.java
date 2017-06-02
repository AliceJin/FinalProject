package sample.Battleship;/**
 * Created by lie5571 on 6/2/2017.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class GameMenu extends Application {

    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;

        //Button 1
        Label label1 = new Label("Welcome to the Battleship Game!");
        Button button1 = new Button("Instructions");
        button1.setOnAction(e -> window.setScene(scene2));

        //Layout 1 - children laid out in vertical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
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
        VBox layout2 = new VBox(30);
        layout2.getChildren().addAll(label2, button2);
        scene2 = new Scene(layout2, 450, 400);

        //Display scene 1 at first
        window.setScene(scene1);
        window.setTitle("Battleship");
        window.show();

    }
}
