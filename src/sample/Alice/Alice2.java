package sample;/**
 * Created by jinq7372 on 5/18/2017.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Alice2 extends Application {

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Battleship - Alice");

        button = new Button();
        button.setText("Click me!");

        //layout
        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        //scene
        Scene scene1 = new Scene(layout, 300, 250);
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
