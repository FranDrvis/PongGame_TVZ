package pong.example.ponggame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.Random;

public class Ball extends Rectangle implements Serializable {

    public static final int BALL_DIAMETER = 20;
    @Serial
    private static final long serialVersionUID = 1L;
    private int xVelocity;
    private int yVelocity;

    Ball(int x, int y, int width, int height) {
        super(x, y, BALL_DIAMETER, BALL_DIAMETER);
        Random random = new Random();
        int randomXDirection = random.nextInt(2);
        if (randomXDirection == 0)
            randomXDirection--;
        int initialSpeed = 2;
        setXDirection(randomXDirection * initialSpeed);

        int randomYDirection = random.nextInt(2);
        if (randomYDirection == 0)
            randomYDirection--;
        setYDirection(randomYDirection * initialSpeed);
    }

    public void setXDirection(int randomXDirection) {
        xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {
        yVelocity = randomYDirection;
    }

    public int getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public void move() {
        setX(getX() + xVelocity);
        setY(getY() + yVelocity);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(getX(), getY(), BALL_DIAMETER, BALL_DIAMETER);
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
