package sample.Alice;/**
 * Created by jinq7372 on 5/18/2017.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Alice2 extends Application implements EventHandler <ActionEvent> {

    //user event - implement interface
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Battleship - Alice");

        button = new Button();
        button.setText("Click me!");

        //method to handle event is in this class
        button.setOnAction(this);

        //layout
        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        //scene
        Scene scene1 = new Scene(layout, 300, 250);
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    //what happens when click button
    @Override
    public void handle(ActionEvent event) {
        //find source to distinguish between buttons
        if(event.getSource() == button)
        {
            System.out.println("hiiiii");
        }
    }
}
