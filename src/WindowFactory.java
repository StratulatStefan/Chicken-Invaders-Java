import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

class GameLabel extends JLabel{
    public StateButton playbuttonlabel;
    public StateButton pausebuttonlabel;
    public StateButton stopbuttonlabel;
    public GameLabel(){
        playbuttonlabel = new StateButton(Game.filepath.StateButtons.get("play"),"play",new Point(30, 550));
        pausebuttonlabel = new StateButton(Game.filepath.StateButtons.get("pause"),"pause",new Point(100, 550));
        stopbuttonlabel = new StateButton(Game.filepath.StateButtons.get("stop"),"stop",new Point(170, 550));
        add(playbuttonlabel);
        add(pausebuttonlabel);
        add(stopbuttonlabel);
    }
}

class FirstLabel extends GameLabel{
    private ContentFormat title;
    private ContentFormat subtitle;
    private MenuButton startnewgame;
    private MenuButton resume;
    private MenuButton options;
    private MenuButton exit;
    public FirstLabel(){
        setIcon(new ImageIcon(Game.filepath.background_image));
        title = new ContentFormat(Game.filepath.MainMenu.get("title"),new Point(110,10));
        subtitle = new ContentFormat(Game.filepath.MainMenu.get("subtitle"),new Point(110,100));
        startnewgame = new MenuButton(Game.filepath.MainMenu.get("start_new_game"),"start_new_game",new Point(300,300));
        resume = new MenuButton(Game.filepath.MainMenu.get("resume_game"),"resume_game",new Point(300,360));
        options = new MenuButton(Game.filepath.MainMenu.get("options"),"options",new Point(300,430));
        exit = new MenuButton(Game.filepath.MainMenu.get("exit"),"exit",new Point(300,490));
        add(title.getText());
        add(subtitle.getText());
        add(startnewgame);
        add(resume);
        add(options);
        add(exit);
    }
}

class HelpLabel extends GameLabel{
    private StateButton backbutton;
    private ContentFormat controltitle;
    private ContentFormat up;
    private ContentFormat down;
    private ContentFormat left;
    private ContentFormat right;
    private ContentFormat shoot;
    private ContentFormat shootextra;


    public HelpLabel(){
        setIcon(new ImageIcon(Filepath.background_image));
        this.backbutton = new StateButton(Game.filepath.StateButtons.get("back"),"back",new Point(20 ,10));
        this.controltitle = new ContentFormat(Game.filepath.Controls.get("controls_title"),new Point(100,10));
        this.up = new ContentFormat(Game.filepath.Controls.get("up"),new Point(100,120));
        this.left = new ContentFormat(Game.filepath.Controls.get("left"),new Point(-100,200));
        this.right = new ContentFormat(Game.filepath.Controls.get("right"),new Point(300,200));
        this.down = new ContentFormat(Game.filepath.Controls.get("down"),new Point(100,280));
        this.shoot = new ContentFormat(Game.filepath.Controls.get("shoot"),new Point(100,360));
        this.shootextra = new ContentFormat(Game.filepath.Controls.get("shoot_extra"),new Point(100,440));
        add(backbutton);
        add(controltitle.getText());
        add(up.getText());
        add(down.getText());
        add(left.getText());
        add(right.getText());
        add(shoot.getText());
        add(shootextra.getText());

    }
}

class OptionsLabel extends GameLabel{
    private ContentFormat optionsTitle;
    private StateButton backbutton;
    private MenuButton help;
    private MenuButton soundbutton;
    private MenuButton selectrocket;
    public OptionsLabel(){
        setIcon(new ImageIcon(Filepath.background_image));
        this.optionsTitle = new ContentFormat(Game.filepath.Options.get("options_title"),new Point(100,10));
        this.help = new MenuButton(Game.filepath.Options.get("control"),"help",new Point(300,210));
        this.soundbutton = new MenuButton(Game.filepath.Options.get("sound"),"soundmenu",new Point(300,290));
        this.selectrocket = new MenuButton(Game.filepath.Options.get("rocket"),"selectrocket",new Point(300,370));
        this.backbutton = new StateButton(Game.filepath.StateButtons.get("back"),"back",new Point(20 ,10));
        add(optionsTitle.getText());
        add(backbutton);
        add(help);
        add(soundbutton);
        add(selectrocket);
    }
}

class SoundMenuLabel extends GameLabel{
    private StateButton backbutton;
    private ContentFormat soundtitle;
    private MenuButton volumeup;
    private MenuButton volumedown;
    private MenuButton changeMusic;
    public SoundMenuLabel(){
        setIcon(new ImageIcon(Filepath.background_image));
        this.backbutton = new StateButton(Game.filepath.StateButtons.get("back"),"back",new Point(20 ,10));
        this.soundtitle = new ContentFormat(Game.filepath.SoundMenu.get("sound_title"),new Point(100,10));
        volumeup = new MenuButton(Game.filepath.SoundMenu.get("volume_up"),"volumeup",new Point(300,200));
        volumedown = new MenuButton(Game.filepath.SoundMenu.get("volume_down"),"volumedown",new Point(300,290));
        changeMusic = new MenuButton(Game.filepath.SoundMenu.get("change_music"),"changemusic",new Point(300,380));
        add(backbutton);
        add(soundtitle.getText());
        add(volumeup);
        add(volumedown);
        add(changeMusic);
    }
}

class RocketMenuLabel extends GameLabel{
    private StateButton backbutton;
    private ContentFormat title;
    private RocketImage[] rockets = new RocketImage[5];
    public RocketMenuLabel(){
        setIcon(new ImageIcon(Filepath.background_image));
        this.backbutton = new StateButton(Game.filepath.StateButtons.get("back"),"back",new Point(20 ,10));
        this.title = new ContentFormat(Filepath.rocket,new Point(100,10));
        for(int i = 0;i<rockets.length;i++){
            if(i == 0) {
                rockets[i] = new RocketImage(Filepath.rockets[i], new Point(100, 300));
            }else{
                rockets[i] = new RocketImage(Filepath.rockets[i], new Point(rockets[i-1].getLocation().x + rockets[i - 1].getWidth()-50, 300));
            }
            add(rockets[i]);
        }
        add(title.getText());
        add(backbutton);
    }
}

class GameEnd extends GameLabel{
    private ContentFormat the_end;
    private ContentFormat you_died;
    private ContentFormat score;
    private ContentFormat number;
    private MenuButton gotomainmenu;
    public GameEnd(){
        setIcon(new ImageIcon(Filepath.background_play));
        this.the_end = new ContentFormat("resources\\endpage\\the_end.png",new Point(100,10));
        this.you_died = new ContentFormat("resources\\endpage\\you_died.png",new Point(100,170));
        this.score = new ContentFormat("resources\\endpage\\score.png",new Point(50,300));
        this.number = new ContentFormat(Game.filepath.Numbers.get(GamePlayLabel.scor),new Point(160,300));
        this.gotomainmenu = new MenuButton("resources\\endpage\\gotomainmenu.png","mainmenu",new Point(100,400));
        this.gotomainmenu.setSize(new Dimension(800,200));
        add(number.getText());
        add(the_end.getText());
        add(you_died.getText());
        add(score.getText());
        add(gotomainmenu);
        GamePlayLabel.lifes = 4;
    }
}

class Between extends GameLabel{
    private ContentFormat you_won;
    private ContentFormat killed_all;
    private MenuButton gotonextlevel;
    private MenuButton gotomainmenu;
    public Between() {
        setIcon(new ImageIcon(Filepath.background_play));
        this.you_won = new ContentFormat("resources\\between\\you_won.png",new Point(100,10));
        this.killed_all = new ContentFormat("resources\\between\\killed_all.png",new Point(100,170));
        this.gotonextlevel = new MenuButton("resources\\between\\nextlevel.png","nextlevel",new Point(100,280));
        this.gotomainmenu = new MenuButton("resources\\endpage\\gotomainmenu.png","mainmenu",new Point(100,400));
        this.gotomainmenu.setSize(new Dimension(800,200));
        this.gotonextlevel.setSize(new Dimension(800,200));
        add(you_won.getText());
        add(killed_all.getText());
        add(gotomainmenu);
        add(gotonextlevel);
    }
}

class LabelFactory{
    public static GameLabel CreateLabel(String windowType){
        switch(windowType){
            case "initWindow":
                return new FirstLabel();
            case "start_game":
                return new StartNewGameLabel(0);
            case "resume_game":
                return new ResumeGameLabel();
            case "options":
                return new OptionsLabel();
            case "help":
                return new HelpLabel();
            case "soundmenu":
                return new SoundMenuLabel();
            case "selectrocket":
                return new RocketMenuLabel();
            case "the_end":
                return new GameEnd();
            case "between":
                return new Between();
            case "nextlevel":
                return new NextLevel(0);
        }
        return null;
    }
}



