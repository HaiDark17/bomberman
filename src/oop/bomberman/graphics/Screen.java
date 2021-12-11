package oop.bomberman.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import oop.bomberman.Board;
import oop.bomberman.CommonVariables;
import oop.bomberman.Game;
import oop.bomberman.entities.Entity;
import oop.bomberman.entities.mob.Player;

import javax.imageio.ImageIO;

public class Screen implements CommonVariables {
    protected int _width, _height;
    public int[] _pixels;
    private final int _transparentColor = 0xffff00ff; //pink with alpha channel (ff in the begining)
    private Font font;
    public boolean isBasicMap = true;

    private BufferedImage setting = null;
    private Image settingFixed = null;

    private BufferedImage background = null;
    private Image backgroundFixed = null;

    private BufferedImage aboutImage = null;
    private Image aboutImageFixed = null;

    private BufferedImage chooseNewGameImage = null;
    private Image chooseNewGameImageFixed = null;

    public static int xOffset = 0, yOffset = 0;
    
    public Screen(int width, int height) {
        _width = width;
        _height = height;

        _pixels = new int[width * height];

        try {
            setting = ImageIO.read(new File("res/textures/options-table.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        settingFixed = setting.getScaledInstance(450,325,Image.SCALE_DEFAULT);

        try {
            background = ImageIO.read(new File("res/textures/menu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backgroundFixed = background.getScaledInstance(Game.WIDTH * Game.SCALE, Game.HEIGHT* Game.SCALE, Image.SCALE_DEFAULT);

        try {
            aboutImage = ImageIO.read(new File("res/textures/about-table.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            chooseNewGameImage = ImageIO.read(new File("res/textures/new-game.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        chooseNewGameImageFixed = chooseNewGameImage.getScaledInstance(266,100,Image.SCALE_DEFAULT);
    }

    public void clear() {
        Arrays.fill(_pixels, 0);
    }

    public void renderEntity(int xp, int yp, Entity entity) { //save entity pixels
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < entity.getSprite().getSize(); y++) {
            int ya = y + yp; //add offset
            for (int x = 0; x < entity.getSprite().getSize(); x++) {
                int xa = x + xp; //add offset
                if (xa < -entity.getSprite().getSize() || xa >= _width || ya < 0 || ya >= _height)
                    break; //fix black margins
                if (xa < 0) xa = 0; //start at 0 from left
                int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
                if (color != _transparentColor) _pixels[xa + ya * _width] = color;
            }
        }
    }

    public void renderEntityWithBelowSprite(int xp, int yp, Entity entity, Sprite below) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < entity.getSprite().getSize(); y++) {
            int ya = y + yp;
            for (int x = 0; x < entity.getSprite().getSize(); x++) {
                int xa = x + xp;
                if (xa < -entity.getSprite().getSize() || xa >= _width || ya < 0 || ya >= _height)
                    break; //fix black margins
                if (xa < 0) xa = 0;
                int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
                if (color != _transparentColor)
                    _pixels[xa + ya * _width] = color;
                else
                    _pixels[xa + ya * _width] = below.getPixel(x + y * below.getSize());
            }
        }
    }

    public static void setOffset(int xO, int yO) {
        xOffset = xO;
        yOffset = yO;
    }

    public static int calculateXOffset(Board board, Player player) {
        if (player == null) return 0;
        int temp = xOffset;

        double playerX = player.getX() / 16;
        double complement = 0.5;
        int firstBreakpoint = board.getWidth() / 4;
        int lastBreakpoint = board.getWidth() - firstBreakpoint;

        if (playerX > firstBreakpoint + complement && playerX < lastBreakpoint - complement) {
            temp = (int) player.getX() - (Game.WIDTH / 2);
        }

        return temp;
    }

    /*
    |--------------------------------------------------------------------------
    | Game Screens
    |--------------------------------------------------------------------------
     */
    public void intializeFont() {
        try {
            File fontFile = new File("res/font/VBRUSHTB.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 60);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException|FontFormatException e) {
            //Handle exception
        }
    }
    public void drawEndGame(Graphics g, int points, String code) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getRealWidth(), getRealHeight());

        Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.white);
        drawCenteredString("GAME OVER", getRealWidth(), getRealHeight(), g);

        font = new Font("Arial", Font.PLAIN, 10 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.yellow);
        drawCenteredString("POINTS: " + points, getRealWidth(), getRealHeight() + (Game.TILES_SIZE * 2) * Game.SCALE, g);

        font = new Font("Arial", Font.PLAIN, 10 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.GRAY);
        drawCenteredString(code, getRealWidth(), getRealHeight() * 2 - (Game.TILES_SIZE * 2) * Game.SCALE, g);
    }

    public void drawChangeLevel(Graphics g, int level) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getRealWidth(), getRealHeight());

        g.setFont(font);
        g.setColor(Color.white);
        drawCenteredString("LEVEL " + level, getRealWidth(), getRealHeight(), g);

    }

    public void drawPaused(Graphics g) {
        Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
        g.setFont(font);
        g.setColor(Color.white);
        drawCenteredString("PAUSED", getRealWidth(), getRealHeight(), g);

    }

    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    public void drawMenu(Graphics g) {
        g.drawImage(backgroundFixed, 0, 0, null);
    }

    public void drawSetting(Graphics g) {
        font.deriveFont(Font.PLAIN, 10);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawImage(settingFixed, Game.WIDTH - 100, Game.HEIGHT - 100, null);
        if(isBasicMap) {
            g.drawString("Erangel", Game.WIDTH + 120, Game.HEIGHT + 40);
        }else{
            g.drawString("Miramar", Game.WIDTH + 110, Game.HEIGHT + 40);
        }
    }

    public void drawAbout(Graphics g){
        g.drawImage(aboutImage, Game.WIDTH - 150, Game.HEIGHT - 100, null);
    }

    public void drawNewGameNoti(Graphics g){
        g.drawImage(chooseNewGameImageFixed, Game.WIDTH, Game.HEIGHT, null);
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public int getRealWidth() {
        return _width * Game.SCALE;
    }

    public int getRealHeight() {
        return _height * Game.SCALE;
    }
}