package oop.bomberman.entities.tile.powerup;

import oop.bomberman.Game;
import oop.bomberman.entities.Entity;
import oop.bomberman.entities.character.Player;
import oop.bomberman.graphics.Sprite;

public class PowerupBombs extends Powerup {

    public PowerupBombs(int x, int y, int level, Sprite sprite) {
        super(x, y, level, sprite);
    }

    @Override
    public boolean collide(Entity e) {

        if (e instanceof Player) {
            ((Player) e).addPowerup(this);
            remove();
            return true;
        }

        return false;
    }

    @Override
    public void setValues() {
        _active = true;
        Game.addBombRate(1);
    }


}
