package sample;/**
 * Created by jinq7372 on 5/18/2017.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Cody.Cats.ConfirmBox;

public class Cody4 extends Application  {

    Stage window;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("thenewboston");

        button = new Button("Click me");

        button.setOnAction(event -> {
            boolean result = ConfirmBox.display("Title of Window", "Are you sure you want to restart?");
            System.out.println(result);
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();

    }


}
