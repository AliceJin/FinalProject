package sample.Alice;/**
 * Created by jinq7372 on 5/22/2017.
 */

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;


public class Alice3 extends Application {

    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;

        Label label1 = new Label("Welcome to first scene");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));
        // use lamba to move from scene 1 to scene 2

        //layout - children laid out in vertical column
        VBox layout1 = new VBox(20);  //spaced out by 20 pixels
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 200, 200);   //how many pixels

        //button 2
        Button button2 = new Button("lalalalalala Go to scene 1");
        button1.setOnAction(e -> window.setScene(scene1));

        //layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 600, 300);

        //beginning scene
        window.setScene(scene1);
        window.setTitle("Title");
        window.show();
    }
}
