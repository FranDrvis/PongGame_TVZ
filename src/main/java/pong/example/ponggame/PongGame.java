package pong.example.ponggame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.scene.layout.Pane;

public class PongGame extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the PongGame.fxml
        FXMLLoader pongGameLoader = new FXMLLoader(PongGame.class.getResource("PongGame.fxml"));
        Pane pongGamePane = pongGameLoader.load();
        GameController gameController = pongGameLoader.getController();

        // Load the StartScreen.fxml
        FXMLLoader startScreenLoader = new FXMLLoader(PongGame.class.getResource("StartScreen.fxml"));
        Pane startScreenPane = startScreenLoader.load();
        StartScreenController startScreenController = startScreenLoader.getController();
        startScreenController.setGameController(gameController);

        // Set up the primaryStage with the StartScreen.fxml
        primaryStage.setTitle("Pong Game");
        primaryStage.setScene(new Scene(startScreenPane, 1000, 582.5));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
