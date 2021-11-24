package oop.bomberman;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import oop.bomberman.exceptions.BombermanException;
import oop.bomberman.graphics.Screen;
import oop.bomberman.gui.Frame;
import oop.bomberman.input.Keyboard;

public class Game extends Canvas implements MouseListener, MouseMotionListener
{

	/*
	|--------------------------------------------------------------------------
	| Options & Configs
	|--------------------------------------------------------------------------
	 */
	public static final double VERSION = 1.9;

	public static final int TILES_SIZE = 16,
			WIDTH = TILES_SIZE * (int)(31 / 2), //minus one to ajust the window,
			HEIGHT = 13 * TILES_SIZE;

	public static int SCALE = 3;

	public static final String TITLE = "Bomberman ";

	//initial configs
	private static final int BOMBRATE = 1;
	private static final int BOMBRADIUS = 1;
	private static final double PLAYERSPEED = 0.9;

	public static final int TIME = 200;
	public static final int POINTS = 0;
	public static final int LIVES = 3;

	protected static int SCREENDELAY = 3;

	public static int _highscore = 0;

	//can be modified with bonus
	protected static int bombRate = BOMBRATE;
	protected static int bombRadius = BOMBRADIUS;
	protected static double playerSpeed = PLAYERSPEED;


	//Time in the level screen in seconds
	protected int _screenDelay = SCREENDELAY;

	private Keyboard _input;
	private boolean _running = false;
	private boolean _menu = true;
	private boolean _paused = true;

	private Board _board;
	private Screen screen;
	private Frame _frame;

	//this will be used to render the game, each render is a calculated image saved here
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

	public Game(Frame frame) throws BombermanException {
		_frame = frame;
		_frame.setTitle(TITLE);

		screen = new Screen(WIDTH, HEIGHT);
		_input = new Keyboard();

		_board = new Board(this, _input, screen);
		addKeyListener(_input);
		addMouseListener(this);
		addMouseMotionListener(this);
	}


	private void renderGame() { //render will run the maximum times it can per second
		BufferStrategy bs = getBufferStrategy(); //create a buffer to store images using canvas
		if(bs == null) { //if canvas dont have a bufferstrategy, create it
			createBufferStrategy(3); //triple buffer
			return;
		}

		screen.clear();

		_board.render(screen);

		for (int i = 0; i < pixels.length; i++) { //create the image to be rendered
			pixels[i] = screen._pixels[i];
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		_board.renderMessages(g);

		g.dispose(); //release resources
		bs.show(); //make next buffer visible
	}

	private void renderScreen() { //TODO: merge these render methods
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		Graphics g = bs.getDrawGraphics();

		_board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		_input.update();
		_board.update();
	}

	public void start() {
		readHighscore();
		while (_menu)
		{
			renderScreen();
		}

		_board.changeLevel(1);


		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; // nanosecond, 60 frames per second
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();

		while (_running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1)
			{
				update();
				updates++;
				delta--;
			}

			if (_paused)
			{
				if (_screenDelay <= 0)
				{
					_board.setShow(3);
					_paused = false;
				}
				renderScreen();
			}
			else
			{
				renderGame();
			}

			frames++;
			if (System.currentTimeMillis() - timer > 1000)
			{
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
				timer += 1000;
				_frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
				updates = 0;
				frames = 0;

				if (_board.getShow() == 2)
					--_screenDelay;
			}
		}
	}

	public void readHighscore()
	{
		BufferedReader read;
		try
		{
			read = new BufferedReader(new FileReader(new File("res/data/highscore.txt")));
			String score = read.readLine().trim();
			if (score == null)
				_highscore = 0;
			else
				_highscore = Integer.parseInt(score);
			read.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		Rectangle playButton = new Rectangle(Game.WIDTH - 110, Game.HEIGHT + 50, 200, 100);
		if (playButton.contains(e.getX(), e.getY()) == true)
		{
			_menu = false;
			_running = true;
		}
		Rectangle replayButton = new Rectangle(310, 412, 105, 30);
		if (replayButton.contains(e.getX(), e.getY()) == true)
		{
			_board.changeLevel(1);
			_board.resetPoints();

		}
		Rectangle highscoreButton = new Rectangle(Game.WIDTH + 100, Game.HEIGHT + 50, 250, 70);
		if (highscoreButton.contains(e.getX(), e.getY()) == true)
		{
			_board.setShow(5);
		}
		Rectangle backButton = new Rectangle(10, 10, 90, 45);
		if (backButton.contains(e.getX(),  e.getY()) == true)
		{
			_board.setShow(4);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e){
		Cursor.getDefaultCursor();
		Cursor cursor;
		Rectangle playButton = new Rectangle(Game.WIDTH - 110, Game.HEIGHT + 50, 200, 100);
		if (playButton.contains(e.getX(), e.getY()) == true){
			screen.isHover = true;
			cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			setCursor(cursor);
		}else{
			screen.isHover = false;
			setCursor(Cursor.getDefaultCursor());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e){}

	@Override
	public void mousePressed(MouseEvent e)
	{}

	@Override
	public void mouseReleased(MouseEvent e)
	{}

	@Override
	public void mouseEntered(MouseEvent e)
	{}

	@Override
	public void mouseExited(MouseEvent e)
	{}

	/*
	|--------------------------------------------------------------------------
	| Getters & Setters
	|--------------------------------------------------------------------------
	 */
	public static double getPlayerSpeed() {
		return playerSpeed;
	}

	public static int getBombRate() {
		return bombRate;
	}

	public static int getBombRadius() {
		return bombRadius;
	}

	public static void addPlayerSpeed(double i) {
		playerSpeed += i;
	}

	public static void addBombRadius(int i) {
		bombRadius += i;
	}

	public static void addBombRate(int i) {
		bombRate += i;
	}

	//screen delay
	public int getScreenDelay() {
		return _screenDelay;
	}

	public void decreaseScreenDelay() {
		_screenDelay--;
	}

	public void resetScreenDelay() {
		_screenDelay = SCREENDELAY;
	}

	public Keyboard getInput() {
		return _input;
	}

	public Board getBoard() {
		return _board;
	}

	public void run() {
		_running = true;
		_paused = false;
	}

	public void stop() {
		_running = false;
	}

	public boolean isRunning() {
		return _running;
	}

	public boolean isPaused() {
		return _paused;
	}

	public void pause() {
		_paused = true;
	}
}
