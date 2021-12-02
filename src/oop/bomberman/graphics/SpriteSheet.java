package oop.bomberman.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public int[] pixels;
	public final int SIZE;
	private final String path;

	public static SpriteSheet character = new SpriteSheet("/textures/character.png", 64);
	public static SpriteSheet bomb = new SpriteSheet("/textures/bomb.png", 64);
	public static SpriteSheet item = new SpriteSheet("/textures/item.png", 64);
	public static SpriteSheet enemy = new SpriteSheet("/textures/enemy.png", 128);
	public static SpriteSheet map0 = new SpriteSheet("/textures/erangel.png", 64);
	public static SpriteSheet map1 = new SpriteSheet("/textures/miramar.png", 64);
	public static SpriteSheet map = map0;

	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	private void load() {
		try {
			URL url = SpriteSheet.class.getResource(path);
			assert url != null;
			BufferedImage image = ImageIO.read(url);
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
