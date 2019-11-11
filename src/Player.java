import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


public class Player extends JLabel {
    String filepath;
    Point location;
    Image image;
    public Player(String filepath, Point location){
        this.location = location;
        this.filepath = filepath;
        image = new ImageIcon(filepath).getImage();
        setSize(new Dimension(90,90));
        setLocation(location.x,location.y);
        setVisible(true);
    }

    public boolean intersects(Chicken chicken){
        boolean conditieX = (chicken.location.x < this.location.x  && chicken.location.x + chicken.getWidth() >= this.location.x);
        boolean conditieY = (chicken.location.y + chicken.getHeight() - 30 >= this.location.y);
        return (conditieX && conditieY);
    }
}

class Rocket extends Player {
    private Thread shoot;
    Point location;
    int speed = 20;
    public Rocket(String filepath, Point location) {
        super(filepath, location);
        this.location = location;
    }

    public void Move(String direction) {
        switch (direction) {
            case "up":
                location.y -= speed;
                break;
            case "down":
                location.y += speed;
                break;
            case "left":
                location.x -= speed;
                break;
            case "right":
                location.x += speed;
                break;
        }
    }

    public void AlmostDie(){
        if(GamePlayLabel.lifes > 0) {
            GamePlayLabel.lifes -= 1;
            System.out.println(GamePlayLabel.lifes + " lifes left!");
            if(GamePlayLabel.life.size() > 0)
                GamePlayLabel.life.remove(GamePlayLabel.life.size() - 1);
        }
        if(GamePlayLabel.lifes == 0) {
            GamePlayLabel.running = false;
            Game.game.WindowSetter(LabelFactory.CreateLabel("the_end"));
        }
    }
}


class Chicken extends Player{
    Point location;
    boolean strike;
    static ArrayList<String> eggs = new ArrayList<String>();
    public Chicken(String filepath,Point location){
        super(filepath,location);
        setSize(new Dimension(80,80));
        this.location = location;
        eggs.add(Game.filepath.eggs.get("EasterEgg"));
        eggs.add(Game.filepath.eggs.get("PoisonedEgg"));
        eggs.add(Game.filepath.eggs.get("Omlette"));
        eggs.add(Game.filepath.eggs.get("EasterTraditionalEgg"));
        eggs.add(Game.filepath.eggs.get("BabyBornEgg"));
        eggs.add(Game.filepath.eggs.get("Shit"));
        eggs.add(Game.filepath.eggs.get("SpecialEgg"));
        eggs.add(Game.filepath.eggs.get("Bicolor"));
        int random = new Random().nextInt(Game.filepath.eggs.size());
    }

    public void Move(){
        this.location.x += 50;
    }

    public boolean Defend(DefendImage defension,Rocket rocket){
        strike = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(defension.position.y < 700 && !strike){
                    defension.position.y += 10;
                    if(defension.Impact(rocket) && location.y < rocket.location.y){
                        strike = true;
                        System.out.println("Impaact");
                        rocket.AlmostDie();
                        return;
                    }
                    if(!strike) {
                        repaint();
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException exceptie) {
                            System.out.println("Exceptieeeeeee");
                        }
                    }
                    else{
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();
        return strike;
    }
}