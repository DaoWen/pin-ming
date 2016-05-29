/*
 * GridRow.java
 *
 * Created on August 10, 2007, 12:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.gui;

import pinming.data.GedcomDatabase;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author OfficeRecorder
 */
public class GridRow implements MouseListener {
    
    /** These are bit-flags for the displayState variable */
    private static int NONE = 0, HIGHLIGHT = 1, INCOMPLETE = 2;
    
    /** These are the indecies for the nameLabels,
     * so characters come first, then zhuyin, then pinyin.
     */
    private static int CHAR = 0, ZHU = 1, PIN = 2;
    
    /** Default font for the name displays. */
    private static final Font defaultFont = new Font(Font.SANS_SERIF,Font.PLAIN,20);
    
    /** Text colors for various display states. */
    private static final Color DEFAULT_COLOR = Color.BLACK, HIGHLIGHT_COLOR = Color.BLUE, INCOMPLETE_COLOR = Color.ORANGE;
    
    /** Border for the name labels. */
    private static final Border textBorder = BorderFactory.createEtchedBorder();
    
    /** Which row in the grid is this? */
    private int displayRow = -1;
    
    /** What is my display state? */
    private int displayState;
    
    /** The NameGrid that this GridRow will be added to */
    private NameGrid nameGrid;
    
    /** This is where the GridRow re-directs the NameLabels' modified mouse events. */
    private MouseListener parentListener;
    
    /** The entry from the Gedcom data currently displayed in this row.
     *  This reference should point to the entry in the Gedcom database,
     *  not a copy, otherwise changes made here won't do anything.
     */
    private GedcomDatabase.NameEntry dataEntry;
    
    /** Stores the three cells to display the character, zhuyin and pinyin. */
    private NameLabel[] nameLabels;
    
    private class NameLabel extends JTextArea {
        
        public NameLabel() {
            setBorder(textBorder);
            setEditable(false);
            setLineWrap(true);
            setFont(defaultFont);
        }
        
        private void updateStatus() {
            setForeground((displayState & HIGHLIGHT) == HIGHLIGHT ? HIGHLIGHT_COLOR :
                ((displayState & INCOMPLETE) == INCOMPLETE ? INCOMPLETE_COLOR : DEFAULT_COLOR));
        }
        
    }
    
    /** Creates a new instance of GridRow */
    public GridRow(NameGrid grid, int rowIndex) {
        nameGrid = grid;
        displayRow = rowIndex;
        nameLabels = new NameLabel[3];
        for (int i=CHAR;i<=PIN;i++) {
            nameLabels[i] = new NameLabel();
            nameLabels[i].addMouseListener(this);
            grid.addCell(nameLabels[i]);
        }
    }

    /** Display a different data entry in this row. */
    public void changeEntry(GedcomDatabase.NameEntry entry) {
        dataEntry = entry;
        if (entry != null) {
            displayState = entry.incomplete() ? INCOMPLETE : NONE;
            nameLabels[CHAR].setText(entry.charName());
            nameLabels[ZHU].setText(entry.zhuyinName());
            nameLabels[PIN].setText(entry.pinyinName());
        }
        else {
            displayState = NONE;
            nameLabels[CHAR].setText("");
            nameLabels[ZHU].setText("");
            nameLabels[PIN].setText("");
        }
        for (NameLabel de : nameLabels) {
            de.updateStatus();
        }
    }
    
    public void setHighlight(boolean on) {
        displayState = on ? (displayState | HIGHLIGHT) : (displayState & ~HIGHLIGHT);
        for (NameLabel l : nameLabels) {
            l.updateStatus();
        }
    }
    
    public GedcomDatabase.NameEntry getEntry() {
        return dataEntry;
    }
    
    public boolean isBlank() {
        return dataEntry == null;
    }
    
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            nameGrid.rowClicked(displayRow);
        }
        else if (e.getClickCount() == 2) {
            nameGrid.rowDoubleClicked(displayRow);
        }
    }
    
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    
}
