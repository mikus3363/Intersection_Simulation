package kursJava.Intersection_Simulation;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ML extends MouseAdapter {
    public float x,y;
    public float dx,dy;
    public boolean mousePressed = false;
    public boolean mouseDragged = false;
    @Override
    public void mousePressed(MouseEvent e) {
        this.mousePressed = true;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        this.mousePressed = false;
        this.mouseDragged = false;
        this.dx = 0;
        this.dy = 0;
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }
    @Override
    public void mouseDragged(MouseEvent e){
        this.mouseDragged = true;
        this.dx = e.getX() - this.x;
        this.dy = e.getY() - this.y;
    }
}
