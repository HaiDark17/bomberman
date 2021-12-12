package oop.bomberman.entities.tile;

import oop.bomberman.Board;
import oop.bomberman.entities.Entity;
import oop.bomberman.entities.character.Player;
import oop.bomberman.graphics.Sprite;

public class PortalTile extends Tile {

    protected Board _board;

    public PortalTile(int x, int y, Board board, Sprite sprite) {
        super(x, y, sprite);
        _board = board;
    }

    @Override
    public boolean collide(Entity e) {

        if (e instanceof Player) {

            if (_board.detectNoEnemies() == false)
                return false;

            if (e.getXTile() == getX() && e.getYTile() == getY()) {
                if (_board.detectNoEnemies())
                    _board.nextLevel();
            }

            return true;
        }

        return false;
    }

}
