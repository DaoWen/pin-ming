/*
 * NameEditorFrame.java
 *
 * Created on August 19, 2007, 7:56 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.gui.editor;

import pinming.gui.*;
import pinming.input.*;
import pinming.data.*;
import pinming.conversions.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;

/**
 *
 * @author OfficeRecorder
 */
public class NameEditorFrame extends JDialog implements ActionListener, MouseListener, UpdateListener {
    
    private static final int PAD_SIZE = 10;
    private static final Color DEFAULT_TEXT = Color.BLACK, HIGHLIGHT_TEXT = Color.BLUE;
    private static final Font INPUT_FONT = new Font(Font.SANS_SERIF,Font.PLAIN,20);
    private static Border OUTLINE = BorderFactory.createEtchedBorder();
    private static Border PAD = BorderFactory.createEmptyBorder(PAD_SIZE,PAD_SIZE,PAD_SIZE,PAD_SIZE);
    private static Border OUTLINE_PAD = BorderFactory.createCompoundBorder(OUTLINE,PAD);
    private static Color HIGHLIGHT_COLOR = Color.WHITE, NORMAL_COLOR = new Color(238,238,238);
    private static final String OK = "O", CANCEL = "C";
    
    private GedcomDatabase.NameEntry editEntry;
    private boolean focusZhuYin = false;
    private JPanel zhuYinWrapper;
    private JLabel lblZhuYin;
    private JTextField txtPinYin;
    private ZhuYinListener zhuYinListener;
    private TransData[] tds;
    private CharLabel[] charLabels;
    private int currentTd;
    private ArrayList<Character> characters = new ArrayList<Character>();
    private ArrayList<Integer> indecies = new ArrayList<Integer>();
    
    /**
     * Creates a new instance of PhoneticEditorFrame
     */
    public NameEditorFrame(Frame owner, GedcomDatabase.NameEntry entry) {
        super(owner,"Edit Name",true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        editEntry = entry;
        // TransData
        JPanel gc = (JPanel) getContentPane();
        JPanel charPanel = new JPanel(new FlowLayout());
        // Get Characters
        String chineseName = entry.charName();
        char thisChar;
        for (int i=0;i<chineseName.length();i++) {
            thisChar = chineseName.charAt(i);
            if (thisChar >= GedcomDatabase.CJK_CODEPOINT) {
                characters.add(thisChar);
                indecies.add(i);
            }
        }
        tds = new TransData[characters.size()];
        charLabels = new CharLabel[characters.size()];
        for (int i=0;i<tds.length;i++) {
            tds[i] = new TransData(characters.get(i),this);
            charLabels[i] = new CharLabel(i);
            charLabels[i].addMouseListener(this);
            charPanel.add(charLabels[i]);
        }
        gc.add(charPanel,BorderLayout.NORTH);
        zhuYinListener = new ZhuYinListener();
        // Input
        JPanel centerPanel = new JPanel(null);
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
        JPanel wrap1 = new JPanel();
        JLabel lblZhuYinLabel = new JLabel("<html><center>ZhuYin:</center></html>");
        wrap1.add(lblZhuYinLabel);
        centerPanel.add(wrap1);
        // For some reason the border directly on the label doesn't work right...
        zhuYinWrapper = new JPanel();
        // ... so I'm putting it on this instead.
        zhuYinWrapper.setBorder(OUTLINE);        
        lblZhuYin = new JLabel();
        lblZhuYin.setFont(INPUT_FONT);
        lblZhuYin.setHorizontalAlignment(JLabel.CENTER);
        lblZhuYin.setPreferredSize(new Dimension(250,26));
        lblZhuYin.addMouseListener(this);
        zhuYinWrapper.add(lblZhuYin);
        centerPanel.add(zhuYinWrapper);
        // PinYin
        JPanel wrap2 = new JPanel();
        JLabel lblPinYinLabel = new JLabel("<html><center>PinYin:</center></html>");
        wrap2.add(lblPinYinLabel);
        centerPanel.add(wrap2);
        txtPinYin = new JTextField();
        txtPinYin.setDocument(new PinYinDocument());
        txtPinYin.addMouseListener(this);
        txtPinYin.setHorizontalAlignment(JTextField.CENTER);
        txtPinYin.setFont(INPUT_FONT);
        centerPanel.add(txtPinYin);
        setZhuYinFocus(true);
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnOK = new JButton("OK");
        btnOK.addActionListener(this);
        btnOK.setActionCommand(OK);
        buttonPanel.add(btnOK);
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setActionCommand(CANCEL);
        buttonPanel.add(btnCancel);
        gc.add(centerPanel,BorderLayout.CENTER);
        gc.add(buttonPanel,BorderLayout.SOUTH);
        gc.setBorder(PAD);
        btnOK.addKeyListener(zhuYinListener);
        btnCancel.addKeyListener(zhuYinListener);
        // Display
        for (int i=0;i<indecies.size();i++) {
            tds[i].setPinYin(entry.getPinYinAt(i));
        }
        switchCharFocus(0);
        pack();
        setVisible(true);
    }
    
    public void switchCharFocus(int index) {
        charLabels[currentTd].setHighlight(false);
        charLabels[index].setHighlight(true);
        currentTd = index;
        zhuyinUpdate();
        pinyinUpdate();
    }
    
    public void pinyinUpdate() {
        txtPinYin.setText(tds[currentTd].getPinYin());
    }
    
    public void zhuyinUpdate() {
        lblZhuYin.setText(tds[currentTd].getZhuYin());
    }
    
    private void setZhuYinFocus(boolean hasFocus) {
      if (hasFocus != focusZhuYin) {
          zhuYinWrapper.setBackground(hasFocus ? HIGHLIGHT_COLOR : NORMAL_COLOR);
          txtPinYin.setEnabled(!hasFocus);
          txtPinYin.setBackground(hasFocus ? NORMAL_COLOR : HIGHLIGHT_COLOR);
          if (hasFocus) {
              zhuYinListener.setDataHolder(tds[currentTd]);
          }
          else {
              txtPinYin.requestFocus();
              zhuYinListener.setDataHolder(null);
          }
          focusZhuYin = hasFocus;
      }
    }
    
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd == OK) {
//            if (PinZhuYin.toZhuYin(txtPinYin.getText()) == null) {
//                if (JOptionPane.showConfirmDialog(this,"This is not valid PinYin.  Are you sure you want to save it?","Invalid PinYin",JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
//                    return;
//                }
//            }
            for (int i=0;i<tds.length;i++) {
                editEntry.updatePinYin(i,tds[i].getPinYin());
            }
            setVisible(false);
            dispose();
            MainFrame.getNameGrid().refresh();
        }
        else if (cmd == CANCEL) {
            setVisible(false);
            dispose();
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof CharLabel) {
            switchCharFocus(((CharLabel)e.getSource()).getIndex());
        }
        else {
            setZhuYinFocus(e.getSource() == lblZhuYin);
        }
    }
    
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}    
    
    public boolean editingPinYin() {
        return txtPinYin.isEnabled();
    }
    
    private class CharLabel extends JLabel {
        private TransData myTd;
        private int myIndex;
        
        public CharLabel(int i) {
            super(String.valueOf(tds[i].getChar()));
            setFont(INPUT_FONT);
            setBorder(OUTLINE_PAD);
            myIndex = i;
        }
        
        public void setHighlight(boolean on) {
           setForeground(on ? HIGHLIGHT_TEXT : DEFAULT_TEXT);
        }
        
        public int getIndex() {
          return myIndex;
        }
        
    }

    private class PinYinDocument extends PlainDocument {
        
        public PinYinDocument() {
            super();
        }
        
        public void insertString(int offset, String string, AttributeSet attributes) throws BadLocationException {
            super.insertString(offset,string.toUpperCase(),attributes);
            if (editingPinYin()) {
                tds[currentTd].setPinYin(this.getText(0,this.getLength()));
            }
        }
        public void remove(int offset, int length) throws BadLocationException {
            super.remove(offset,length);
            if (editingPinYin()) {
                tds[currentTd].setPinYin(this.getText(0,this.getLength()));
            }
        }
    }

}