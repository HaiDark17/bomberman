package oop.bomberman.entities.tile;


import oop.bomberman.entities.Entity;
import oop.bomberman.graphics.Sprite;

public class SurfaceTile extends Tile {

    public SurfaceTile(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }
}
