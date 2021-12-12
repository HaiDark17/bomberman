package oop.bomberman.entities.character.enemy;


import oop.bomberman.Board;
import oop.bomberman.Game;
import oop.bomberman.entities.character.enemy.algorithm.MediumAlgo;
import oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {


    public Kondoria(int x, int y, Board board) {
        super(x, y, board, Sprite.kondoria_dead, Game.getPlayerSpeed() / 4, 1000);

        _sprite = Sprite.kondoria_right1;

        algorithm = new MediumAlgo(_board.getPlayer(), this);
        _direction = algorithm.getDirection();
    }

    /*
    |--------------------------------------------------------------------------
    | Mob Sprite
    |--------------------------------------------------------------------------
     */
    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving)
                    _sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
                else
                    _sprite = Sprite.kondoria_left1;
                break;
            case 2:
            case 3:
                if (_moving)
                    _sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
                else
                    _sprite = Sprite.kondoria_left1;
                break;
        }
    }
}
