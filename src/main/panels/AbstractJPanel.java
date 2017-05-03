package main.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 * Created by ferhaty
 */
public class AbstractJPanel extends JPanel {

    public AbstractJPanel() {
    
        setBounds(0,0,MainFrame.WINDOW_WIDTH,MainFrame.WINDOW_HEIGHT);
        setBackground(new Color(247, 249, 252));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);
    }
    public void init(){}
}

