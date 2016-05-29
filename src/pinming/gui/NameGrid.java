/*
 * NameGrid.java
 *
 * Created on August 1, 2007, 3:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.gui;

import pinming.data.*;
import pinming.conversions.*;
import pinming.gui.editor.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;

/**
 *
 * @author OfficeRecorder
 */
public class NameGrid extends JPanel implements AdjustmentListener {
    
    private JPanel nameGrid;
    private static final int ROWS = 10, COLS = 3;
    private JScrollBar nameGridScroller;
    private GedcomDatabase gdb;
    private GridRow[] gridRows;
    
    private int gridTopIndex = -1, highlightedRow = -1;
    
    
    /** Creates a new instance of NameGrid */
    public NameGrid() {
        super(new BorderLayout());
        nameGrid = new JPanel(new GridLayout(ROWS,COLS,0,0));
        Border outer = BorderFactory.createLoweredBevelBorder();
        Border pad = BorderFactory.createEmptyBorder(3,3,0,0);
        nameGrid.setBorder(BorderFactory.createCompoundBorder(outer,pad));
        gridRows = new GridRow[ROWS];
        for (int i=0;i<ROWS;i++) {
            gridRows[i] = new GridRow(this, i);
        }
        nameGridScroller = new JScrollBar(JScrollBar.VERTICAL,0,0,0,0);
        add(nameGridScroller,BorderLayout.EAST);
        add(nameGrid,BorderLayout.CENTER);
    }
    
    public void fillNameGrid(GedcomDatabase db) {
        gdb = db;
        GedcomDatabase.NameEntry entry;
        setGridData(0);
        gridTopIndex = 0;
        setHighlightedRow(0);
        nameGridScroller.setValues(0,1,0,(gdb.size() > ROWS ? gdb.size() : 0));
        nameGridScroller.addAdjustmentListener(this);
    }
    
    private void setGridData(int startRow) {
        gridTopIndex = startRow;
        GedcomDatabase.NameEntry entry;        
        for (int i=0;i<ROWS;i++) {
            if (i+startRow < gdb.size()) {
                gridRows[i].changeEntry(gdb.getEntry(i+startRow));
            }
            else {
                gridRows[i].changeEntry(null);
            }
        }
    }
    
    /** Used to add a cell (text label) to the grid. */
    public void addCell(Component c) {
        nameGrid.add(c);
    }
    
    public void adjustmentValueChanged(AdjustmentEvent e) {
        int index = e.getValue(), newHighlight = highlightedRow - (index - gridTopIndex);
        setGridData(index);
        if (newHighlight >= ROWS) {
            setHighlightedRow(ROWS-1);
        }
        else if (newHighlight < 0) {
            setHighlightedRow(0);
        }
        else {
            setHighlightedRow(newHighlight);
        }
    }
    
    public void rowClicked(int row) {
        if (!gridRows[row].isBlank()) {
            setHighlightedRow(row);
        }
    }
    
    public void rowDoubleClicked(int row) {
        // Display editor frame
        if (!gridRows[row].isBlank()) {
            new NameEditorFrame((Frame)MainFrame.getMainFrame(),gridRows[row].getEntry());
        }
    }
    
    public int getHighlightedRow() {
        return highlightedRow;
    }
    
    public void setHighlightedRow(int row) {
        if (highlightedRow > -1) {
            gridRows[highlightedRow].setHighlight(false);
        }
        gridRows[row].setHighlight(true);
        highlightedRow = row;
    }
    
    public void refresh() {
        int hi = highlightedRow;
        setGridData(gridTopIndex);
        setHighlightedRow(hi);
        MainFrame.setStatus("Incomplete Entries: " + gdb.incompleteSize());
    }
    
    public void jumpToIncomplete() {
        if (gdb != null && gdb.incompleteSize() > 0) {
            for (int i=0;i<gdb.size();i++) {
                if (gdb.getEntry(i).incomplete()) {
                    setGridData(i);
                    setHighlightedRow(0);
                    nameGridScroller.setValue(i);
                    break;
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(MainFrame.getMainFrame(),"There are no incomplete entries!","",JOptionPane.PLAIN_MESSAGE);
        }
    }
    
}