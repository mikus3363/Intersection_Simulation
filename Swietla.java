package kursJava.Intersection_Simulation;
import java.awt.*;

public class Swietla  {
    protected int x, y, w=40, h=120;
    protected Color color = Color.BLACK;
    protected Color red = Color.DARK_GRAY;
    protected Color yellow = Color.DARK_GRAY;
    protected Color green = Color.DARK_GRAY;
    public boolean isGreen = false;
    public boolean isRed = true;
    public States state;

    public Swietla(int x,int y,States state){
        this.x = x;
        this.y = y;
        this.state = state;
    }
    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void turnRed(){
        this.red = Color.RED;
        this.green = Color.DARK_GRAY;
        this.yellow = Color.DARK_GRAY;
    }
    public void turnYellow(){
        this.red = Color.DARK_GRAY;
        this.green = Color.DARK_GRAY;
        this.yellow = Color.YELLOW;
    }
    public void turnGreen(){
        this.red = Color.DARK_GRAY;
        this.green = Color.GREEN;
        this.yellow = Color.DARK_GRAY;
    }
    public void turnYellowAndRed() {
        this.red = Color.RED;
        this.green = Color.DARK_GRAY;
        this.yellow = Color.YELLOW;
    }

    public void drawSwietla(Graphics2D g2d){
        g2d.setColor(color);
        g2d.fillRect(x,y,w,h);
        g2d.setColor(this.red);
        g2d.fillOval(getX(),getY(),w,h/3);
        g2d.setColor(this.yellow);
        g2d.fillOval(getX(),getY()+40,w,h/3);
        g2d.setColor(this.green);
        g2d.fillOval(getX(),getY()+80,w,h/3);
    }
}
