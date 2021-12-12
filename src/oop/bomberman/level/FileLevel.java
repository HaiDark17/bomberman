package oop.bomberman.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

import oop.bomberman.Board;
import oop.bomberman.CommonVariables;
import oop.bomberman.Game;
import oop.bomberman.entities.LayeredEntity;
import oop.bomberman.entities.character.Player;
import oop.bomberman.entities.character.enemy.Balloom;
import oop.bomberman.entities.character.enemy.Doll;
import oop.bomberman.entities.character.enemy.Kondoria;
import oop.bomberman.entities.character.enemy.Minvo;
import oop.bomberman.entities.character.enemy.Oneal;
import oop.bomberman.entities.tile.SurfaceTile;
import oop.bomberman.entities.tile.PortalTile;
import oop.bomberman.entities.tile.WallTile;
import oop.bomberman.entities.tile.destroyable.BrickTile;
import oop.bomberman.entities.tile.powerup.PowerupBombs;
import oop.bomberman.entities.tile.powerup.PowerupFlames;
import oop.bomberman.entities.tile.powerup.PowerupSpeed;

import oop.bomberman.graphics.Screen;
import oop.bomberman.graphics.Sprite;

public class FileLevel extends Level implements CommonVariables {

    public FileLevel(String path, Board board) {
        super(path, board);
    }

    @Override
    public void loadLevel(String path) {
        try {
            URL absPath = FileLevel.class.getResource("/" + path);
            assert absPath != null;
            BufferedReader in = new BufferedReader(new InputStreamReader(absPath.openStream()));

            String data = in.readLine();
            StringTokenizer tokens = new StringTokenizer(data);

            level = Integer.parseInt(tokens.nextToken());
            height = Integer.parseInt(tokens.nextToken());
            width = Integer.parseInt(tokens.nextToken());

            lineTiles = new String[height];

            for (int i = 0; i < height; i++) {
                lineTiles[i] = in.readLine().substring(0, width);
            }

            in.close();
        } catch (IOException e) {
            System.out.println("Error loading level " + path);
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

        switch (c) {
            // Map
            case '6': // Top-left corner
                board.addEntitie(pos, new WallTile(x, y, wall_corner0));
                break;
            case '7': // Top-right corner
                board.addEntitie(pos, new WallTile(x, y, wall_corner1));
                break;
            case '8': // Bottom-right corner
                board.addEntitie(pos, new WallTile(x, y, wall_corner2));
                break;
            case '9': // Bottom-left corner
                board.addEntitie(pos, new WallTile(x, y, wall_corner3));
                break;
            case 'T': // Top border
                board.addEntitie(pos, new WallTile(x, y, wall_top));
                break;
            case 'L': // Left border
                board.addEntitie(pos, new WallTile(x, y, wall_left));
                break;
            case 'R': // Right border
                board.addEntitie(pos, new WallTile(x, y, wall_right));
                break;
            case 'D': // Bottom border
                board.addEntitie(pos, new WallTile(x, y, wall_down));
                break;

            case '#': // Wall
                board.addEntitie(pos, new WallTile(x, y, bunker));
                break;
            case '*': // Brick wall
                board.addEntitie(pos, new LayeredEntity(x, y,
                        new SurfaceTile(x, y, grass),
                        new BrickTile(x, y, brick)));
                break;
            case 'P': // Port
                board.addEntitie(pos, new LayeredEntity(x, y,
                        new SurfaceTile(x, y, grass),
                        new PortalTile(x, y, board, portal),
                        new BrickTile(x, y, brick)));
                break;
            case 'C': // Character
                matrix[y][x] = 1;
                board.addMob(new Player(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                Screen.setOffset(0, 0);
                board.addEntitie(pos, new SurfaceTile(x, y, grass));
                break;

            // Items
            case 'B':
                LayeredEntity layer = new LayeredEntity(x, y,
                        new SurfaceTile(x, y, grass),
                        new BrickTile(x, y, brick));

                if (!board.isPowerupUsed(x, y, level)) {
                    layer.addBeforeTop(new PowerupBombs(x, y, level, Sprite.powerup_bombs));
                }

                board.addEntitie(pos, layer);
                break;
            case 'S':
                layer = new LayeredEntity(x, y,
                        new SurfaceTile(x, y, grass),
                        new BrickTile(x, y, brick));

                if (!board.isPowerupUsed(x, y, level)) {
                    layer.addBeforeTop(new PowerupSpeed(x, y, level, Sprite.powerup_speed));
                }

                board.addEntitie(pos, layer);
                break;
            case 'F':
                layer = new LayeredEntity(x, y,
                        new SurfaceTile(x, y, grass),
                        new BrickTile(x, y, brick));

                if (!board.isPowerupUsed(x, y, level)) {
                    layer.addBeforeTop(new PowerupFlames(x, y, level, Sprite.powerup_flames));
                }

                board.addEntitie(pos, layer);
                break;

            // Enemies
            case '1':
                matrix[y][x] = 1;
                board.addMob(new Balloom(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                board.addEntitie(pos, new SurfaceTile(x, y, grass));
                break;
            case '2':
                matrix[y][x] = 1;
                board.addMob(new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                board.addEntitie(pos, new SurfaceTile(x, y, grass));
                break;
            case '3':
                matrix[y][x] = 1;
                board.addMob(new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                board.addEntitie(pos, new SurfaceTile(x, y, grass));
                break;
            case '4':
                matrix[y][x] = 1;
                board.addMob(new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                board.addEntitie(pos, new SurfaceTile(x, y, grass));
                break;
            case '5':
                matrix[y][x] = 1;
                board.addMob(new Kondoria(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
                board.addEntitie(pos, new SurfaceTile(x, y, grass));
                break;

            default: // Surface
                matrix[y][x] = 1;
                board.addEntitie(pos, new SurfaceTile(x, y, grass));
                break;
        }
    }

}
