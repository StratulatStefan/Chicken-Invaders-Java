import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Sound {
    Clip clip;
    AudioInputStream inputStream;
    String status;
    Long currentFrame;
    String filepath;

    public Sound(String filepath) {
        this.filepath = filepath;
        status = "null";
        try {
            inputStream = AudioSystem.getAudioInputStream(
                    new File(filepath).getAbsoluteFile()
            );
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Sound playing exception : " + e.getMessage());
        }
    }

    public void playMusic() {
        if (status.equals("paused")) {
            this.clip.setMicrosecondPosition(currentFrame);
            this.clip.start();
        } else if (status.equals("stopped")) {
            this.resetAudioStream();
            this.clip.start();
        } else {
            this.clip.start();
        }
        status = "play";
    }

    public void pauseMusic() {
        if (this.status.equals("paused")) {
            System.out.println("Couldn't pause. Audio is already paused");
            return;
        }
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    public void StopMusic() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
        status = "stopped";
    }

    public void resetAudioStream() {
        try {
            inputStream = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
            this.clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Resume Audio Error");
        }
    }

    public void VolumeUp(){
        int x = 20;
        while(x > 0) {
            try {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(5.0f);
                x--;
            }
            catch(IllegalArgumentException e){
                System.out.println("Sound System error : " + e.getMessage());
            }
        }
        clip.start();
    }

    public void VolumeDown(){
        int x = 20;
        while(x > 0) {
            try {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-5.0f);
                x--;
            }
            catch(IllegalArgumentException e){
                System.out.println("Sound System error : " + e.getMessage());
            }
        }
        clip.start();
    }

    public void ChangeMusic(){
        filepath = Filepath.background_music1;
        status = "null";
        clip.stop();
        clip.close();
        try {
            inputStream = AudioSystem.getAudioInputStream(
                    new File(filepath).getAbsoluteFile()
            );
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Sound playing exception : " + e.getMessage());
        }
    }
}