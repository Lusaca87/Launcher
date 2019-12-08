package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Gui extends Application {

    Stage window;

    public Gui(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Main-Window");

        StackPane layout = new StackPane();
        Scene scene = new Scene(layout, 800, 600);
        window.setScene(scene);
        window.show();
    }
}
