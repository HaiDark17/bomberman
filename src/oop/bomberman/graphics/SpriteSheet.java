package oop.bomberman.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public int[] pixels;
	public final int SIZE;
	private final String path;

	public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);
	public static SpriteSheet character = new SpriteSheet("/textures/character.png", 64);

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
			//TODO: what should this do? stop the program? yes i think
			System.exit(0);
		}
	}
}
