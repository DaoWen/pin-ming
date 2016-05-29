/*
 * MainMenu.java
 *
 * Created on August 1, 2007, 2:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.gui;

import pinming.data.*;
import pinming.locale.*;
import pinming.gui.editor.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 *
 * @author OfficeRecorder
 */
public class MainMenu extends JMenuBar implements ActionListener {
    
    private static final FileFilter gedcomFileFilter = new FileNameExtensionFilter("GEDCOM Files","ged");
    
    private static final String OPEN_FILE = "O", SAVE_FILE = "S", QUIT = "Q", EDIT_CHAR = "E",
            JUMP_DATA = "J", EXPORT_DATA = "X", ABOUT_HELP = "A";
    
    private GedcomDatabase gedcomData;
    
    /** Creates a new instance of MainMenu */
    public MainMenu() {
        super();
        // File Menu
        JMenu fileMenu = new JMenu("File");
        // File -> Open
        JMenuItem openFileItem = new JMenuItem("Open new Gedcom File");
        openFileItem.addActionListener(this);
        openFileItem.setActionCommand(OPEN_FILE);
        fileMenu.add(openFileItem);
        add(fileMenu);
        // File -> Save
        JMenuItem saveFileItem = new JMenuItem("Save Gedcom File");
        saveFileItem.addActionListener(this);
        saveFileItem.setActionCommand(SAVE_FILE);
        fileMenu.add(saveFileItem);
        add(fileMenu);
        // File -> Quit
        JMenuItem quitFileItem = new JMenuItem("Quit");
        quitFileItem.addActionListener(this);
        quitFileItem.setActionCommand(QUIT);
        fileMenu.add(quitFileItem);
        // Data Menu
        JMenu dataMenu = new JMenu("Database");
        // Data -> Edit Character Phonetic
        JMenuItem editDataItem = new JMenuItem("Edit Character Phonetic");
        editDataItem.addActionListener(this);
        editDataItem.setActionCommand(EDIT_CHAR);
        dataMenu.add(editDataItem);
        add(dataMenu);
        // Data -> Jump to Incomplete Entry
        JMenuItem jumpDataItem = new JMenuItem("Jump to Incomplete Entry");
        jumpDataItem.addActionListener(this);
        jumpDataItem.setActionCommand(JUMP_DATA);
        dataMenu.add(jumpDataItem);
        // Data -> Export Database
        JMenuItem exportDataItem = new JMenuItem("Save Phonetic Database");
        exportDataItem.addActionListener(this);
        exportDataItem.setActionCommand(EXPORT_DATA);
        dataMenu.add(exportDataItem);
        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        // Help -> About
        JMenuItem aboutHelpItem = new JMenuItem("About");
        aboutHelpItem.addActionListener(this);
        aboutHelpItem.setActionCommand(ABOUT_HELP);
        helpMenu.add(aboutHelpItem);
        add(helpMenu);
    }
    
    public void actionPerformed(ActionEvent ae) {
        String cmd = ae.getActionCommand();
        if (cmd == OPEN_FILE) {
            JFileChooser openFileDialog = new JFileChooser();
            openFileDialog.setFileFilter(gedcomFileFilter);
            if (openFileDialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    gedcomData = new GedcomDatabase(openFileDialog.getSelectedFile());
                    MainFrame.fillNameGrid(gedcomData);
                }
                catch (InvalidGedcomFileException ioe) {
                    JOptionPane.showMessageDialog(this,ioe.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
                catch (java.io.UnsupportedEncodingException ioe) {
                    JOptionPane.showMessageDialog(this,Messages.get(Msg.NO_UTF_SUPPORT),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
            MainFrame.setStatus("Incomplete Entries: " + gedcomData.incompleteSize());
        }
        else if (cmd == SAVE_FILE) {
            if (gedcomData != null) {
                try {
                    gedcomData.saveFile();
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame.getMainFrame(),e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(MainFrame.getMainFrame(),"No data to save.","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (cmd == QUIT) {
            MainFrame.getMainFrame().closeWindow();
        }
        else if (cmd == EDIT_CHAR) {
            String msg = "Enter the character that you want to edit:";
            String input = JOptionPane.showInputDialog(MainFrame.getMainFrame(),msg,"Edit Character Phonetic",JOptionPane.QUESTION_MESSAGE);
            if (input != null && input.length() > 0) {
                char editChar = input.charAt(0);
                new PhoneticEditorFrame(MainFrame.getMainFrame(),editChar);
            }
        }
        else if (cmd == JUMP_DATA) {
            MainFrame.getNameGrid().jumpToIncomplete();
        }
        else if (cmd == EXPORT_DATA) {
            if (PhoneticDatabase.modified()) {
                try {
                    PhoneticDatabase.export();
                    JOptionPane.showMessageDialog(MainFrame.getMainFrame(),"Saved successfully!");
                }
                catch (java.io.IOException ioe) {
                    JOptionPane.showMessageDialog(MainFrame.getMainFrame(),"ERROR: "+ioe.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(MainFrame.getMainFrame(),"There's no new data to export!");
            }
        }
        else if (cmd == ABOUT_HELP) {
            new AboutFrame(MainFrame.getMainFrame());
        }
    }
    
}
