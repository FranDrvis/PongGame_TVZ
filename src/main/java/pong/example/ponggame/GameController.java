package pong.example.ponggame;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.*;

import javafx.event.ActionEvent;

public class GameController {
    private static final int GAME_WIDTH = 1000;
    private static final int GAME_HEIGHT = (int) (GAME_WIDTH * 0.5555);
    @FXML
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private Paddle paddle1;
    private Paddle paddle2;
    private Ball ball;
    private Score score;
    private AnimationTimer gameLoop;
    private boolean gamePaused = true;

    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();
        paddle1 = new Paddle(0, ((double) GAME_HEIGHT / 2) - ((double) Paddle.PADDLE_HEIGHT / 2), Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - Paddle.PADDLE_WIDTH, ((double) GAME_HEIGHT / 2) - ((double) Paddle.PADDLE_HEIGHT / 2), Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT, 2);
        ball = new Ball((GAME_WIDTH / 2) - (Ball.BALL_DIAMETER / 2), (GAME_HEIGHT / 2) - (Ball.BALL_DIAMETER / 2), Ball.BALL_DIAMETER, Ball.BALL_DIAMETER);
        score = new Score(GAME_WIDTH);

        // Set up key event handlers
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPressed);
        gameCanvas.setOnKeyReleased(this::handleKeyReleased);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                checkCollision();
                draw();
            }
        };
//        gameLoop.start();
        if (!gamePaused) {
            gameLoop.start();
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        paddle1.keyPressed(event);
        paddle2.keyPressed(event);
    }

    private void handleKeyReleased(KeyEvent event) {
        paddle1.keyReleased(event);
        paddle2.keyReleased(event);
    }

    private void update() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        // Bounce ball off top & bottom window edges
        if (ball.getY() <= 0) {
            ball.setYDirection(-ball.getYVelocity());
        }
        if (ball.getY() >= GAME_HEIGHT - Ball.BALL_DIAMETER) {
            ball.setYDirection(-ball.getYVelocity());
        }
        // Bounce ball off paddles
        if (ball.getBoundsInParent().intersects(paddle1.getBoundsInParent())) {
            ball.setXVelocity(Math.abs(ball.getXVelocity()));
            ball.setXVelocity(ball.getXVelocity() + 1); // Optional for more difficulty
            if (ball.getYVelocity() > 0) {
                ball.setYVelocity(ball.getYVelocity() + 1); // Optional for more difficulty
            } else {
                ball.setYVelocity(ball.getYVelocity() - 1);
            }
            ball.setXDirection(ball.getXVelocity());
            ball.setYDirection(ball.getYVelocity());
        }
        if (ball.getBoundsInParent().intersects(paddle2.getBoundsInParent())) {
            ball.setXVelocity(Math.abs(ball.getXVelocity()));
            ball.setXVelocity(ball.getXVelocity() + 1); // Optional for more difficulty
            if (ball.getYVelocity() > 0) {
                ball.setYVelocity(ball.getYVelocity() + 1); // Optional for more difficulty
            } else {
                ball.setYVelocity(ball.getYVelocity() - 1);
            }
            ball.setXDirection(-ball.getXVelocity());
            ball.setYDirection(ball.getYVelocity());
        }
        // Stops paddles at window edges
        if (paddle1.getY() <= 0) {
            paddle1.setY(0);
        }
        if (paddle1.getY() >= (GAME_HEIGHT - Paddle.PADDLE_HEIGHT)) {
            paddle1.setY(GAME_HEIGHT - Paddle.PADDLE_HEIGHT);
        }
        if (paddle2.getY() <= 0) {
            paddle2.setY(0);
        }
        if (paddle2.getY() >= (GAME_HEIGHT - Paddle.PADDLE_HEIGHT)) {
            paddle2.setY(GAME_HEIGHT - Paddle.PADDLE_HEIGHT);
        }
        // Give a player 1 point and create new paddles & ball
        if (ball.getX() <= 0) {
            score.player2++;
            paddle1 = new Paddle(0, ((double) GAME_HEIGHT / 2) - ((double) Paddle.PADDLE_HEIGHT / 2), Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT, 1);
            ball = new Ball((GAME_WIDTH / 2) - (Ball.BALL_DIAMETER / 2), (GAME_HEIGHT / 2) - (Ball.BALL_DIAMETER / 2), Ball.BALL_DIAMETER, Ball.BALL_DIAMETER);
            System.out.println("Player 2(Red) score: " + score.player2);
        }
        if (ball.getX() >= GAME_WIDTH - Ball.BALL_DIAMETER) {
            score.player1++;
            paddle2 = new Paddle(GAME_WIDTH - Paddle.PADDLE_WIDTH, ((double) GAME_HEIGHT / 2) - ((double) Paddle.PADDLE_HEIGHT / 2), Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT, 2);
            ball = new Ball((GAME_WIDTH / 2) - (Ball.BALL_DIAMETER / 2), (GAME_HEIGHT / 2) - (Ball.BALL_DIAMETER / 2), Ball.BALL_DIAMETER, Ball.BALL_DIAMETER);
            System.out.println("Player 1(Blue) score: " + score.player1);
        }
    }

    private void draw() {
        gc.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        paddle1.draw(gc);
        paddle2.draw(gc);
        ball.draw(gc);
        score.draw(gc);
    }

    @FXML
    public void handleNewGame() {
        // Reset the game state
        paddle1 = new Paddle(0, ((double) GAME_HEIGHT / 2) - ((double) Paddle.PADDLE_HEIGHT / 2), Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - Paddle.PADDLE_WIDTH, ((double) GAME_HEIGHT / 2) - ((double) Paddle.PADDLE_HEIGHT / 2), Paddle.PADDLE_WIDTH, Paddle.PADDLE_HEIGHT, 2);
        ball = new Ball((GAME_WIDTH / 2) - (Ball.BALL_DIAMETER / 2), (GAME_HEIGHT / 2) - (Ball.BALL_DIAMETER / 2), Ball.BALL_DIAMETER, Ball.BALL_DIAMETER);
        score = new Score(GAME_WIDTH);

        // Resume the game if it was paused
        if (gamePaused) {
            gamePaused = false;
            gameLoop.start();
        }
    }

    @FXML
    private void handleSaveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save-game.ser"))) {
            oos.writeObject(paddle1);
            oos.writeObject(paddle2);
            oos.writeObject(ball);
            oos.writeObject(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLoadGame() {
        // Pause the game before loading
        gameLoop.stop();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save-game.ser"))) {
            paddle1 = (Paddle) ois.readObject();
            paddle2 = (Paddle) ois.readObject();
            ball = (Ball) ois.readObject();
            score = (Score) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // Draw the game state after loading
        draw();

        // Pause the game after loading
        gameLoop.stop();

    }

    @FXML
    private void handlePauseResumeGame(ActionEvent event) {
        gamePaused = !gamePaused;
        if (gamePaused) {
            gameLoop.stop();
        } else {
            gameLoop.start();
        }
    }

    public void handlePauseResumeGame() {
        gamePaused = !gamePaused;
    }

}
