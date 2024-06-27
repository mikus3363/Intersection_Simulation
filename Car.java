package kursJava.Intersection_Simulation;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

public class Car {
    protected int x, y, w = 100, h = 60;
    protected Color colorBody;
    protected Color colorWheel = Color.black;
    protected States curState;
    protected States nextState;
    protected boolean isTurnable;
    public boolean inCenter = false;
    private boolean isControlled = false;
    public boolean stopCar = false;
    protected ArrayList<Shape> carBounds = new ArrayList<>();
    public Rectangle body = new Rectangle(x, y, w, h);
    public Ellipse2D frontWheel = new Ellipse2D.Double(x + 55, y + 40, 40, 40);
    public Ellipse2D rearWheel = new Ellipse2D.Double(x + 5, y + 40, 40, 40);



    public Car(int x, int y, Color colorBody, States curState) {
        this.x = x;
        this.y = y;
        this.colorBody = colorBody;
        this.curState = curState;
        this.nextState = random();

        this.carBounds.add(body);
        this.carBounds.add(frontWheel);
        this.carBounds.add(rearWheel);

    }
    public void updateCarBounds(int x, int y) {
        this.carBounds.clear();
        this.body = new Rectangle(this.x, this.y, 100, 60);
        this.frontWheel = new Ellipse2D.Double(this.x + 55, this.y + 40, 40, 40);
        this.rearWheel = new Ellipse2D.Double(this.x + 5, this.y + 40, 40, 40);
        this.carBounds.add(body);
        this.carBounds.add(frontWheel);
        this.carBounds.add(rearWheel);
    }
    public boolean getIsControlled() {
        return this.isControlled;
    }
    public void setIsControlled(boolean isControlled) {
        this.isControlled = isControlled;
    }
    public void stopCar() {
        this.stopCar = true;
    }
    public void runCar() {
        this.stopCar = false;
    }

    public int getW() {
        return w;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return colorBody;
    }

    public void setColor(Color color) {
        this.colorBody = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public void drawCar(Graphics2D g2d) {
        g2d.setColor(colorBody);
        g2d.fillRect(x, y, w, h);
        g2d.setColor(colorWheel);
        g2d.fillOval(x + 5, y + 40, 40, 40);
        g2d.fillOval(x + 55, y + 40, 40, 40);
    }
    public void setCurrentState(States curState) {
        this.curState = curState;
    }

    public States getNextState() {
        return nextState;
    }
    public States getCurState() {
        return curState;
    }

    public void setNextState(States nextState) {
        this.nextState = nextState;
    }

    public States currentState() {
        if (this.x == 540 && (this.y < 170 || this.y > 450)) return States.Down;

        else if (this.x == 640 && (this.y < 250 || this.y > 450)) return States.Up;

        else if (this.y == 250 && (this.x < 440 || this.x > 740)) return States.Left;

        else if (this.y == 350 && (this.x < 440 || this.x > 740)) return States.Right;
        return States.Center;
    }

    public void turnRight(States curState) {
        if (curState.equals(States.Up)) {
            if (this.getY() == 350) {
                this.setCurrentState(States.Right);
                this.isTurnable = false;
            }
        }
        if (curState.equals(States.Left)) {
            if (this.getX() == 640) {
                this.setCurrentState(States.Up);
                this.isTurnable = false;
            }
        }
        if (curState.equals(States.Right)) {
            if (this.getX() == 540) {
                this.setCurrentState(States.Down);
                this.isTurnable = false;
            }
        }
        if (curState.equals(States.Down)) {
            if (this.getY() == 250) {
                this.setCurrentState(States.Left);
                this.isTurnable = false;
            }
        }
    }
    public void turnLeft(States curState) {
        if (curState.equals(States.Up)) {
            if (this.getY() == 250) {
                this.setCurrentState(States.Left);
                this.isTurnable = false;
            }
        }
        if (curState.equals(States.Left)) {
            if (this.getX() == 540) {
                this.setCurrentState(States.Down);
                this.isTurnable = false;
            }
        }
        if (curState.equals(States.Right)) {
            if (this.getX() == 640) {
                this.setCurrentState(States.Up);
                this.isTurnable = false;
            }
        }
        if (curState.equals(States.Down)) {
            if (this.getY() == 350) {
                this.setCurrentState(States.Right);
                this.isTurnable = false;
            }
        }
    }
    public void noJoint(States curState) {
        if (curState.equals(States.Up)) {
            if (this.getY() == 450) {
                this.setCurrentState(States.Up);
            }
        }
        if (curState.equals(States.Left)) {
            if (this.getX() == 740) {
                this.setCurrentState(States.Left);
            }
        }
        if (curState.equals(States.Right)) {
            if (this.getX() == 540) {
                this.setCurrentState(States.Right);
            }
        }
        if (curState.equals(States.Down)) {
            if (this.getY() == 250) {
                this.setCurrentState(States.Down);
            }
        }
    }
    public States random(){
        Random rand = new Random();
        int randomNum = rand.nextInt(3);
        switch (randomNum){
            case 0:
                return States.noJoint;
            case 1:
                return States.Right;
            case 2:
                return States.Left;
            default:
                return null;
        }
    }
}
