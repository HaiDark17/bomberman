package oop.bomberman.entities.tile.destroyable;

import oop.bomberman.entities.Entity;
import oop.bomberman.entities.bomb.DirectionalExplosion;
import oop.bomberman.entities.character.enemy.Kondoria;
import oop.bomberman.graphics.Screen;
import oop.bomberman.graphics.Sprite;
import oop.bomberman.level.Coordinates;

public class BrickTile extends DestroyableTile {
    public BrickTile(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Screen screen) {
        int x = Coordinates.tileToPixel(_x);
        int y = Coordinates.tileToPixel(_y);

        if (_destroyed) {
            _sprite = movingSprite(brick_exploded, brick_exploded1, brick_exploded2);
            screen.renderEntityWithBelowSprite(x, y, this, _belowSprite);
        } else {
            screen.renderEntity(x, y, this);
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof DirectionalExplosion) {
            destroy();
            brickBreakAudio.playSound(0);
        }

        return e instanceof Kondoria;
    }
}
