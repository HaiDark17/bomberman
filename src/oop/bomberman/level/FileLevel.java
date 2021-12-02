package oop.bomberman.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;



import oop.bomberman.Board;
import oop.bomberman.Game;
import oop.bomberman.entities.LayeredEntity;
import oop.bomberman.entities.mob.Player;
import oop.bomberman.entities.mob.enemy.Balloom;
import oop.bomberman.entities.mob.enemy.Doll;
import oop.bomberman.entities.mob.enemy.Kondoria;
import oop.bomberman.entities.mob.enemy.Minvo;
import oop.bomberman.entities.mob.enemy.Oneal;
import oop.bomberman.entities.tile.SurfaceTile;
import oop.bomberman.entities.tile.PortalTile;
import oop.bomberman.entities.tile.WallTile;
import oop.bomberman.entities.tile.destroyable.BrickTile;
import oop.bomberman.entities.tile.powerup.PowerupBombs;
import oop.bomberman.entities.tile.powerup.PowerupFlames;
import oop.bomberman.entities.tile.powerup.PowerupSpeed;
import oop.bomberman.exceptions.LoadLevelException;
import oop.bomberman.graphics.Screen;
import oop.bomberman.graphics.Sprite;

public class FileLevel extends Level {
	
	public FileLevel(String path, Board board) throws LoadLevelException {
		super(path, board);
	}
	
	@Override
	public void loadLevel(String path) throws LoadLevelException {
		try {
			URL absPath = FileLevel.class.getResource("/" + path);
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(absPath.openStream()));

			String data = in.readLine();
			StringTokenizer tokens = new StringTokenizer(data);
			
			level = Integer.parseInt(tokens.nextToken());
			height = Integer.parseInt(tokens.nextToken());
			width = Integer.parseInt(tokens.nextToken());

			lineTiles = new String[height];
			
			for(int i = 0; i < height; i++) {
				lineTiles[i] = in.readLine().substring(0, width);
 			}
			
			in.close();
		} catch (IOException e) {
			throw new LoadLevelException("Error loading level " + path, e);
		}
	}
	
	@Override
	public void createEntities() {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				addLevelEntity(lineTiles[y].charAt(x), x, y);
			}
		}
	}
	
	public void addLevelEntity(char c, int x, int y) {
		int pos = x + y * getWidth();
		
		switch(c) {
			// Map
			case '6': // Top-left corner
				board.addEntitie(pos, new WallTile(x, y, Sprite.wall_corner0));
				break;
			case '7': // Top-right corner
				board.addEntitie(pos, new WallTile(x, y, Sprite.wall_corner1));
				break;
			case '8': // Bottom-right corner
				board.addEntitie(pos, new WallTile(x, y, Sprite.wall_corner2));
				break;
			case '9': // Bottom-left corner
				board.addEntitie(pos, new WallTile(x, y, Sprite.wall_corner3));
				break;
			case 'T': // Top border
				board.addEntitie(pos, new WallTile(x, y, Sprite.wall_top));
				break;
			case 'L': // Left border
				board.addEntitie(pos, new WallTile(x, y, Sprite.wall_left));
				break;
			case 'R': // Right border
				board.addEntitie(pos, new WallTile(x, y, Sprite.wall_right));
				break;
			case 'D': // Bottom border
				board.addEntitie(pos, new WallTile(x, y, Sprite.wall_down));
				break;

			case '#': // Wall
				board.addEntitie(pos, new WallTile(x, y, Sprite.bunker));
				break;
			case '*': // Brick wall
				board.addEntitie(pos, new LayeredEntity(x, y,
						new SurfaceTile(x ,y, Sprite.grass),
						new BrickTile(x ,y, Sprite.brick)) );
				break;
			case 'P': // Port
				board.addEntitie(pos, new LayeredEntity(x, y,
						new SurfaceTile(x ,y, Sprite.grass),
						new PortalTile(x ,y, board, Sprite.portal),
						new BrickTile(x ,y, Sprite.brick)) );
				break;
			case ' ': // Surface
				board.addEntitie(pos, new SurfaceTile(x, y, Sprite.grass) );
				break;
			case 'C': // Character
				board.addMob( new Player(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board) );
				Screen.setOffset(0, 0);
				board.addEntitie(pos, new SurfaceTile(x, y, Sprite.grass) );
				break;

			// Items
			case 'B':
				LayeredEntity layer = new LayeredEntity(x, y, 
						new SurfaceTile(x ,y, Sprite.grass),
						new BrickTile(x ,y, Sprite.brick));
				
				if(board.isPowerupUsed(x, y, level) == false) {
					layer.addBeforeTop(new PowerupBombs(x, y, level, Sprite.powerup_bombs));
				}
				
				board.addEntitie(pos, layer);
				break;
			case 'S':
				layer = new LayeredEntity(x, y, 
						new SurfaceTile(x ,y, Sprite.grass),
						new BrickTile(x ,y, Sprite.brick));
				
				if(board.isPowerupUsed(x, y, level) == false) {
					layer.addBeforeTop(new PowerupSpeed(x, y, level, Sprite.powerup_speed));
				}
				
				board.addEntitie(pos, layer);
				break;
			case 'F':
				layer = new LayeredEntity(x, y, 
						new SurfaceTile(x ,y, Sprite.grass),
						new BrickTile(x ,y, Sprite.brick));
				
				if(board.isPowerupUsed(x, y, level) == false) {
					layer.addBeforeTop(new PowerupFlames(x, y, level, Sprite.powerup_flames));
				}
				
				board.addEntitie(pos, layer);
				break;

			// Enemies
			case '1':
				board.addMob( new Balloom(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
				board.addEntitie(pos, new SurfaceTile(x, y, Sprite.grass) );
				break;
			case '2':
				board.addMob( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
				board.addEntitie(pos, new SurfaceTile(x, y, Sprite.grass) );
				break;
			case '3':
				board.addMob( new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
				board.addEntitie(pos, new SurfaceTile(x, y, Sprite.grass) );
				break;
			case '4':
				board.addMob( new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
				board.addEntitie(pos, new SurfaceTile(x, y, Sprite.grass) );
				break;
			case '5':
				board.addMob( new Kondoria(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
				board.addEntitie(pos, new SurfaceTile(x, y, Sprite.grass) );
				break;

			default: 
				board.addEntitie(pos, new SurfaceTile(x, y, Sprite.grass) );
				break;
			}
	}

}
