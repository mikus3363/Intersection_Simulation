package kursJava.Intersection_Simulation;

import javax.swing.*;

public class Window extends JFrame {
    private Skrzyzowanie panel;
    public Window(){
        initComponents();
    }
    public void initComponents(){

        panel = new Skrzyzowanie();
        this.getContentPane().add(panel);
        panel.setLayout(null);
        panel.setFocusable(true);

        this.setTitle("Intersection");
        this.setSize(1280,720);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window().setVisible(true);
            }
        });
    }
}
