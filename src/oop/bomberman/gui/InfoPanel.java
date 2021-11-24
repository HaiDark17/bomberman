package oop.bomberman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import oop.bomberman.Game;

public class InfoPanel extends JPanel {

	private JLabel timeLabel;
	private JLabel pointsLabel;
	private JLabel livesLabel;

	public InfoPanel(Game game) {
		setLayout(new GridLayout(3,1));

		timeLabel = new JLabel("Time: " + game.getBoard().getTime());
		timeLabel.setForeground(Color.white);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);

		pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
		pointsLabel.setForeground(Color.white);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);

		livesLabel = new JLabel("Lives: " + game.getBoard().getLives());
		livesLabel.setForeground(Color.white);
		livesLabel.setHorizontalAlignment(JLabel.CENTER);

		add(timeLabel);
		add(pointsLabel);
		add(livesLabel);

		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(100, 0));
	}

	public void setTime(int t) {
		timeLabel.setText("Time: " + t);
	}

	public void setLives(int t) {
		livesLabel.setText("Lives: " + t);

	}

	public void setPoints(int t) {
		pointsLabel.setText("Points: " + t);
	}

}