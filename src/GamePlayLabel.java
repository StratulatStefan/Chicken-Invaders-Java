import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;

public class GamePlayLabel extends GameLabel{
    StateButton backbutton;
    ContentFormat health;
    static ArrayList<DefendImage> life = new ArrayList<DefendImage>();
    ContentFormat score;
    ContentFormat killed;
    ContentFormat munition;
    MunitionImage[] munitions;
    Rocket rocket;
    static Player munitie;
    List<ArrayList<Chicken>> chickens;
    boolean munitionFired = false;
    ContentFormat number;
    ArrayList<String> score_filepaths = new ArrayList<String>();
    DefendImage defend;
    static int scor;
    static int lifes;
    static boolean running = true;
    public GamePlayLabel(){
        setIcon(new ImageIcon(Filepath.background_play));
        InitComponents();
        NumberFilepaths();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(rocket.image, rocket.location.x, rocket.location.y, this);
        g.drawImage(new ImageIcon(number.filepath).getImage(),520,72,this);
        if(defend != null)
            g.drawImage(defend.image,defend.position.x,defend.position.y,this);
        if(munitionFired)
            g.drawImage(munitie.image,munitie.location.x,munitie.location.y,this);
        for(int i = 0;i<chickens.size();i++){
            if(chickens.get(i).size() > 0) {
                for (int j = 0; j < chickens.get(i).size(); j++) {
                    g.drawImage(chickens.get(i).get(j).image, chickens.get(i).get(j).location.x, chickens.get(i).get(j).location.y, this);
                }
            }
        }
        for(int i = 0;i<GamePlayLabel.lifes;i++){
            g.drawImage(new ImageIcon(life.get(i).filepath).getImage(),life.get(i).position.x,life.get(i).position.y,this);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public Thread ListenToShots(){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                munitie = new Player(munitie.filepath, new Point(rocket.location.x + rocket.getWidth() / 4 + 5, rocket.location.y - 65));
                while(munitie.location.y > -munitie.getWidth()) {
                    munitie.location.y -= 5;
                    repaint();
                    try{
                        Thread.sleep(10);
                    }
                    catch(Exception e){
                        System.out.println("exceptie");
                    }
                    for (int i = 0; i < chickens.size(); i++) {
                        for(int j = 0;j<chickens.get(i).size();j++) {
                            if (chickens.get(i).get(j) != null && munitie.intersects(chickens.get(i).get(j))) {
                                scor++;
                                if(chickens.get(0).size() + chickens.get(1).size() + chickens.get(2).size() <= 2){
                                    NextLevel();
                                }
                                number.filepath = Game.filepath.Numbers.get(score_filepaths.indexOf(number.filepath) + 1);
                                if (chickens.get(i).size() > 0)
                                        chickens.get(i).remove(j);
                                munitie = new Player(munitie.filepath, new Point(rocket.location.x + rocket.getWidth() / 4, rocket.location.y - 65));
                                munitionFired = false;
                                return;
                            }
                        }
                    }
                }
            }
        });
    }

    public void AddComponents() {
        remove(playbuttonlabel);
        remove(pausebuttonlabel);
        remove(stopbuttonlabel);
        add(backbutton);
        add(health.getText());
        add(score.getText());
        add(killed.getText());
        add(munition.getText());
        add(rocket);
        add(munitie);
        add(number.getText());
        for (int i = 0; i < munitions.length; i++) {
            add(munitions[i]);
        }
        for (int i = 0; i < chickens.size(); i++) {
            for (int j = 0; j < chickens.get(i).size(); j++) {
                add(chickens.get(i).get(j));
            }
        }
        if (defend != null)
            add(defend);
        for (int i = 0; i < life.size(); i++) {
            add(life.get(i));
        }
    }

    public void InitComponents(){
        chickens = new ArrayList<ArrayList<Chicken>>();
        munitions = new MunitionImage[5];
        life = new ArrayList<DefendImage>();
        this.health = new ContentFormat(Game.filepath.Gameplay.get("health"),new Point(-300,530));
        this.munition = new ContentFormat(Game.filepath.Gameplay.get("munition"),new Point(160,520));
        this.munitions[0] = new MunitionImage(Game.filepath.munition.get("Blue_Fire"),new Point(this.munition.position.x + 460,570));
        this.munitions[1] = new MunitionImage(Game.filepath.munition.get("Red_Fire"),new Point(munitions[0].getX() + munitions[0].getWidth() - 40,570));
        this.munitions[2] = new MunitionImage(Game.filepath.munition.get("Fork"),new Point(munitions[1].getX() + munitions[0].getWidth() - 40,570));
        this.munitions[3] = new MunitionImage(Game.filepath.munition.get("Knife"),new Point(munitions[2].getX() + munitions[0].getWidth() - 40,570));
        this.munitions[4] = new MunitionImage(Game.filepath.munition.get("Dynamite"),new Point(munitions[3].getX() + munitions[0].getWidth() - 40,570));
        this.backbutton = new StateButton(Game.filepath.StateButtons.get("back"),"back",new Point(20 ,10));
        this.score = new ContentFormat(Game.filepath.Gameplay.get("score"),new Point(100,-60));
        this.killed = new ContentFormat(Game.filepath.Gameplay.get("killed"),new Point(50,-10));
    }

    public void NumberFilepaths(){
        for(int i = 0;i<Game.filepath.Numbers.size();i++){
            score_filepaths.add(Game.filepath.Numbers.get(i));
        }
    }

    public static void SaveContext(GamePlayLabel label,int validate){
        HashMap<String,String> map = new HashMap<String,String>();
        int index = 0;
        for(int i = 0; i < label.chickens.size();i++){
            for(int j = 0;j<label.chickens.get(i).size();j++){
                int x = label.chickens.get(i).get(j).location.x;
                int y = label.chickens.get(i).get(j).location.y ;
                map.put(index + "",x + "/" + y);
                index++;
            }
            if(validate == 1) {
                if (i == 0) {
                    while (index < 30) {
                        map.put(index + "", "null");
                        index++;
                    }
                }
                if (i == 1) {
                    while (index < 40) {
                        map.put(index + "", "null");
                        index++;
                    }
                }
                if (i == 2) {
                    while (index < 50) {
                        map.put(index + "", "null");
                        index++;
                    }
                }
            }else if(validate == 2){
                if (i == 0) {
                    while (index < 15) {
                        map.put(index + "", "null");
                        index++;
                    }
                }
                if (i == 1) {
                    while (index < 30) {
                        map.put(index + "", "null");
                        index++;
                    }
                }
                if (i == 2) {
                    while (index < 50) {
                        map.put(index + "", "null");
                        index++;
                    }
                }
            }
        }
        map.put(index + "",label.rocket.location.x + "/" + label.rocket.location.y);
        index++;
        map.put(index + "",GamePlayLabel.lifes +"");
        index++;
        map.put(index + "",label.scor + "");
        index++;
        map.put(index + "",label.munitie.filepath + "");
        index++;
        map.put(index + "",label.rocket.filepath + "");
        Database.SaveContext(map);
    }

    public KeyListener KeyboardListener(){
        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Thread thread = ListenToShots();
                if (running) {
                    int key = e.getKeyCode();
                    if (key == VK_W) {
                        rocket.Move("up");
                        try {
                            Thread.sleep(2);
                        } catch (Exception ex) {
                            System.out.println("Ass");
                        }
                    } else if (key == VK_S) {
                        rocket.Move("down");
                    } else if (key == VK_A) {
                        rocket.Move("left");
                    } else if (key == VK_D) {
                        rocket.Move("right");
                    } else if (key == VK_1 || key == VK_2 || key == VK_3 || key == VK_4 || key == VK_5) {
                        munitie.filepath = munitions[key - VK_0 - 1].filepath;
                    } else if (key == VK_SPACE) {
                        munitionFired = true;
                        thread.start();
                    }
                }else{
                    thread.interrupt();
                    thread = null;
                    running = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        return listener;
    }

    public void NextLevel(){

    }


}

class StartNewGameLabel extends GamePlayLabel{
    Thread firstline;
    Thread secondline;
    public StartNewGameLabel(int resume){
        super();
        InitComponent();
        AddComponents();
        if(resume == 0) {
            addKeyListener(KeyboardListener());
            firstline = Firstline(this);
            secondline = SecondLine(this);
            firstline.start();
            secondline.start();
        }

    }

    public Thread Firstline(GamePlayLabel label){
        firstline = new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 0;
                int y = 0;
                while(true) {
                    String path;
                    Chicken defender;
                    if(chickens.get(0).size() > 0)
                        defender = label.chickens.get(0).get(label.chickens.get(0).size()/2);
                    else return;
                    Point point ;
                    while(label.chickens.get(0).size() > 0 && label.chickens.get(0).get(0).location.x < 1200) {
                        for (int i = label.chickens.get(0).size() - 1; i >= 0; i--) {
                            label.chickens.get(0).get(i).location.x += 50;
                            repaint();
                            try {
                                Thread.sleep(100 / chickens.get(0).size());
                            } catch (Exception eee) {
                                System.out.println("Exceptie eggs");
                            }
                        }
                        x++;
                        if(x % 5 == 0) {
                            path = Chicken.eggs.get(new Random().nextInt(Game.filepath.eggs.size()));
                            point = new Point(new Random().nextInt(1000) + label.chickens.get(0).get(0).location.x, 210);
                            defend = new DefendImage(path, point);
                            defender.Defend(defend,label.rocket);
                            try {
                                Thread.sleep(200);
                            } catch (Exception eee) {
                                System.out.println("Exceptie eggs");
                            }
                        }
                    }
                    try{ Thread.sleep(200); }catch(Exception eee){ System.out.println("Exceptie eggs"); }
                    do{
                        for (int i = 0; i < label.chickens.get(0).size(); i++) {
                            label.chickens.get(0).get(i).location.x -= 50;
                            repaint();
                            try{ Thread.sleep(100/chickens.get(0).size()); }catch(Exception eee){ System.out.println("Exceptie eggs"); }
                        }
                        y++;
                        if(y % 5 == 0) {
                            path = Chicken.eggs.get(new Random().nextInt(Game.filepath.eggs.size()));
                            point = new Point(new Random().nextInt(1000) + label.chickens.get(0).get(0).location.x , 210);
                            defend = new DefendImage(path, point);
                            defender.Defend(defend,label.rocket);
                            try {
                                Thread.sleep(200);
                            } catch (Exception eee) {
                                System.out.println("Exceptie eggs");
                            }
                        }

                    }while(label.chickens.get(0).size() > 0 && label.chickens.get(0).get(label.chickens.get(0).size() - 1).location.x > -200);
                    try{ Thread.sleep(500); }catch(Exception eee){ System.out.println("Exceptie eggs"); }
                }
            }
        });
        return firstline;
    }

    public Thread SecondLine(GamePlayLabel label){
        secondline = new Thread(new Runnable() {
            @Override
            public void run() {
                int totalchickens = label.chickens.get(1).size() + label.chickens.get(2).size();
                while (true) {
                    for (int i = label.chickens.get(1).size() - 1; i >= 0; i--) {
                        label.chickens.get(1).get(i).location.x += 50;
                        repaint();
                    }
                    try {
                        Thread.sleep(5000 / totalchickens);
                    } catch (Exception eee) {
                        System.out.println("exceptiea pului");
                    }
                    for (int i = 0; i < label.chickens.get(1).size(); i++) {
                        label.chickens.get(1).get(i).location.x -= 50;
                        repaint();
                    }
                    try {
                        Thread.sleep(5000 / totalchickens);
                    } catch (Exception eee) {
                        System.out.println("exceptiea pului");
                    }

                    for (int i = label.chickens.get(2).size() - 1; i >= 0; i--) {
                        label.chickens.get(2).get(i).location.x -= 50;
                        repaint();
                    }
                    try {
                        Thread.sleep(5000 / totalchickens);
                    } catch (Exception eee) {
                        System.out.println("exceptiea pului");
                    }
                    for (int i = 0; i < label.chickens.get(2).size(); i++) {
                        label.chickens.get(2).get(i).location.x += 50;
                        repaint();
                    }
                    try {
                        Thread.sleep(5000 / totalchickens);
                    } catch (Exception eee) {
                        System.out.println("exceptiea pului");
                    }
                }
            }
        });
        return secondline;
    }

    public void InitComponent(){
        chickens.add(new ArrayList<Chicken>());
        chickens.add(new ArrayList<Chicken>());
        chickens.add(new ArrayList<Chicken>());
        String munitiefilepath = "";
        String numberfilepath = "";
        Point rocketpoint = null;
        GamePlayLabel.lifes = 4;
        String rocketfilepath = "";
        this.scor = 0;
        rocketpoint = new Point(500, 480);
        munitiefilepath = Game.filepath.munition.get("Fork");
        numberfilepath = Game.filepath.Numbers.get(0);
        Point point;
        ArrayList<String> databasepositions = new Database().GetLvl1Positions();
        for (int i = 0; i < 30; i++) {
            String str = databasepositions.get(i);
            String[] strs = str.split("/");
            point = new Point(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
            chickens.get(0).add(new Chicken(Game.filepath.chickens.get("BabyPlusChicken"), point));
        }
        for (int i = 30; i < 40; i++) {
            String str = databasepositions.get(i);
            String[] strs = str.split("/");
            point = new Point(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
            chickens.get(1).add(new Chicken(Game.filepath.chickens.get("BabyChicken"), point));
        }
        int i;
        for (i = 40; i < 50; i++) {
            String str = databasepositions.get(i);
            String[] strs = str.split("/");
            point = new Point(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
            chickens.get(2).add(new Chicken(Game.filepath.chickens.get("BabyChicken"), point));
        }
        rocketfilepath = databasepositions.get(i);
        System.out.println(databasepositions.get(i));
        this.number = new ContentFormat(numberfilepath, new Point(1200,72));
        this.rocket = new Rocket(rocketfilepath, rocketpoint);
        this.munitie = new Player(munitiefilepath, new Point(rocket.location.x + this.rocket.getWidth() / 4 + 5, rocket.location.y - 65));
        for(i = 0;i<GamePlayLabel.lifes;i++) {
            Point position = new Point();
            position.y = 595;
            if (i == 0) {
                position.x = health.position.x + 80 + 380;
            } else {
                position.x = life.get(i - 1).position.x + 60;
            }
            life.add(new DefendImage(Game.filepath.hearth, position));
        }
    }

    public void NextLevel(){
        firstline.interrupt();
        secondline.interrupt();
        Game.game.WindowSetter(LabelFactory.CreateLabel("between"));
    }

}

class ResumeGameLabel extends GamePlayLabel{
    static int label = 0;
    public ResumeGameLabel(){
        super();
        InitComponent();
        AddComponents();
        addKeyListener(KeyboardListener());
        if(label == 0){
            StartNewGameLabel game = new StartNewGameLabel(1);
            game.Firstline(this).start();
            game.SecondLine(this).start();

        }
        if(label == 1){
            NextLevel game = new NextLevel(1);
            game.Up(this).start();
            game.Left(this).start();
            game.Right(this).start();
        }
    }

    public void InitComponent(){
        chickens.add(new ArrayList<Chicken>());
        chickens.add(new ArrayList<Chicken>());
        chickens.add(new ArrayList<Chicken>());
        String munitiefilepath = "";
        String numberfilepath = "";
        Point rocketpoint = null;
        String rocketfilepath = "";
        HashMap<String,String> map = Database.resumeGame();
        this.scor = Integer.parseInt(map.get("scor"));
        munitiefilepath = map.get("munitie");
        numberfilepath = Game.filepath.Numbers.get(this.scor);
        String[] strings = map.get("rocket").split("/");
        rocketpoint = new Point(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]));
        GamePlayLabel.lifes = Integer.parseInt(map.get("health"));
        Point point;
        for (int i = 0; i < 30; i++) {
            String str = map.get(i + "");
            if(!str.equals("null")){
                String[] strng = str.split("/");
                point = new Point(Integer.parseInt(strng[0]), Integer.parseInt(strng[1]));
                if(label == 0) {
                    chickens.get(0).add(new Chicken(Game.filepath.chickens.get("BabyPlusChicken"), point));
                }
                else if(label == 1){
                    chickens.get(0).add(new Chicken(Game.filepath.chickens.get("NormalChicken"), point));
                }
            }
            else{
                break;
            }
        }
        for (int i = 30; i < 40; i++) {
            String str = map.get(i + "");
            if(!str.equals("null")){
                String[] strng = str.split("/");
                point = new Point(Integer.parseInt(strng[0]), Integer.parseInt(strng[1]));
                if(label == 0) {
                    chickens.get(1).add(new Chicken(Game.filepath.chickens.get("BabyChicken"), point));
                }
                else if(label == 1){
                    chickens.get(1).add(new Chicken(Game.filepath.chickens.get("NormalChicken"), point));
                }
            }
            else{
                break;
            }
        }
        for (int i = 40; i < 50; i++) {
            String str = map.get(i + "");
            if(!str.equals("null")){
                String[] strng = str.split("/");
                point = new Point(Integer.parseInt(strng[0]), Integer.parseInt(strng[1]));
                if(label == 0) {
                    chickens.get(2).add(new Chicken(Game.filepath.chickens.get("BabyChicken"), point));
                }
                else if(label == 1){
                    chickens.get(2).add(new Chicken(Game.filepath.chickens.get("BadassChicken"), point));
                }
            }
            else{
                break;
            }
        }
        rocketfilepath = map.get("rocketfilepath");
        this.number = new ContentFormat(numberfilepath, new Point(1200,72));
        this.rocket = new Rocket(rocketfilepath, rocketpoint);
        this.munitie = new Player(munitiefilepath, new Point(rocket.location.x + this.rocket.getWidth() / 4 + 5, rocket.location.y - 65));
        System.out.println(lifes);
        for(int i = 0;i<GamePlayLabel.lifes;i++) {
            Point position = new Point();
            position.y = 595;
            if (i == 0) {
                position.x = health.position.x + 80 + 380;
            } else {
                position.x = life.get(i - 1).position.x + 60;
            }
            life.add(new DefendImage(Game.filepath.hearth, position));
        }
    }
}


class NextLevel extends GamePlayLabel{
    Thread up;
    Thread left;
    Thread right;
    public NextLevel(int resume){
        super();
        InitComponent();
        AddComponents();
        if(resume == 0) {
            addKeyListener(KeyboardListener());
            up = Up(this);
            left = Left(this);
            right = Right(this);
            up.start();
            left.start();
            right.start();
        }
    }

    public void InitComponent(){
        chickens.add(new ArrayList<Chicken>());
        chickens.add(new ArrayList<Chicken>());
        chickens.add(new ArrayList<Chicken>());
        String munitiefilepath = Game.filepath.munition.get("Fork");
        String numberfilepath = Game.filepath.Numbers.get(0);
        Point rocketpoint = new Point(500, 480);
        this.scor = 0;
        String rocketfilepath = new Database().GetLvl1Positions().get(50);
        Point point;
        this.number = new ContentFormat(numberfilepath, new Point(1200,72));
        this.rocket = new Rocket(rocketfilepath, rocketpoint);
        this.munitie = new Player(munitiefilepath, new Point(rocket.location.x + this.rocket.getWidth() / 4 + 5, rocket.location.y - 65));
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            point = new Point(random.nextInt(1) - 500, random.nextInt(700));
            chickens.get(0).add(new Chicken(Game.filepath.chickens.get("NormalChicken"), point));
        }
        for (int i = 0; i < 10; i++) {
            point = new Point(random.nextInt(1500) + 1000, random.nextInt(700));
            chickens.get(1).add(new Chicken(Game.filepath.chickens.get("NormalChicken"), point));
        }
        int i;
        for (i = 0; i < 5; i++) {
            point = new Point(random.nextInt(1000), random.nextInt(1) - 500);
            chickens.get(2).add(new Chicken(Game.filepath.chickens.get("BadassChicken"), point));
        }
        for(i = 0;i<GamePlayLabel.lifes;i++) {
            Point position = new Point();
            position.y = 595;
            if (i == 0) {
                position.x = health.position.x + 80 + 380;
            } else {
                position.x = life.get(i - 1).position.x + 60;
            }
            life.add(new DefendImage(Game.filepath.hearth, position));
        }

    }

    public void NextLevel(){
        up.interrupt();
        left.interrupt();
        right.interrupt();
        Game.game.WindowSetter(LabelFactory.CreateLabel("between"));
    }

    public Thread Left(GamePlayLabel label){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                int x = 0;
                String path;
                Point point;
                while (true) {
                    Chicken defender = label.chickens.get(0).get(label.chickens.get(0).size() / 2);
                    while (label.chickens.get(0).size() > 0 && label.chickens.get(0).get(0).location.x < 1100) {
                        for (int i = 0; i < label.chickens.get(0).size(); i++) {
                            label.chickens.get(0).get(i).location.x += 10;
                        }
                        this.sleep(800 / 15);
                        x++;
                        if (x % 5 == 0) {
                            path = Chicken.eggs.get(new Random().nextInt(Game.filepath.eggs.size()));
                            point = new Point(new Random().nextInt(1000) + label.chickens.get(0).get(0).location.x, 210);
                            defend = new DefendImage(path, point);
                            defender.Defend(defend, label.rocket);
                            this.sleep(200);
                        }
                    }
                    this.sleep(200);
                    do {
                        for (int i = 0; i < label.chickens.get(0).size(); i++) {
                            label.chickens.get(0).get(i).location.x -= 10;
                        }
                        this.sleep(800 / 15);
                    } while (label.chickens.get(0).size() > 0 && label.chickens.get(0).get(0).location.x > -200);
                    this.sleep(300);
                    x++;
                    if (x % 5 == 0) {
                        path = Chicken.eggs.get(new Random().nextInt(Game.filepath.eggs.size()));
                        point = new Point(new Random().nextInt(1000) + label.chickens.get(0).get(0).location.x, 210);
                        defend = new DefendImage(path, point);
                        defender.Defend(defend, label.rocket);
                        this.sleep(200);
                    }
                }
            }

            public void sleep(int time){
                try {
                    Thread.sleep(time);
                } catch (Exception eee) {
                    System.out.println("Moving Exception");
                }
            }
        });
    }

    public Thread Right(GamePlayLabel label){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    while (label.chickens.get(1).size() > 0 && label.chickens.get(1).get(0).location.x > -200) {
                        for (int i = 0; i < label.chickens.get(1).size(); i++) {
                            label.chickens.get(1).get(i).location.x -= 10;
                        }
                        this.sleep(800 / 15);
                    }
                    this.sleep(500);
                    do {
                        for (int i = 0; i < label.chickens.get(1).size(); i++) {
                            label.chickens.get(1).get(i).location.x += 10;
                        }
                        this.sleep(800 / 15);
                    } while (label.chickens.get(1).size() > 0 && label.chickens.get(1).get(0).location.x < 1100);
                    this.sleep(300);
                }
            }
            public void sleep(int time){
                try {
                    Thread.sleep(time);
                } catch (Exception eee) {
                    System.out.println("Moving Exception");
                }
            }
        });
    }

    public Thread Up(GamePlayLabel label){
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    while (label.chickens.get(2).size() > 0 && label.chickens.get(2).get(0).location.y < 900) {
                        for (int i = 0; i < label.chickens.get(2).size(); i++) {
                            label.chickens.get(2).get(i).location.y += 10;
                        }
                        this.sleep(800 / 15);
                    }
                    this.sleep(500);
                    do {
                        for (int i = 0; i < label.chickens.get(2).size(); i++) {
                            label.chickens.get(2).get(i).location.y -= 10;
                        }
                        this.sleep(800 / 15);
                    } while (label.chickens.get(2).size() > 0 && label.chickens.get(2).get(0).location.y > -200);
                    this.sleep(300);
                }
            }

            public void sleep(int time){
                try {
                    Thread.sleep(time);
                } catch (Exception eee) {
                    System.out.println("Moving Exception");
                }
            }
        });
    }

}
