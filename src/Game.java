import javax.swing.*;
import java.awt.*;
import java.util.Stack;

class GameWindow extends JFrame {
    public static Stack<GameLabel> gameLabel = new Stack<GameLabel>();
    private Dimension windowSize;
    static Sound sound;
    static int WindowWidth = 1000;
    static int WindowHeight = 700;
    public GameWindow() {
        setTitle("Chicken Invaders - Easter Edition");
        setSize(new Dimension(WindowWidth, WindowHeight));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.center();
        sound = new Sound(Game.filepath.background_music);
        sound.playMusic();
        gameLabel.push(LabelFactory.CreateLabel("initWindow"));
        add(gameLabel.peek());
        setVisible(true);
    }

    public void BackLabel(){
        remove(gameLabel.pop());
        add(gameLabel.peek());
        setVisible(true);
    }


    public void center(){
        windowSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int newX = (screenSize.width - windowSize.width)/2;
        int newY = (screenSize.height - windowSize.height)/2 - 20;
        this.setLocation(newX,newY);
    }

    public void WindowSetter(GameLabel wndw){
        remove(gameLabel.peek());
        gameLabel.push(wndw);
        add(gameLabel.peek());
        gameLabel.peek().setFocusable(true);
        gameLabel.peek().requestFocusInWindow();
        setVisible(true);
    }
}

public class Game extends JFrame {
    public static GameWindow game;
    public static Filepath filepath;
    Game(){
        filepath = new Filepath();
        game = new GameWindow();
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}
