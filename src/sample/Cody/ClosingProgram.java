
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Cody.ConfirmBox;

public class ClosingProgram extends Application  {

    Stage window;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        window.setTitle("Battleship");
        window.setOnCloseRequest(e -> closeProgram());

        button = new Button("Close program");
        button.setOnAction(e -> {
            e.consume();
            closeProgram();
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();

    }

    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Title", "Are you sure you want to exit?");
        if(answer)
            window.close();
    }

}
