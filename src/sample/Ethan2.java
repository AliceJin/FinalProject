package sample;/**
 * Created by jinq7372 on 5/18/2017.
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class Ethan2 extends Application {

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Title of the Window");
        button = new Button();
        button.setText("Click me");
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
