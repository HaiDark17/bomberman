package oop.bomberman.entities.bomb;

import oop.bomberman.Board;
import oop.bomberman.entities.Entity;
import oop.bomberman.entities.character.Character;
import oop.bomberman.graphics.Screen;
import oop.bomberman.graphics.Sprite;


public class Explosion extends Entity {

    protected boolean _last = false;
    protected Board _board;
    protected Sprite _sprite1, _sprite2;

    public Explosion(int x, int y, int direction, boolean last, Board board) {
        _x = x;
        _y = y;
        _last = last;
        _board = board;

        switch (direction) {
            case 0:
                if (last == false) {
                    _sprite = Sprite.explosion_vertical_mid;
                } else {
                    _sprite = Sprite.explosion_vertical_top;
                }
                break;
            case 1:
                if (last == false) {
                    _sprite = Sprite.explosion_horizontal_mid;
                } else {
                    _sprite = Sprite.explosion_horizontal_right;
                }
                break;
            case 2:
                if (last == false) {
                    _sprite = Sprite.explosion_vertical_mid;
                } else {
                    _sprite = Sprite.explosion_vertical_down;
                }
                break;
            case 3:
                if (last == false) {
                    _sprite = Sprite.explosion_horizontal_mid;
                } else {
                    _sprite = Sprite.explosion_horizontal_left;
                }
                break;
        }
    }

    @Override
    public void render(Screen screen) {
        int xt = (int) _x << 4;
        int yt = (int) _y << 4;

        screen.renderEntity(xt, yt, this);
    }

    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {

        if (e instanceof Character) {
            ((Character) e).kill();
        }

        return true;
    }


}