package sample.Alice;/**
 * Created by jinq7372 on 5/24/2017.
 */

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

//doesn't work
public class Alice_B1 extends Application {

    Stage window;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        GridPane gridpane = new GridPane();
        /*
        Group root = new Group();
        Canvas canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image box = new Image("light_blue.jpg");
        gc.drawImage(box, 100, 100);
        */

        Image box = new Image("file:///c:/Users/jinq7372/Downloads/blue.png");
        ImageView boxedit = new ImageView();
        boxedit.setImage(box);
        boxedit.setFitWidth(20);
        boxedit.setFitHeight(20);
        Button button1 = new Button();
        button1.setGraphic(new ImageView(box));
        Button button2 = new Button("Button 2");
        Button button3 = new Button("Button 3");
        Button button4 = new Button("Button 4");
        Button button5 = new Button("Button 5");
        Button button6 = new Button("Button 6");

        gridpane.add(button1, 0, 0, 1, 1);
        gridpane.add(button2, 1, 0, 1, 1);
        gridpane.add(button3, 2, 0, 1, 1);
        gridpane.add(button4, 0, 1, 1, 1);
        gridpane.add(button5, 1, 1, 1, 1);
        gridpane.add(button6, 2, 1, 1, 1);

        /*
        Button button1 = new Button("B");
        GridPane.setRowIndex(button1, 0);
        GridPane.setColumnIndex(button1, 1);
        Label label = new Label("label");
        GridPane.setConstraints(label,2,0);
        gridpane.getChildren().addAll(button1, label);

        button1.setOnAction(e -> System.out.println("hi"));

        StackPane layout = new StackPane();
        layout.getChildren().addAll(gridpane,button1,label);
        */

        Scene scene1 = new Scene(gridpane, 500, 500);
        //Display scene 1 at first
        window.setScene(scene1);
        window.setTitle("Title Here");
        window.show();
    }
}
