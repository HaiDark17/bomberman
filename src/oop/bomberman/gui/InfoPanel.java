package oop.bomberman.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import oop.bomberman.Game;

public class InfoPanel extends JPanel{

	private JLabel timeLabel;
	private JLabel pointsLabel;
	private JLabel livesLabel;
	private JButton settingButton;
	public boolean isSetting = false;

	private SettingPane setting;

	public InfoPanel(Game game) {
		setting = new SettingPane(game);
		setLayout(new GridLayout(4,1));

		timeLabel = new JLabel("Time: " + game.getBoard().getTime());
		timeLabel.setForeground(Color.white);
		timeLabel.setHorizontalAlignment(JLabel.CENTER);

		pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
		pointsLabel.setForeground(Color.white);
		pointsLabel.setHorizontalAlignment(JLabel.CENTER);

		livesLabel = new JLabel("Lives: " + game.getBoard().getLives());
		livesLabel.setForeground(Color.white);
		livesLabel.setHorizontalAlignment(JLabel.CENTER);

		settingButton = new JButton("Setting");
		settingButton.setBackground(Color.BLACK);
		settingButton.setForeground(Color.white);
		settingButton.setBorder(new LineBorder(Color.BLACK));
		settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		settingButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				settingButton.setBackground(Color.green);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				settingButton.setBackground(Color.black);
			}
		});

		settingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setting.setVisible(true);
				/*
				if(!isSetting) {
					isSetting = true;
					game.getBoard().setShow(5);

				}else{
					isSetting = false;
				}

				 */
			}
		});

		add(timeLabel);
		add(pointsLabel);
		add(livesLabel);
		add(settingButton);

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