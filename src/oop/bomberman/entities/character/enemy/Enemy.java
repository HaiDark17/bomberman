package oop.bomberman.entities.character.enemy;

import java.awt.Color;

import oop.bomberman.Board;
import oop.bomberman.Game;
import oop.bomberman.entities.Entity;
import oop.bomberman.entities.Message;
import oop.bomberman.entities.bomb.DirectionalExplosion;
import oop.bomberman.entities.character.Character;
import oop.bomberman.entities.character.Player;
import oop.bomberman.entities.character.enemy.algorithm.Algorithm;
import oop.bomberman.graphics.Screen;
import oop.bomberman.graphics.Sprite;
import oop.bomberman.level.Coordinates;

public abstract class Enemy extends Character {
    protected int points;
    protected double speed;
    protected Algorithm algorithm;

    //necessary to correct move
    protected final double MAX_STEPS;
    protected final double rest;
    protected double steps;

    protected int finalAnimation = 30;
    protected Sprite deadSprite;

    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
        super(x, y, board);

        this.points = points;
        this.speed = speed;

        MAX_STEPS = Game.TILES_SIZE / speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        steps = MAX_STEPS;

        _timeAfter = 20;
        deadSprite = dead;
    }

    public static void avoidBomb(int x, int y) {
        matrix[y][x] = 0;
        // Up
        if (y - 1 >= 0 && matrix[y - 1][x] == 1) {
            matrix[y - 1][x] = -1;
        }
        // Right
        if (x + 1 < MAP_WIDTH && matrix[y][x + 1] == 1) {
            matrix[y][x + 1] = -1;
        }
        // Down
        if (y + 1 < MAP_HEIGHT && matrix[y + 1][x] == 1) {
            matrix[y + 1][x] = -1;
        }
        // Left
        if (x - 1 >= 0 && matrix[y][x - 1] == 1) {
            matrix[y][x - 1] = -1;
        }
    }

    public static void resetAvoidBomb(int x, int y) {
        matrix[y][x] = 1;

        // Up
        if (y - 1 >= 0 && matrix[y - 1][x] == -1) {
            matrix[y - 1][x] = 1;
        }
        // Right
        if (x + 1 < MAP_WIDTH && matrix[y][x + 1] == -1) {
            matrix[y][x + 1] = 1;
        }
        // Down
        if (y + 1 < MAP_HEIGHT && matrix[y + 1][x] == -1) {
            matrix[y + 1][x] = 1;
        }
        // Left
        if (x - 1 >= 0 && matrix[y][x - 1] == -1) {
            matrix[y][x - 1] = 1;
        }


    }

    @Override
    public void update() {
        animate();

        if (_alive == false) {
            afterKill();
            return;
        }

        calculateMove();
    }

    @Override
    public void render(Screen screen) {
        if (_alive) {
            chooseSprite();
        } else {
            if (_timeAfter > 0) {
                _sprite = deadSprite;
                _animate = 0;
            } else {
                _sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
            }
        }
        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    @Override
    public void calculateMove() {
        int xa = 0, ya = 0;
        if (steps <= 0) {
            _direction = algorithm.getDirection();
            steps = MAX_STEPS;
        }

        if (_direction == 0) ya--;
        if (_direction == 2) ya++;
        if (_direction == 3) xa--;
        if (_direction == 1) xa++;

        if (canMove(xa, ya)) {
            steps -= 1 + rest;
            move(xa * speed, ya * speed);
            _moving = true;
        } else {
            steps = 0;
            _moving = false;
        }
    }

    @Override
    public void move(double xa, double ya) {
        if (!_alive) {
            return;
        }
        _y += ya;
        _x += xa;
    }

    @Override
    public boolean canMove(double x, double y) {
        double xr = _x, yr = _y - 16; //subtract y to get more accurate results
        //the thing is, subract 15 to 16 (sprite size), so if we add 1 tile we get the next pixel tile with this
        //we avoid the shaking inside tiles with the help of steps
        if (_direction == 0) {
            yr += _sprite.getSize() - 1;
            xr += _sprite.getSize() / 2;
        }
        if (_direction == 1) {
            yr += _sprite.getSize() / 2;
            xr += 1;
        }
        if (_direction == 2) {
            xr += _sprite.getSize() / 2;
            yr += 1;
        }
        if (_direction == 3) {
            xr += _sprite.getSize() - 1;
            yr += _sprite.getSize() / 2;
        }

        int xx = Coordinates.pixelToTile(xr) + (int) x;
        int yy = Coordinates.pixelToTile(yr) + (int) y;

        Entity a = _board.getEntity(xx, yy, this); //entity of the position we want to go

        return a.collide(this);
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof DirectionalExplosion) {
            kill();
            return false;
        }

        if (e instanceof Player) {
            ((Player) e).kill();
            return false;
        }

        return true;
    }

    @Override
    public void kill() {
        if (_alive == false) {
            return;
        }
        _alive = false;
        _board.addPoints(points);
        Message msg = new Message("+" + points, getXMessage(), getYMessage(), 2, Color.white, 14);
        _board.addMessage(msg);
    }


    @Override
    protected void afterKill() {
        if (_timeAfter > 0) {
            _timeAfter--;
        } else {
            if (finalAnimation > 0) {
                --finalAnimation;
            } else {
                remove();
            }
        }
    }

    protected abstract void chooseSprite();
}
