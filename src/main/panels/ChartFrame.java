package main.panels;

import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ferhaty
 */
public class ChartFrame extends JFrame {
    public static int WINDOW_WIDTH = 900, WINDOW_HEIGHT=800;
    private static JPanel mainPanel;

    public ChartFrame(){
        mainPanel = new JPanel();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(200, 100, WINDOW_WIDTH, WINDOW_HEIGHT);
        setContentPane(mainPanel);
        RefineryUtilities.centerFrameOnScreen(this);
    }

}
