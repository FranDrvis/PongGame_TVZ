package pong.example.ponggame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;

public class Paddle extends Rectangle implements Serializable {

    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    @Serial
    private static final long serialVersionUID = 1L;
    int id;
    int yVelocity;
    int speed = 10;

    public Paddle(double x, double y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.id = id;
    }

    public void draw(GraphicsContext gc) {
        if (id == 1) {
            gc.setFill(Color.BLUE);
        } else {
            gc.setFill(Color.RED);
        }
        gc.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public void keyPressed(KeyEvent e) {
        switch (id) {
            case 1 -> {
                if (e.getCode() == KeyCode.W) {
                    setYDirection(-speed);
                }
                if (e.getCode() == KeyCode.S) {
                    setYDirection(speed);
                }
            }
            case 2 -> {
                if (e.getCode() == KeyCode.UP) {
                    setYDirection(-speed);
                }
                if (e.getCode() == KeyCode.DOWN) {
                    setYDirection(speed);
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (id) {
            case 1 -> {
                if (e.getCode() == KeyCode.W) {
                    setYDirection(0);
                }
                if (e.getCode() == KeyCode.S) {
                    setYDirection(0);
                }
            }
            case 2 -> {
                if (e.getCode() == KeyCode.UP) {
                    setYDirection(0);
                }
                if (e.getCode() == KeyCode.DOWN) {
                    setYDirection(0);
                }
            }
        }
    }

    private void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move() {
        setY(getY() + yVelocity);
    }

    @Serial
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeDouble(getX());
        oos.writeDouble(getY());
        oos.writeDouble(getWidth());
        oos.writeDouble(getHeight());
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        double x = ois.readDouble();
        double y = ois.readDouble();
        double width = ois.readDouble();
        double height = ois.readDouble();
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
}