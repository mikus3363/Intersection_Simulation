package kursJava.Intersection_Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Skrzyzowanie extends JPanel implements MouseListener {
    public Skrzyzowanie() { //konstruktor
        super();

        this.kl = new KL();
        this.addKeyListener(kl);
        createCarListAndLightList();
        initSkrzyzowanie();
        this.addMouseListener(this);
    }
    private Swietla trafficLightRight ;
    private Swietla trafficLightUp ;
    private Swietla trafficLightLeft ;
    private Swietla trafficLightDown ;
    private Car auto1;
    private Car auto2;
    private Car auto3;
    private Car auto4;
    private Car controlledCar;
    private ArrayList<Car> carList;
    private ArrayList<Swietla> lightList;
    private BufferedImage buffer;
    private KL kl;
    private Swietla curTrafficLight;
    private Toolkit tlotoolkit;
    private Image tloimage;
    private int SPEED = 2;
    @Override
    protected void paintComponent(Graphics g) { //malowanie wszystkich rzeczy w programie
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

//        buffer = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2d = buffer.createGraphics();
//        Graphics2D g2d = (Graphics2D) g;
        drawMap(g2d);
        lightList.forEach(trafLight -> trafLight.drawSwietla(g2d));
        //repaint();
        carList.forEach(car -> car.drawCar(g2d));
        repaint();
        //g.drawImage(buffer,0,0,this);
    }
    private void drawMap(Graphics2D g2d){ //rysowanie mapy
        tlotoolkit = Toolkit.getDefaultToolkit();
        tloimage = tlotoolkit.getImage("GraSkrzyzowanie\\background.jpg");
        g2d.drawImage(tloimage,0,0,1280,720,null);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0,250,1280,200);
        g2d.fillRect(540,0,200,720);
        g2d.setColor(Color.WHITE);
        for(int i = 0;i<=1280;i+=100){
            if(i<540 || i>740) g2d.fillRect(i,345,25,5);
        }
        for(int i = 0;i<=720;i+=100){
            if(i<250 || i>450) g2d.fillRect(635,i,5,25);
        }
    }
    private void actionLights() throws InterruptedException { //funkcja działania świateł w programie
        Thread fred = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lightList.forEach(sw -> sw.turnRed());
                    repaint();
                    while (true){
                        for (Swietla curSwietla : lightList){
                            curSwietla.turnYellowAndRed();
                            repaint();
                            Thread.sleep(500);
                            curSwietla.turnGreen();
                            curSwietla.isGreen = true;
                            curSwietla.isRed = false;
                            repaint();
                            Thread.sleep(5000);
                            curSwietla.turnYellow();
                            repaint();
                            Thread.sleep(500);
                            curSwietla.turnRed();
                            curSwietla.isRed = true;
                            curSwietla.isGreen = false;
                            repaint();
                            Thread.sleep(5000);
                        }
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        fred.start();
    }

//    public void initialize(){
//        int width = 1280;
//        int height = 720;
//        if(doublebufferImage==null){
//            doublebufferImage = createImage(width, height);
//            doublebufferGraphic = (Graphics2D) doublebufferImage.getGraphics();
//            device = (Graphics2D) getGraphics();
//        }
//        doublebufferGraphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//        device.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//    }


    private void actionMoveCar(Car auto){ //funkcja ruchu aut
        auto.isTurnable = true;
        Thread move = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (auto.curState.equals(States.Down) && !auto.stopCar) {
                        auto.setY(auto.getY() +SPEED);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (auto.getY() == 720) {
                            auto.setX(640);
                            auto.setY(790);
                            auto.setCurrentState(States.Up);
                            auto.isTurnable = true;
                            auto.nextState = auto.random();
                            auto.inCenter = false;
                        }
                    }
                    if (auto.curState.equals(States.Up)&& !auto.stopCar) {
                        auto.setY(auto.getY() - SPEED);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (auto.getY() == -220) {
                            auto.setX(540);
                            auto.setY(-200);
                            auto.setCurrentState(States.Down);
                            auto.isTurnable = true;
                            auto.nextState = auto.random();
                            auto.inCenter = false;
                        }
                    }
                    if (auto.curState.equals(States.Right) && !auto.stopCar) {
                        auto.setX(auto.getX() + SPEED);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (auto.getX() == 1280) {
                            auto.setX(1400);
                            auto.setY(250);
                            auto.setCurrentState(States.Left);
                            auto.isTurnable = true;
                            auto.nextState = auto.random();
                            auto.inCenter = false;
                        }
                    }
                    if (auto.curState.equals(States.Left) && !auto.stopCar) {
                        auto.setX(auto.getX() - SPEED);
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (auto.getX() == -200) {
                            auto.setX(-150);
                            auto.setY(350);
                            auto.setCurrentState(States.Right);
                            auto.isTurnable = true;
                            auto.nextState = auto.random();
                            auto.inCenter = false;
                        }
                    }
                    carReactsToTrafficLight(auto);
                    auto.updateCarBounds(auto.getX(),auto.getY());
                }
            }
        });
        move.start();
    }
    private Color randomColor() { //losuje kolor i zwraca nowy
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        return new Color(r, g, b);
    }

    private void checkIfPressedKey() { //kontrola przycisków
        Thread fred2 = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean spawnCar = true;
                while (true) {
                    if (kl.pressedChar == KeyEvent.VK_ENTER && spawnCar) {
                        spawnCar = false;
                        controlledCar = new Car(1160,350,Color.PINK,States.noJoint);
                        controlledCar.setIsControlled(true);
                        carList.add(controlledCar);
                    }
                    if (!spawnCar && kl.pressedChar == KeyEvent.VK_LEFT) {
                        controlledCar.setX(controlledCar.getX() - SPEED);
                    }
                    if (!spawnCar && kl.pressedChar == KeyEvent.VK_UP) {
                        controlledCar.setY(controlledCar.getY() - SPEED);
                    }
                    if (!spawnCar && kl.pressedChar == KeyEvent.VK_RIGHT) {
                        controlledCar.setX(controlledCar.getX() + SPEED);
                    }
                    if (!spawnCar && kl.pressedChar == KeyEvent.VK_DOWN) {
                        controlledCar.setY(controlledCar.getY() + SPEED);
                    }
                    if (kl.pressedChar == KeyEvent.VK_A) { //A
                        carList.forEach(Car::stopCar);
                    }
                    if (kl.pressedChar == KeyEvent.VK_S) { //S
                        carList.forEach(Car::runCar);
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        fred2.start();
    }
    private Swietla getTrafficLightByState(States state) { //zwraca stan światła które chcemy sprawdzić
        if (state.equals(States.Up)) return trafficLightUp;
        if (state.equals(States.Down)) return trafficLightDown;
        if (state.equals(States.Left)) return trafficLightLeft;
        if (state.equals(States.Right)) return trafficLightRight;
        return trafficLightUp;
    }
    private void carReactsToTrafficLight(Car auto) { //działanie na skrzyżowaniu
        if (auto.currentState().equals(States.Center) && auto.isTurnable) {
            curTrafficLight = getTrafficLightByState(auto.curState);  //DODANE
            if (curTrafficLight.isGreen || auto.inCenter) {
                auto.runCar();
                if (auto.nextState.equals(States.Right)) {
                    auto.turnRight(auto.curState);
                }
                if (auto.nextState.equals(States.Left)) {
                    auto.turnLeft(auto.curState);
                }
                if (auto.nextState.equals(States.noJoint)) {
                    auto.noJoint(auto.curState);
                }
                auto.inCenter = true;
            }
            else {
                auto.stopCar();
            }
        }
    }
    private void createCarListAndLightList(){ //dodawanie do listy świateł i aut danych obiektów
        carList = new ArrayList<>();
        lightList = new ArrayList<>();
        createCarsAndLights();
        lightList.add(trafficLightRight);
        lightList.add(trafficLightUp);
        lightList.add(trafficLightLeft);
        lightList.add(trafficLightDown);
        carList.add(auto1);
        carList.add(auto2);
        carList.add(auto3);
        carList.add(auto4);
    }
    private void createCarsAndLights() { //funkcja do dodawania i tworzenia obiektów świateł i aut
        trafficLightRight = new Swietla(450,450,States.Right);
        trafficLightUp = new Swietla(785,450,States.Up);
        trafficLightLeft = new Swietla(785,130,States.Left);
        trafficLightDown = new Swietla(450,130,States.Down);

        auto1 = new Car(540,10,Color.RED, States.Down);
        auto2 = new Car(0,350,Color.BLUE, States.Right);
        auto3 = new Car(640,700,Color.RED,States.Up);
        auto4 = new Car(1160,250,Color.CYAN, States.Left);

    }
    private void checkNextCar(ArrayList<Car> carList){//koreczek
        Thread fred3 = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean stopped = false;
                while (true) {
                    for(int i=0;i<carList.size();i++){
                        for(int j=0;j<carList.size();j++){
                            if (i != j) {
                                if(carList.get(i).getCurState()==States.Left && carList.get(j).getCurState()==States.Left){
                                    if(carList.get(i).getX()<carList.get(j).getX()){
                                        if(carList.get(i).getX()+120==carList.get(j).getX() || carList.get(i).getX()+119==carList.get(j).getX() || carList.get(i).getX()+121==carList.get(j).getX()){
                                            carList.get(j).stopCar=true;
                                            stopped=true;
                                        }
                                        if(stopped && carList.get(i).getX()+120<carList.get(j).getX()){
                                            carList.get(j).stopCar=false;
                                            stopped=false;
                                        }

                                    }
                                    else{
                                        if(carList.get(i).getX()==carList.get(j).getX()+120 || carList.get(i).getX()==carList.get(j).getX()+119 || carList.get(i).getX()==carList.get(j).getX()+121){
                                            carList.get(i).stopCar=true;
                                            stopped=true;
                                        }
                                        if(stopped && carList.get(i).getX()>carList.get(j).getX()+120){
                                            carList.get(i).stopCar=false;
                                            stopped=false;
                                        }
                                    }
                                }
                                if(carList.get(i).getCurState()==States.Right && carList.get(j).getCurState()==States.Right){
                                    if(carList.get(i).getX()<carList.get(j).getX()){
                                        if(carList.get(i).getX()+120==carList.get(j).getX() || carList.get(i).getX()+119==carList.get(j).getX() || carList.get(i).getX()+121==carList.get(j).getX()){
                                            carList.get(i).stopCar=true;
                                            stopped = true;
                                        }
                                        if(stopped && carList.get(i).getX()+120<carList.get(j).getX()){
                                            carList.get(i).stopCar=false;
                                            stopped=false;
                                        }
                                    }
                                    else{
                                        if(carList.get(i).getX()==carList.get(j).getX()+120 || carList.get(i).getX()==carList.get(j).getX()+119 || carList.get(i).getX()==carList.get(j).getX()+121){
                                            carList.get(j).stopCar=true;
                                            stopped=true;
                                        }
                                        if(stopped && carList.get(i).getX()>carList.get(j).getX()+120){
                                            carList.get(j).stopCar=false;
                                            stopped=false;
                                        }
                                    }
                                }
                                if(carList.get(i).getCurState()==States.Up && carList.get(j).getCurState()==States.Up){
                                    if(carList.get(i).getY()<carList.get(j).getY()){
                                        if(carList.get(i).getY()+90==carList.get(j).getY() || carList.get(i).getY()+89==carList.get(j).getY() || carList.get(i).getY()+91==carList.get(j).getY()){
                                            carList.get(j).stopCar=true;
                                            stopped=true;
                                        }
                                        if(stopped && carList.get(i).getY()+90>carList.get(j).getY()){
                                            carList.get(j).stopCar=false;
                                            stopped=false;
                                        }
                                    }
                                    else{
                                        if(carList.get(i).getY()==carList.get(j).getY()+90 || carList.get(i).getY()==carList.get(j).getY()+89 || carList.get(i).getY()==carList.get(j).getY()+91){
                                            carList.get(i).stopCar=true;
                                            stopped=true;
                                        }
                                        if(stopped && carList.get(i).getY()<carList.get(j).getY()+90){
                                            carList.get(i).stopCar=false;
                                            stopped=false;
                                        }
                                    }
                                }
                                if(carList.get(i).getCurState()==States.Down && carList.get(j).getCurState()==States.Down){
                                    if(carList.get(i).getY()<carList.get(j).getY()){
                                        if(carList.get(i).getY()+90==carList.get(j).getY() || carList.get(i).getY()+89==carList.get(j).getY() || carList.get(i).getY()+91==carList.get(j).getY()){
                                            carList.get(i).stopCar=true;
                                            stopped = true;
                                        }
                                        if(stopped && carList.get(i).getY()+90<carList.get(j).getY()){
                                            carList.get(i).stopCar=false;
                                            stopped = false;
                                        }
                                    }
                                    else{
                                        if(carList.get(i).getY()==carList.get(j).getY()+90 || carList.get(i).getY()==carList.get(j).getY()+89 || carList.get(i).getY()==carList.get(j).getY()+91){
                                            carList.get(j).stopCar=true;
                                            stopped=true;
                                        }
                                        if(stopped && carList.get(i).getY()>carList.get(j).getY()+90){
                                            carList.get(j).stopCar=false;
                                            stopped=false;
                                        }
                                    }
                                }
                                else {
                                    carReactsToTrafficLight(carList.get(i));
                                }
                            }
                        }
                    }
                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        fred3.start();
    }
    private void initSkrzyzowanie() { //inicjalizowanie skrzyżowania
        for (Car car: carList) {
            if (!car.getIsControlled()) actionMoveCar(car);
        }
        try {
            actionLights();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        checkNextCar(carList);
        //checkIfPressedKey();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(Car car:carList) {
            if (e.getX() >= car.getX() && e.getX() <= car.getX() + car.getWidth() && e.getY() >= car.getY() && e.getY() <= car.getY() + car.getHeight()) {
                car.setColor(randomColor());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}


