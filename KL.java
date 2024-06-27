package kursJava.Intersection_Simulation;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KL extends KeyAdapter implements KeyListener {

    public boolean keyPressed = false;
    public int pressedChar;

    @Override
    public void keyPressed(KeyEvent e) {
        this.keyPressed = true;
        this.pressedChar = e.getKeyCode();
    }
    @Override
    public void keyReleased(KeyEvent e) {
        this.keyPressed = false;
        this.pressedChar = -1;
    }
}
