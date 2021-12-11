package oop.bomberman.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Audio {
    private Clip clip = null;
    private boolean isDisabled = false;
    private String path;

    public Audio(String soundFile) {
        path = soundFile;
        File f = new File("./" + soundFile);
        AudioInputStream audioIn = null;

        try {
            audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        try {
            this.clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        try {
            this.clip.open(audioIn);
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playSound(int _loop) {
        if (!this.isDisabled) {
            File f = new File("./" + path);
            AudioInputStream audioIn = null;

            try {
                audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            } catch (IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }

            try {
                this.clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

            try {
                this.clip.open(audioIn);
            } catch (IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

            this.clip.start();
            this.clip.loop(_loop);
        }
    }

    public void stopSound() {
        this.isDisabled = true;
        this.clip.stop();
    }

    public void setDisabled(boolean disabled) {
        this.isDisabled = disabled;
    }
}