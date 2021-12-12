package oop.bomberman.entities.character.enemy;


import oop.bomberman.Board;
import oop.bomberman.Game;
import oop.bomberman.entities.character.enemy.algorithm.MediumAlgo;
import oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {

    public Oneal(int x, int y, Board board) {
        super(x, y, board, Sprite.oneal_dead, Game.getPlayerSpeed(), 200);
        _sprite = Sprite.oneal_left1;
        algorithm = new MediumAlgo(_board.getPlayer(), this);
        _direction = algorithm.getDirection();
    }

    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving)
                    _sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
                else
                    _sprite = Sprite.oneal_left1;
                break;
            case 2:
            case 3:
                if (_moving)
                    _sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
                else
                    _sprite = Sprite.oneal_left1;
                break;
        }
    }
}
