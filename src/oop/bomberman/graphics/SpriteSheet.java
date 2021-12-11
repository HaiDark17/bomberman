package oop.bomberman.graphics;

import oop.bomberman.CommonVariables;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class SpriteSheet implements CommonVariables {
    public int[] pixels;
    public int SIZE;
    private String path;

    public SpriteSheet(String path, int size) {
        this.path = path;
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            URL url = SpriteSheet.class.getResource(path);
            assert url != null;
            BufferedImage image = ImageIO.read(url);
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void modifySpriteSheet(String p, int s) {
        this.path = p;
        this.SIZE = s;
        pixels = new int[SIZE * SIZE];
        load();
    }

    public String getPath() {
        return path;
    }

    public int getSIZE() {
        return SIZE;
    }
}
