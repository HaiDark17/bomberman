package oop.bomberman.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import oop.bomberman.Game;


public class Frame extends JFrame {

    public GamePanel _gamepane;
    private JPanel _containerpane;
    private InfoPanel _infopanel;
    private Game _game;

    public Frame() {
        _containerpane = new JPanel(new BorderLayout());
        _gamepane = new GamePanel(this);
        _infopanel = new InfoPanel(_gamepane.getGame());

        _containerpane.add(_infopanel, BorderLayout.NORTH);
        _containerpane.add(_gamepane, BorderLayout.CENTER);

        _game = _gamepane.getGame();
        _infopanel.setVisible(false);

        add(_containerpane);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        _game.start();
    }

	/*
	|--------------------------------------------------------------------------
	| Game Related
	|--------------------------------------------------------------------------
	 */

    public InfoPanel get_infopanel() {
        return _infopanel;
    }

    public void newGame() {
        _game.getBoard().newGame();
    }

    public void changeLevel(int i) {
        _game.getBoard().changeLevel(i);
    }

    public void pauseGame() {
        _game.getBoard().gamePauseOnSetting();
    }

    public void resumeGame() {
        _game.getBoard().gameResume();
    }

    public boolean isRunning() {
        return _game.isRunning();
    }

    public void setTime(int time) {
        _infopanel.setTime(time);
    }

    public void setLives(int lives) {
        _infopanel.setLives(lives);
    }

    public void setPoints(int points) {
        _infopanel.setPoints(points);
    }

    public boolean validCode(String str) {
        if (_gamepane.getGame().getMenu()) {
            return false;
        }
        return _game.getBoard().getLevel().validCode(str) != -1;
    }

    public void changeLevelByCode(String str) {
        _game.getBoard().changeLevelByCode(str);
    }

}