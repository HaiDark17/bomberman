package oop.bomberman.graphics;

import java.util.Arrays;

public class Sprite {
    public int[] pixels;
    public final int SIZE;
    protected int realWidth;
    protected int realHeight;
    private int x, y;
    private SpriteSheet sheet;
    // public static Sprite voidSprite = new Sprite(16, 0xffffff); //black

    /**
     * Player.
     */

    public static Sprite player_down = new Sprite(16, 0, 0, SpriteSheet.character, 16, 16);
    public static Sprite player_right = new Sprite(16, 0, 1, SpriteSheet.character, 16, 16);
    public static Sprite player_left = new Sprite(16, 0, 2, SpriteSheet.character, 16, 16);
    public static Sprite player_up = new Sprite(16, 0, 3, SpriteSheet.character, 16, 16);

    public static Sprite player_down_1 = new Sprite(16, 1, 0, SpriteSheet.character, 16, 16);
    public static Sprite player_down_2 = new Sprite(16, 2, 0, SpriteSheet.character, 16, 16);

    public static Sprite player_right_1 = new Sprite(16, 1, 1, SpriteSheet.character, 16, 16);
    public static Sprite player_right_2 = new Sprite(16, 2, 1, SpriteSheet.character, 16, 16);

    public static Sprite player_left_1 = new Sprite(16, 1, 2, SpriteSheet.character, 16, 16);
    public static Sprite player_left_2 = new Sprite(16, 2, 2, SpriteSheet.character, 16, 16);

    public static Sprite player_up_1 = new Sprite(16, 1, 3, SpriteSheet.character, 16, 16);
    public static Sprite player_up_2 = new Sprite(16, 2, 3, SpriteSheet.character, 16, 16);

    public static Sprite player_dead1 = new Sprite(16, 3, 0, SpriteSheet.character, 16, 16);
    public static Sprite player_dead2 = new Sprite(16, 3, 1, SpriteSheet.character, 16, 16);
    public static Sprite player_dead3 = new Sprite(16, 3, 2, SpriteSheet.character, 16, 16);

    /**
     * Enemies.
     */

    // BALLOM
    public static Sprite balloom_left1 = new Sprite(16, 0, 0, SpriteSheet.enemy, 16, 16);
    public static Sprite balloom_left2 = new Sprite(16, 0, 1, SpriteSheet.enemy, 16, 16);
    public static Sprite balloom_left3 = new Sprite(16, 0, 2, SpriteSheet.enemy, 16, 16);

    public static Sprite balloom_right1 = new Sprite(16, 1, 0, SpriteSheet.enemy, 16, 16);
    public static Sprite balloom_right2 = new Sprite(16, 1, 1, SpriteSheet.enemy, 16, 16);
    public static Sprite balloom_right3 = new Sprite(16, 1, 2, SpriteSheet.enemy, 16, 16);

    public static Sprite balloom_dead = new Sprite(16, 0, 3, SpriteSheet.enemy, 16, 16);

    // ONEAL
    public static Sprite oneal_left1 = new Sprite(16, 2, 0, SpriteSheet.enemy, 16, 16);
    public static Sprite oneal_left2 = new Sprite(16, 2, 1, SpriteSheet.enemy, 16, 16);
    public static Sprite oneal_left3 = new Sprite(16, 2, 2, SpriteSheet.enemy, 16, 16);

    public static Sprite oneal_right1 = new Sprite(16, 3, 0, SpriteSheet.enemy, 16, 16);
    public static Sprite oneal_right2 = new Sprite(16, 3, 1, SpriteSheet.enemy, 16, 16);
    public static Sprite oneal_right3 = new Sprite(16, 3, 2, SpriteSheet.enemy, 16, 16);

    public static Sprite oneal_dead = new Sprite(16, 2, 3, SpriteSheet.enemy, 16, 16);

    //Doll
    public static Sprite doll_left1 = new Sprite(16, 4, 0, SpriteSheet.enemy, 16, 16);
    public static Sprite doll_left2 = new Sprite(16, 4, 1, SpriteSheet.enemy, 16, 16);
    public static Sprite doll_left3 = new Sprite(16, 4, 2, SpriteSheet.enemy, 16, 16);

    public static Sprite doll_right1 = new Sprite(16, 5, 0, SpriteSheet.enemy, 16, 16);
    public static Sprite doll_right2 = new Sprite(16, 5, 1, SpriteSheet.enemy, 16, 16);
    public static Sprite doll_right3 = new Sprite(16, 5, 2, SpriteSheet.enemy, 16, 16);

    public static Sprite doll_dead = new Sprite(16, 4, 3, SpriteSheet.enemy, 16, 16);

    //Minvo
    public static Sprite minvo_left1 = new Sprite(16, 0, 4, SpriteSheet.enemy, 16, 16);
    public static Sprite minvo_left2 = new Sprite(16, 0, 5, SpriteSheet.enemy, 16, 16);
    public static Sprite minvo_left3 = new Sprite(16, 0, 6, SpriteSheet.enemy, 16, 16);

    public static Sprite minvo_right1 = new Sprite(16, 1, 4, SpriteSheet.enemy, 16, 16);
    public static Sprite minvo_right2 = new Sprite(16, 1, 5, SpriteSheet.enemy, 16, 16);
    public static Sprite minvo_right3 = new Sprite(16, 1, 6, SpriteSheet.enemy, 16, 16);

    public static Sprite minvo_dead = new Sprite(16, 0, 7, SpriteSheet.enemy, 16, 16);

    //Kondoria
    public static Sprite kondoria_left1 = new Sprite(16, 2, 4, SpriteSheet.enemy, 16, 16);
    public static Sprite kondoria_left2 = new Sprite(16, 2, 5, SpriteSheet.enemy, 16, 16);
    public static Sprite kondoria_left3 = new Sprite(16, 2, 6, SpriteSheet.enemy, 16, 16);

    public static Sprite kondoria_right1 = new Sprite(16, 3, 4, SpriteSheet.enemy, 16, 16);
    public static Sprite kondoria_right2 = new Sprite(16, 3, 5, SpriteSheet.enemy, 16, 16);
    public static Sprite kondoria_right3 = new Sprite(16, 3, 6, SpriteSheet.enemy, 16, 16);

    public static Sprite kondoria_dead = new Sprite(16, 2, 7, SpriteSheet.enemy, 16, 16);

    //ALL
    public static Sprite mob_dead1 = new Sprite(16, 6, 0, SpriteSheet.enemy, 16, 16);
    public static Sprite mob_dead2 = new Sprite(16, 6, 1, SpriteSheet.enemy, 16, 16);
    public static Sprite mob_dead3 = new Sprite(16, 6, 2, SpriteSheet.enemy, 16, 16);

    /**
     * Bomb.
     */

    public static Sprite bomb = new Sprite(16, 0, 0, SpriteSheet.bomb, 16, 16);
    public static Sprite bomb_1 = new Sprite(16, 1, 0, SpriteSheet.bomb, 16, 16);
    public static Sprite bomb_2 = new Sprite(16, 2, 0, SpriteSheet.bomb, 16, 16);

    /**
     * Bomb explosion.
     */

    public static Sprite explosion_central = new Sprite(16, 0, 2, SpriteSheet.bomb, 16, 16);
    public static Sprite explosion_horizontal_left = new Sprite(16, 0, 1, SpriteSheet.bomb, 16, 16);
    public static Sprite explosion_horizontal_mid = new Sprite(16, 1, 1, SpriteSheet.bomb, 16, 16);
    public static Sprite explosion_horizontal_right = new Sprite(16, 2, 1, SpriteSheet.bomb, 16, 16);
    public static Sprite explosion_vertical_top = new Sprite(16, 3, 0, SpriteSheet.bomb, 16, 16);
    public static Sprite explosion_vertical_mid = new Sprite(16, 3, 1, SpriteSheet.bomb, 16, 16);
    public static Sprite explosion_vertical_down = new Sprite(16, 3, 2, SpriteSheet.bomb, 16, 16);

    /**
     * Power up.
     */

    public static Sprite powerup_bombs = new Sprite(16, 0, 0, SpriteSheet.item, 16, 16);
    public static Sprite powerup_flames = new Sprite(16, 1, 0, SpriteSheet.item, 16, 16);
    public static Sprite powerup_speed = new Sprite(16, 2, 0, SpriteSheet.item, 16, 16);

    public Sprite(int size, int x, int y, SpriteSheet sheet, int rw, int rh) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * SIZE;
        this.y = y * SIZE;
        this.sheet = sheet;
        //realWidth = rw;
        //realHeight = rh;
        load();
    }

    public Sprite(int size, int color) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        setColor(color);
    }

    private void setColor(int color) {
        Arrays.fill(pixels, color);
    }

    private void load() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                pixels[i + j * SIZE] = sheet.pixels[(i + x) + (j + y) * sheet.SIZE];
            }
        }
    }

    public void changeSheet(SpriteSheet s) {
        this.sheet = s;
        this.load();
    }

    /**
     * Moving sprite.
     */
    public static Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2, int animate, int time) {
        int calc = animate % time;
        int diff = time / 3;

        if (calc < diff) {
            return normal;
        }

        if (calc < diff * 2) {
            return x1;
        }

        return x2;
    }

    public static Sprite movingSprite(Sprite x1, Sprite x2, int animate, int time) {
        int diff = time / 2;
        return (animate % time > diff) ? x1 : x2;
    }

    public int getSize() {
        return this.SIZE;
    }

    public int[] getPixels() {
        return this.pixels;
    }

    public int getPixel(int i) {
        return this.pixels[i];
    }

    public int getRealWidth() {
        return this.realWidth;
    }

    public int getRealHeight() {
        return this.realHeight;
    }
}
