package oop.bomberman.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import oop.bomberman.CommonVariables;
import oop.bomberman.Game;

public class InfoPanel extends JPanel implements CommonVariables {
    private JLabel emptyLabel_1;
    private JLabel emptyLabel_2;
    private JLabel emptyLabel_3;
    private JLabel emptyLabel_4;

    private JLabel timeLabel;
    private JLabel pointsLabel;
    private JLabel livesLabel;
    private JLabel soundLabel;
    private JLabel settingLabel;
    private JLabel resetLabel;

    private JButton settingButton;
    private JButton soundButton;
    private JButton resetButton;

    private ImageIcon left_bar = new ImageIcon((new ImageIcon("res/textures/left-bar.png")).getImage().getScaledInstance(80, 30, Image.SCALE_DEFAULT));
    private ImageIcon center_bar = new ImageIcon((new ImageIcon("res/textures/center-bar.png")).getImage().getScaledInstance(80, 30, Image.SCALE_DEFAULT));
    private ImageIcon right_bar = new ImageIcon((new ImageIcon("res/textures/right-bar.png")).getImage().getScaledInstance(80, 30, Image.SCALE_DEFAULT));
    private ImageIcon optionImg = new ImageIcon((new ImageIcon("res/textures/options.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon soundOnImg = new ImageIcon((new ImageIcon("res/textures/sound.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon soundOffImg = new ImageIcon((new ImageIcon("res/textures/mute.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon resetImg = new ImageIcon((new ImageIcon("res/textures/reset.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

    public InfoPanel(Game game) {
        setLayout(new GridLayout(1, 8));

        emptyLabel_1 = new JLabel();
        emptyLabel_2 = new JLabel();
        emptyLabel_3 = new JLabel();
        emptyLabel_4 = new JLabel();

        timeLabel = new JLabel();
        timeLabel.setIcon(left_bar);
        timeLabel.setText("Time: " + game.getBoard().getTime());
        timeLabel.setForeground(Color.white);
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);
        timeLabel.setHorizontalTextPosition(JLabel.CENTER);

        pointsLabel = new JLabel();
        pointsLabel.setIcon(center_bar);
        pointsLabel.setText("Points: " + game.getBoard().getPoints());
        pointsLabel.setForeground(Color.white);
        pointsLabel.setHorizontalAlignment(JLabel.CENTER);
        pointsLabel.setHorizontalTextPosition(JLabel.CENTER);

        livesLabel = new JLabel();
        livesLabel.setIcon(right_bar);
        livesLabel.setText("Lives: " + game.getBoard().getLives());
        livesLabel.setForeground(Color.white);
        livesLabel.setHorizontalAlignment(JLabel.LEFT);
        livesLabel.setHorizontalTextPosition(JLabel.CENTER);

        soundLabel = new JLabel();
        soundLabel.setLayout(new BorderLayout());


        soundButton = new JButton();
        soundButton.setIcon(soundOnImg);
        soundButton.setBackground(basicColor);
        soundButton.setBorder(BorderFactory.createEmptyBorder());
        soundButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        soundButton.setModel(new FixedStateButtonModel());
        soundButton.setFocusPainted(false);
        soundButton.setPreferredSize(new Dimension(30, 30));
        soundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (soundButton.getIcon().equals(soundOnImg)) {
                    disableSound();
                    soundButton.setIcon(soundOffImg);
                } else {
                    enableSound();
                    soundButton.setIcon(soundOnImg);
                }

            }
        });
        soundLabel.add(soundButton, BorderLayout.EAST);

        settingLabel = new JLabel();
        settingLabel.setLayout(new BorderLayout());

        settingButton = new JButton();
        settingButton.setBackground(basicColor);
        settingButton.setBorder(BorderFactory.createEmptyBorder());
        settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingButton.setIcon(optionImg);
        settingButton.setModel(new FixedStateButtonModel());
        settingButton.setFocusPainted(false);
        settingButton.setPreferredSize(new Dimension(35, 35));
        settingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!game.isPaused()) {
                    game.getBoard().gamePauseOnSetting();
                }
            }
        });
        settingLabel.add(settingButton, BorderLayout.WEST);

        resetLabel = new JLabel();
        resetLabel.setLayout(new BorderLayout());

        resetButton = new JButton();
        resetButton.setBackground(basicColor);
        resetButton.setBorder(BorderFactory.createEmptyBorder());
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        resetButton.setIcon(resetImg);
        resetButton.setModel(new FixedStateButtonModel());
        resetButton.setFocusPainted(false);
        resetButton.setPreferredSize(new Dimension(35, 35));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getBoard().gamePauseOnReset();
            }
        });
        resetLabel.add(resetButton, BorderLayout.CENTER);

        add(resetLabel);
        add(emptyLabel_1);
        add(timeLabel);
        add(pointsLabel);
        add(livesLabel);
        add(emptyLabel_4);
        add(soundLabel);
        add(settingLabel);


        setBackground(basicColor);
        setPreferredSize(new Dimension(0, 40));
    }

    private void disableSound() {
        mainAudio.stopSound();
        placeBombAudio.stopSound();
        explosionBombAudio.stopSound();
        deadAudio.stopSound();
        upItemAudio.stopSound();
        brickBreakAudio.stopSound();
    }

    private void enableSound() {
        mainAudio.setDisabled(false);
        placeBombAudio.setDisabled(false);
        explosionBombAudio.setDisabled(false);
        deadAudio.setDisabled(false);
        upItemAudio.setDisabled(false);
        brickBreakAudio.setDisabled(false);
        mainAudio.playSound(100);
    }

    public void setTime(int t) {
        this.timeLabel.setText("Time: " + t);
    }

    public void setLives(int t) {
        this.livesLabel.setText("Lives: " + t);
    }

    public void setPoints(int t) {
        this.pointsLabel.setText("Points: " + t);
    }

    public void changeBackground(Color c) {
        setBackground(c);
        soundButton.setBackground(c);
        settingButton.setBackground(c);
        resetButton.setBackground(c);
    }
}

class FixedStateButtonModel extends DefaultButtonModel {
    FixedStateButtonModel() {
    }

    public boolean isPressed() {
        return false;
    }

    public boolean isRollover() {
        return false;
    }

    public void setRollover(boolean b) {
    }
}