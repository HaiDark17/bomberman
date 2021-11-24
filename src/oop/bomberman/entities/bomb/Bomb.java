package oop.bomberman.entities.bomb;

import oop.bomberman.Board;
import oop.bomberman.Game;
import oop.bomberman.entities.AnimatedEntitiy;
import oop.bomberman.entities.Entity;
import oop.bomberman.entities.mob.Mob;
import oop.bomberman.entities.mob.Player;
import oop.bomberman.graphics.Screen;
import oop.bomberman.graphics.Sprite;
import oop.bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 120;
	public int _timeAfter = 20;
	
	protected Board _board;
	protected boolean _allowedToPassThru = true;
	protected DirectionalExplosion[] _explosions = null;
	protected boolean _exploded = false;
	
	public Bomb(int x, int y,Board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explosion();
			else
				updateExplosions();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderExplosions(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderExplosions(Screen screen) {
		for (int i = 0; i < _explosions.length; i++) {
			_explosions[i].render(screen);
		}
	}
	
	public void updateExplosions() {
		for (int i = 0; i < _explosions.length; i++) {
			_explosions[i].update();
		}
	}
	
	public void explode() {
		_timeToExplode = 0;
	}
	
	protected void explosion() {
		_allowedToPassThru = true;
		_exploded = true;
		
		Mob a = _board.getMobAt(_x, _y);
		if(a != null)  {
			a.kill();
		}
		
		_explosions = new DirectionalExplosion[4];
		
		for (int i = 0; i < _explosions.length; i++) {
			_explosions[i] = new DirectionalExplosion((int)_x, (int)_y, i, Game.getBombRadius(), _board);
		}
	}
	
	public Explosion explosionAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _explosions.length; i++) {
			if(_explosions[i] == null) return null;
			Explosion e = _explosions[i].explosionAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}
	
	public boolean isExploded() {
		return _exploded;
	}
	

	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof Player) {
			double diffX = e.getX() - Coordinates.tileToPixel(getX());
			double diffY = e.getY() - Coordinates.tileToPixel(getY());
			
			if(!(diffX >= -13 && diffX < 16 && diffY >= 1 && diffY <= 30)) {
				System.out.println(e.getX() +" "+ Coordinates.tileToPixel(getX()) +" "+ diffX);
				System.out.println(e.getY() +" "+ Coordinates.tileToPixel(getY()) +" "+ diffY);
				_allowedToPassThru = false;
			}
			
			return _allowedToPassThru;
		}
		
		if(e instanceof DirectionalExplosion) {
			explode();
			return true;
		}
		
		return false;
	}
}
