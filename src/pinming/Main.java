/*
 * Main.java
 *
 * Created on July 25, 2007, 3:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming;

import javax.swing.JOptionPane;

/**
 *
 * @author OfficeRecorder
 */
public class Main {
    
    /** Creates a new instance of Main */
    private Main() {}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Initialize the static classes
        pinming.locale.Messages.initialize();
        pinming.conversions.PinZhuYin.initialize();
        try {
            pinming.data.PhoneticDatabase.initialize();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        // Create the window
        new pinming.gui.MainFrame();
    }
    
}
