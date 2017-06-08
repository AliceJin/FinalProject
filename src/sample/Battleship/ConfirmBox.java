package sample.Battleship;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import sample.Battleship.BattleshipMain;


/**
 * Created by linc9640 on 6/8/2017.
 */
public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("yes");
        Button noButton = new Button("no");

        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,yesButton,noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}