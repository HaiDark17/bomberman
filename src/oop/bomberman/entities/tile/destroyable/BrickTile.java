package oop.bomberman.entities.tile.destroyable;


import oop.bomberman.audio.Audio;
import oop.bomberman.entities.Entity;
import oop.bomberman.entities.bomb.DirectionalExplosion;
import oop.bomberman.entities.mob.enemy.Kondoria;
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
		
		if(_destroyed) {
			_sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
			
			screen.renderEntityWithBelowSprite(x, y, this, _belowSprite);
		}
		else
			screen.renderEntity( x, y, this);
	}
	
	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof DirectionalExplosion) {
			destroy();
			audio.playSound("res/sound/Crystal.wav", 0);
		}
		
		if(e instanceof Kondoria)
			return true;
			
		return false;
	}
	
	
}
