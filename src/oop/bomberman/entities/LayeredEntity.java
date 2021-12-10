package oop.bomberman.entities;

import java.util.LinkedList;

import oop.bomberman.entities.tile.destroyable.DestroyableTile;
import oop.bomberman.graphics.Screen;

public class LayeredEntity extends Entity {

    protected LinkedList<Entity> _entities = new LinkedList<>();

    public LayeredEntity(int x, int y, Entity... entities) {
        _x = x;
        _y = y;

        for (int i = 0; i < entities.length; i++) {
            _entities.add(entities[i]);

            if (i > 1) { //Add to destroyable tiles the bellow sprite for rendering in explosion
                if (entities[i] instanceof DestroyableTile)
                    ((DestroyableTile) entities[i]).addBelowSprite(entities[i - 1].getSprite());
            }
        }
    }

    @Override
    public void update() {
        clearRemoved();
        getTopEntity().update();
    }

    @Override
    public void render(Screen screen) {
        getTopEntity().render(screen);
    }

    public Entity getTopEntity() {
        return _entities.getLast();
    }

    private void clearRemoved() {
        Entity top = getTopEntity();

        if (top.isRemoved()) {
            matrix[(int) _entities.getLast()._y][(int) _entities.getLast()._x] = 1;
            /* Debug
            System.out.println(_entities.getLast()._x + " " + _entities.getLast()._y );
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 31; j++) System.out.print(matrix[i][j] + " ");
                System.out.println();
            }
            */
            _entities.removeLast();
        }
    }

    public void addBeforeTop(Entity e) {
        _entities.add(_entities.size() - 1, e);
    }

    @Override
    public boolean collide(Entity e) {
        return getTopEntity().collide(e);
    }

}
