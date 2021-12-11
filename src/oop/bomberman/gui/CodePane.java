package oop.bomberman.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CodePane extends JFrame implements ActionListener {
    private JLabel code = new JLabel("Enter Code: ");
    private JTextField codeField = new JTextField();
    private JButton btEnter = new JButton("Enter");
    private boolean valid = false;
    private Frame frame;

    public CodePane(Frame _frame) {
        frame = _frame;
        setTitle("Code");
        setSize(400, 100);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        btEnter.addActionListener(this);
        add(code, BorderLayout.WEST);
        add(codeField, BorderLayout.CENTER);
        add(btEnter, BorderLayout.PAGE_END);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String _code = codeField.getText();

        if (frame.validCode(_code)) {
            frame.changeLevelByCode(_code);
            valid = true;
            dispose();
        } else {
            if (frame._gamepane.getGame().getMenu()) {
                JOptionPane.showMessageDialog(frame,
                        "You haven't started the game yet! Please start the game to change level by code!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "That code isn't correct! Please enter the code again!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
