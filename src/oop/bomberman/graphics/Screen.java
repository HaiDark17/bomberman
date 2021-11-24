package oop.bomberman.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import oop.bomberman.Board;
import oop.bomberman.Game;
import oop.bomberman.entities.Entity;
import oop.bomberman.entities.mob.Player;

import javax.imageio.ImageIO;

public class Screen {
    protected int _width, _height;
    public int[] _pixels;
    private final int _transparentColor = 0xffff00ff; //pink with alpha channel (ff in the begining)

    public static int xOffset = 0, yOffset = 0;

    public boolean isHover = false;

    public Screen(int width, int height) {
        _width = width;
        _height = height;

        _pixels = new int[width * height];

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
                if(xa < -entity.getSprite().getSize() || xa >= _width || ya < 0 || ya >= _height) break; //fix black margins
                if(xa < 0) xa = 0; //start at 0 from left
                int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
                if(color != _transparentColor) _pixels[xa + ya * _width] = color;
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
                if(xa < -entity.getSprite().getSize() || xa >= _width || ya < 0 || ya >= _height) break; //fix black margins
                if(xa < 0) xa = 0;
                int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
                if(color != _transparentColor)
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
        if(player == null) return 0;
        int temp = xOffset;

        double playerX = player.getX() / 16;
        double complement = 0.5;
        int firstBreakpoint = board.getWidth() / 4;
        int lastBreakpoint = board.getWidth() - firstBreakpoint;

        if( playerX > firstBreakpoint + complement && playerX < lastBreakpoint - complement) {
            temp = (int)player.getX()  - (Game.WIDTH / 2);
        }

        return temp;
    }

    /*
    |--------------------------------------------------------------------------
    | Game Screens
    |--------------------------------------------------------------------------
     */
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
        drawCenteredString(code, getRealWidth(), getRealHeight() * 2  - (Game.TILES_SIZE * 2) * Game.SCALE, g);
    }

    public void drawChangeLevel(Graphics g, int level) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getRealWidth(), getRealHeight());

        Font font = new Font("Arial", Font.PLAIN, 20 * Game.SCALE);
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

    public void drawMenu(Graphics g)
    {
        Graphics2D paint = (Graphics2D) g;

        BufferedImage background = null;

        Shape play = new Rectangle(Game.WIDTH - 100, Game.HEIGHT + 50, 195, 100);
        Shape highscore = new Rectangle(Game.WIDTH + 100, Game.HEIGHT + 50, 250, 70);
        //Vẽ hình nền
        //g.drawImage(background, 0, 0, null);

        //Vẽ nút play
        paint.setColor(Color.BLACK);
        paint.draw(play);
        paint.setColor(Color.GREEN);
        paint.fill(play);

        if(isHover){
            paint.setColor(Color.RED);
            paint.fill(play);
        }

        //Vẽ nút điểm cao
        paint.setColor(Color.BLACK);
        paint.draw(highscore);
        paint.setColor(Color.GREEN);
        paint.fill(highscore);

        //Vẽ tên trò chơi
        paint.setColor(new Color(239, 249, 39));
        paint.setFont(new Font("Calibri", Font.BOLD, 30*Game.SCALE));
        paint.drawString("BOMBERMAN", 110, 120);

        //Vẽ chữ play
        paint.setColor(Color.BLACK);
        paint.setFont(new Font("Arial", Font.PLAIN, 20*Game.SCALE));
        paint.drawString("PLAY", Game.WIDTH - 80, Game.HEIGHT + 120);

        //Vẽ chữ highscore
        paint.setColor(Color.BLACK);
        paint.setFont(new Font("Arial", Font.PLAIN, 15*Game.SCALE));
        paint.drawString("Highscore", Game.WIDTH + 120, Game.HEIGHT + 100);
    }

    public void drawHighscore(Graphics g)
    {
        Graphics2D paint = (Graphics2D) g;

        BufferedImage background = null;

        try
        {
            background = ImageIO.read(new File("res/backgroundimage.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        g.drawImage(background, 0, 0, null);

        Shape back = new Rectangle(10, 10, 90, 45);
        paint.setColor(Color.BLACK);
        paint.draw(back);
        paint.setColor(Color.GREEN);
        paint.fill(back);


        paint.setColor(Color.BLACK);
        paint.setFont(new Font("Arial", Font.PLAIN, 10*Game.SCALE));
        paint.drawString("Back", 20, 40);
        drawCenteredString("High score: " + Game._highscore, getRealWidth(), getRealHeight(), g);

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