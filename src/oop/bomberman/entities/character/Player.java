package oop.bomberman.entities.character;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oop.bomberman.Board;
import oop.bomberman.Game;
import oop.bomberman.entities.Entity;
import oop.bomberman.entities.Message;
import oop.bomberman.entities.bomb.Bomb;
import oop.bomberman.entities.bomb.DirectionalExplosion;
import oop.bomberman.entities.character.enemy.Enemy;
import oop.bomberman.entities.tile.powerup.Powerup;
import oop.bomberman.graphics.Screen;
import oop.bomberman.graphics.Sprite;
import oop.bomberman.input.Keyboard;
import oop.bomberman.level.Coordinates;

public class Player extends Character {
    private List<Bomb> _bombs;
    protected Keyboard _input;

    protected int _timeBetweenPutBombs = 0;

    public static List<Powerup> _powerups = new ArrayList<>();


    public Player(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }


    /*
    |--------------------------------------------------------------------------
    | Update & Render
    |--------------------------------------------------------------------------
     */
    @Override
    public void update() {
        clearBombs();
        if (_alive == false) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--; //dont let this get tooo big

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead3;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }


    /*
    |--------------------------------------------------------------------------
    | Mob Unique
    |--------------------------------------------------------------------------
     */
    private void detectPlaceBomb() {
        if (_input.space && Game.getBombRate() > 0 && _timeBetweenPutBombs < 0) {

            int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
            int yt = Coordinates.pixelToTile((_y + _sprite.getSize() / 2) - _sprite.getSize()); //subtract half player height and minus 1 y position

            placeBomb(xt, yt);
            Game.addBombRate(-1);

            _timeBetweenPutBombs = 30;
        }
    }

    protected void placeBomb(int x, int y) {
        Bomb b = new Bomb(x, y, _board);
        Enemy.avoidBomb(x, y);
        //System.out.println(x + " " + y);
        _board.addBomb(b);
        placeBombAudio.playSound(0);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    /*
    |--------------------------------------------------------------------------
    | Mob Colide & Kill
    |--------------------------------------------------------------------------
     */
    @Override
    public void kill() {
        if (!_alive) return;

        _alive = false;

        _board.addLives(-1);
        deadAudio.playSound(0);
        Message msg = new Message("-1 LIVE", getXMessage(), getYMessage(), 2, Color.white, 14);
        _board.addMessage(msg);
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            if (_bombs.size() == 0) {

                if (_board.getLives() == 0)
                    _board.endGame();
                else
                    _board.restartLevel();
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Mob Movement
    |--------------------------------------------------------------------------
     */
    @Override
    protected void calculateMove() {
        int xa = 0, ya = 0;
        if (_input.up) ya--;
        if (_input.down) ya++;
        if (_input.left) xa--;
        if (_input.right) xa++;
        if (xa != 0 || ya != 0) {
            move(xa * Game.getPlayerSpeed(), ya * Game.getPlayerSpeed());
            _moving = true;
        } else {
            _moving = false;
        }
    }

    @Override
    public boolean canMove(double x, double y) {
        for (int c = 0; c < 4; c++) {
            double xt = ((_x + x) + c % 2 * 13) / Game.TILES_SIZE;
            double yt = ((_y + y) + c / 2 * 12 - 13) / Game.TILES_SIZE;

            Entity a = _board.getEntity(xt, yt, this);
            if (!a.collide(this)) {
				/*
				if (_input.right || _input.left) {
					if ((a.getY() * Game.TILES_SIZE + 14 > _y
							&& a.getY() * Game.TILES_SIZE < _y)) {
						_y -= 1;
					}
					if (((a.getY() + 1) * Game.TILES_SIZE + 15 > _y
							&& (a.getY() + 1) * Game.TILES_SIZE < _y)) {
						_y += 1;
					}
				}

				if (_input.up || _input.down) {
					if ((a.getX() * Game.TILES_SIZE + 16 > _x
							&& a.getX() * Game.TILES_SIZE + 6 < _x)) {
						_x += 1;
					}
					if (((a.getX() - 1) * Game.TILES_SIZE + 15 > _x
							&& (a.getX() - 1) * Game.TILES_SIZE < _x)) {
						_x -= 1;
					}
				}
				*/
                return false;
            }

        }
        return true;
    }

    @Override
    public void move(double xa, double ya) {
        if (xa > 0) _direction = 1;
        if (xa < 0) _direction = 3;
        if (ya > 0) _direction = 2;
        if (ya < 0) _direction = 0;

        if (canMove(0, ya)) { //separate the moves for the player can slide when is colliding
            _y += ya;
        }

        if (canMove(xa, 0)) {
            _x += xa;
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof DirectionalExplosion) {
            kill();
            return false;
        }

        if (e instanceof Enemy) {
            kill();
            return true;
        }

        return true;
    }

    /*
    |--------------------------------------------------------------------------
    | Powerups
    |--------------------------------------------------------------------------
     */
    public void addPowerup(Powerup p) {
        if (p.isRemoved()) return;

        _powerups.add(p);
        upItemAudio.playSound(0);
        p.setValues();
    }

    public void clearUsedPowerups() {
        Powerup p;
        for (int i = 0; i < _powerups.size(); i++) {
            p = _powerups.get(i);
            if (p.isActive() == false)
                _powerups.remove(i);
        }
    }

    public void removePowerups() {
        for (int i = 0; i < _powerups.size(); i++) {
            _powerups.remove(i);
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Mob Sprite
    |--------------------------------------------------------------------------
     */
    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}