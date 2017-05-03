package main.view;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * Created by ferhaty on 12/13/2016.
 */
public class JtableHeader {
    public JTableHeader tableHeader(JTable Jtable) {
        JTableHeader tableHeader = Jtable.getTableHeader();
        tableHeader.setBackground(new Color(209, 225, 250));
        tableHeader.setFont(new Font("Georgia", Font.ITALIC, 15));
        return tableHeader;
    }
}