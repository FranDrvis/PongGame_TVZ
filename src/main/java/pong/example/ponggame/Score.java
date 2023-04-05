package pong.example.ponggame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.Serial;
import java.io.Serializable;

public class Score extends Rectangle implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static int GAME_WIDTH;
    public int player1;
    public int player2;

    public Score(int GAME_WIDTH) {
        Score.GAME_WIDTH = GAME_WIDTH;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Consolas", 60));

        double halfGameWidth = (double) GAME_WIDTH / 2;

        gc.fillText(player1 / 10 + "" + player1 % 10, halfGameWidth - 85, 50);
        gc.fillText(player2 / 10 + "" + player2 % 10, halfGameWidth + 20, 50);

    }
}
