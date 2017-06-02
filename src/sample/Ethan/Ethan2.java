package sample.Ethan;

/**
 * Created by jinq7372 on 5/18/2017.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Ethan2 extends Application {

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Battleship");
        button = new Button();
        button.setText("Start Game");

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 450, 375);

        primaryStage.setScene(scene);
        scene.getStylesheets().add("sample/Ethan/Theme.css");
        primaryStage.show();
    }

}
