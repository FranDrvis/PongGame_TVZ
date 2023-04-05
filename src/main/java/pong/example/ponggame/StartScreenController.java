package pong.example.ponggame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class StartScreenController {

    public Button loadButton;
    public Button startButton;
    @FXML
    private Button exitButton;
    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @FXML
    private void handleStartGame() {
        try {
            // Load PongGame.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PongGame.fxml"));
            Parent root = fxmlLoader.load();

            // Set the gameController reference
            gameController = fxmlLoader.getController();
            gameController.handleNewGame();

            // Set the new scene for the current stage
            Stage currentStage = (Stage) startButton.getScene().getWindow();
            currentStage.setScene(new Scene(root, 1000, 582.5));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoadGame(ActionEvent event) {
        loadGameScreen(true, event);
    }

    private void loadGameScreen(boolean loadSavedGame, ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PongGame.class.getResource("PongGame.fxml"));
            Scene gameScene = new Scene(fxmlLoader.load(), 1000, 582.5);
            GameController gameController = fxmlLoader.getController();
            if (loadSavedGame) {
                gameController.handleLoadGame();
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleExitGame() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
