package oop.bomberman.gui;

import javax.swing.*;
import java.awt.*;

public class CodePane extends JFrame {
    private JLabel code = new JLabel("Enter Code: ");
    private JTextField codeField = new JTextField();
    private JButton btEnter = new JButton("Enter");
    public CodePane(){
        setTitle("Code");
        setSize(400,100);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(code, BorderLayout.WEST);
        add(codeField, BorderLayout.CENTER);
        add(btEnter, BorderLayout.PAGE_END);
    }
}
