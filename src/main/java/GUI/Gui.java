package GUI;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        Button button = new Button();
        button.setText("TEST");

        button.setOnAction(e -> {
            System.out.println("TEST");
            System.out.printf(e.toString());
        });

        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 800, 600);
        window.setScene(scene);
        window.show();
    }
}
