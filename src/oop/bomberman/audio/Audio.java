package oop.bomberman.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Audio {
    private Clip clip = null;
    private boolean isHaveSound = true;
    private int loop;

    public void playSound(String soundFile, int _loop) {
        loop = _loop;
        File f = new File("./" + soundFile);
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            clip.open(audioIn);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        if(!isHaveSound){
            return;
        }
        clip.start();
        clip.loop(loop);
    }

    public void setSound() {
        if (isHaveSound) {
            isHaveSound = false;
            clip.stop();
        } else {
            isHaveSound = true;
        }
    }


}