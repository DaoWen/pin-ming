/*
 * MainFrame.java
 *
 * Created on August 1, 2007, 2:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author OfficeRecorder
 */
public class MainFrame extends JFrame implements WindowListener {
    
    private static MainFrame mainFrame;
    private static NameGrid nGrid;
    private static JLabel statusBar;
    
    /** Creates a new instance of MainFrame */
    public MainFrame() {
        super("Pin-Ming");
        mainFrame = this;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // The windowClosing function will handle it
        addWindowListener(this);
        JPanel cp = (JPanel) getContentPane();
        cp.setBorder(BorderFactory.createEmptyBorder(4,2,2,2));
        setSize(500,400);
        // Container gc = getContentPane();
        setJMenuBar(new MainMenu());
        nGrid = new NameGrid();
        cp.add(nGrid,BorderLayout.CENTER);
        statusBar = new JLabel("Ready");
        Border edge = BorderFactory.createLoweredBevelBorder(), padding = BorderFactory.createEmptyBorder(2,4,2,4);
        statusBar.setBorder(BorderFactory.createCompoundBorder(edge,padding));
        cp.add(statusBar,BorderLayout.SOUTH);
        //pack();
        setVisible(true);
    }

    public static void fillNameGrid(pinming.data.GedcomDatabase db) {
        nGrid.fillNameGrid(db);
    }
    
    public static MainFrame getMainFrame() {
        return mainFrame;
    }
    
    public static NameGrid getNameGrid() {
        return nGrid;
    }
    
    public static void setStatus(String status) {
        statusBar.setText(status);
    }
    
    public void closeWindow() {
        if (JOptionPane.showConfirmDialog(this,"Are you sure you want to quit?","",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            setVisible(false);
            System.exit(0);
        }
    }
    
    public void windowClosing(WindowEvent e) {
        closeWindow();
    }
    
    public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
    
}
