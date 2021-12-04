package oop.bomberman.gui;

import oop.bomberman.CommonVariables;
import oop.bomberman.Game;
import oop.bomberman.graphics.SpriteSheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class SettingPane extends JFrame implements CommonVariables {

    private CodePane codePane = new CodePane();
    public SettingPane(Game game){
        setLayout(new GridBagLayout());
        setTitle("Setting");
        setSize(500,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;


        JButton bt1 = new JButton();
        bt1.setText("New Game");
        bt1.setFont(new Font("Arial", Font.PLAIN, 20));
        bt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.getBoard().newGame();
                setVisible(false);
                dispose();
            }
        });
        add(bt1,gbc);


        JButton bt3 = new JButton("Sound: On");
        bt3.setFont(new Font("Arial", Font.PLAIN, 20));
        bt3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bt3.getText().equals("Sound: On")){
                    audio.setSound();
                    audio.playSound("res/sound/stage.wav",100);
                    bt3.setText("Sound: Off");
                }else{
                    audio.setSound();
                    audio.playSound("res/sound/stage.wav",100);
                    bt3.setText("Sound: On");
                }
            }
        });
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(bt3,gbc);

        JButton bt4 = new JButton("Code");
        bt4.setFont(new Font("Arial", Font.PLAIN, 20));
        bt4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codePane.setVisible(true);
            }
        });
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(bt4,gbc);
/*
        JButton bt5 = new JButton("Change Map");
        bt5.setFont(new Font("Arial", Font.PLAIN, 20));
        bt5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map.modifySpriteSheet(map1.getPath(), map1.getSIZE());

                setVisible(false);
                dispose();
            }
        });
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(bt5,gbc);

 */
    }
}
