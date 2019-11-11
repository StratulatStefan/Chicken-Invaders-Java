import javax.swing.*;
import java.awt.*;

public class ContentFormat{
    String filepath;
    Point position;
    public ContentFormat(String filepath,Point position){
        this.filepath = filepath;
        this.position = position;

    }
    public JLabel getText(){
        JLabel label = new JLabel(new ImageIcon(filepath));
        label.setLocation(this.position);
        label.setSize(new Dimension(800,200));
        label.setLocation(this.position);
        return label;
    }
}



class ImageFormat extends JLabel{
    String filepath;
    Point position;
    final Image image;
    public ImageFormat(String filepath, Point position){
        this.filepath = filepath;
        this.position = position;
        image = new ImageIcon(this.filepath).getImage();
        setLocation(this.position);
        setSize(new Dimension(80,80));
    }
}

class DefendImage extends ImageFormat{
    String filepath;
    Point position;
    final Image image;
    public DefendImage(String filepath, Point position){
        super(filepath,position);
        this.filepath = filepath;
        this.position = position;
        image = new ImageIcon(this.filepath).getImage();
        setLocation(this.position);
        setSize(new Dimension(80,80));
    }

    public boolean Impact(Rocket rocket){
        boolean conditiexv1 = (this.position.x < rocket.location.x && this.position.x + this.getWidth() > rocket.location.x);
        boolean conditiexv2 = (rocket.location.x < this.position.x && rocket.location.x + rocket.getWidth() > this.position.x);
        boolean conditiey = (this.position.y > rocket.location.y);
        if((conditiexv1 || conditiexv2) && conditiey){
            return true;
        }
        return false;
    }
}

