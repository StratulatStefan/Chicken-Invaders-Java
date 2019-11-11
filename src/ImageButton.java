import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ImageButton extends JButton {
    String filepath;
    ImageButton(String filepath){
        this.filepath = filepath;
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        try{
            setIcon(new ImageIcon(this.filepath));
        }
        catch(Exception ex){
            System.out.println("Image Button Exception : " + ex.getMessage());
        }
    }
}

class RocketImage extends ImageButton{
    String filepath;
    RocketImage(String filepath,Point location){
        super(filepath);
        this.filepath = filepath;
        setSize(new Dimension(150,150));
        setLocation(location.x,location.y);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database.AddRocketToDb(filepath);
                System.out.println("Filepath changed");
            }
        });
    }
}

class MunitionImage extends ImageButton{
    String filepath;
    MunitionImage(String filepath,Point location){
        super(filepath);
        this.filepath = filepath;
        setSize(new Dimension(100,100));
        setLocation(location.x,location.y);
    }
}


class StateButton extends ImageButton{
    String action;
    static boolean goback;
    StateButton(String filepath,String action,Point location){
        super(filepath);
        this.action = action;
        goback = false;
        setLocation(location.x,location.y);
        setSize(new Dimension(100,100));
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Action(GameWindow.sound, action);
            }
        });
    }

    public void Action(Sound sound,String option){
        switch(option){
            case "play":
                sound.playMusic();
                break;
            case "pause":
                sound.pauseMusic();
                break;
            case "stop":
                sound.StopMusic();
                break;
            case "back":
                String className = GameWindow.gameLabel.peek().getClass().getName();
                if(className.equals("StartNewGameLabel") || className.equals("ResumeGameLabel") || className.equals("NextLevel")) {
                    GamePlayLabel label = (GamePlayLabel) GameWindow.gameLabel.peek();
                    if(className.equals("StartNewGameLabel"))
                        ResumeGameLabel.label = 0;
                        GamePlayLabel.SaveContext(label,1);
                    if(className.equals("NextLevel"))
                        ResumeGameLabel.label = 1;
                        GamePlayLabel.SaveContext(label,2);
                    if(className.equals("ResumeGameLabel")) {
                        if(ResumeGameLabel.label == 0)
                            GamePlayLabel.SaveContext(label, 1);
                        else if(ResumeGameLabel.label == 1)
                            GamePlayLabel.SaveContext(label, 2);
                    }
                }
                Game.game.BackLabel();
                break;
        }
    }
}

class MenuButton extends ImageButton{
    String action;
    MenuButton(String filepath,String action,Point location){
        super(filepath);
        this.action = action;
        setLocation(location.x,location.y);
        setSize(new Dimension(420,100));
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(action){
                    case "start_new_game":
                        Game.game.WindowSetter(LabelFactory.CreateLabel("start_game"));
                        break;
                    case "resume_game":
                        Game.game.WindowSetter(LabelFactory.CreateLabel("resume_game"));
                        break;
                    case "options":
                        Game.game.WindowSetter(LabelFactory.CreateLabel("options"));
                        break;
                    case "help":
                        Game.game.WindowSetter(LabelFactory.CreateLabel("help"));
                        break;
                    case "soundmenu":
                        Game.game.WindowSetter(LabelFactory.CreateLabel("soundmenu"));
                        break;
                    case "volumeup":
                        GameWindow.sound.VolumeUp();
                        break;
                    case "volumedown":
                        GameWindow.sound.VolumeDown();
                        break;
                    case "changemusic":
                        GameWindow.sound.ChangeMusic();
                        break;
                    case "selectrocket":
                        Game.game.WindowSetter(LabelFactory.CreateLabel("selectrocket"));
                        break;
                    case "mainmenu":
                        Game.game.WindowSetter(LabelFactory.CreateLabel("initWindow"));
                        break;
                    case "nextlevel":
                        Game.game.WindowSetter(LabelFactory.CreateLabel("nextlevel"));
                        break;
                    case "exit":
                        System.out.println("Game successfully exit");
                        System.exit(0);
                }
            }
        });
    }
}