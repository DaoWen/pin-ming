/*
 * PhoneticEditorFrame.java
 *
 * Created on August 15, 2007, 4:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.gui.editor;

import pinming.input.*;
import pinming.data.*;
import pinming.conversions.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 *
 * @author OfficeRecorder
 */
public class PhoneticEditorFrame extends JDialog implements ActionListener, MouseListener, UpdateListener {
    
    private static final int PAD = 10;
    private static final Font inputFont = new Font(Font.SANS_SERIF,Font.PLAIN,20);
    private static Color HIGHLIGHT_COLOR = Color.WHITE, NORMAL_COLOR = new Color(238,238,238);
    private static final String OK = "O", CANCEL = "C";
    
    private char editCharacter;
    private boolean focusZhuYin = false;
    private JPanel zhuYinWrapper;
    private JLabel lblZhuYin;
    private JTextField txtPinYin;
    private ZhuYinListener zhuYinListener;
    private TransData td;
    
    /**
     * Creates a new instance of PhoneticEditorFrame
     */
    public PhoneticEditorFrame(Frame owner, char editChar) {
        super(owner,"Edit Entry",true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // TransData
        JPanel gc = (JPanel) getContentPane();
        td = new TransData(editChar,this);
        zhuYinListener = new ZhuYinListener();
        editCharacter = editChar;
        // Message 
        JPanel messagePanel = new JPanel();
        String msg = "<html><center>The current phonetic for " + editChar + " is \"" + PhoneticDatabase.getPhonetic(editChar) + "\".<br>What would you like to change it to?</center></html>";
        JLabel lblMsg = new JLabel(msg);
        messagePanel.add(lblMsg);
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
        zhuYinWrapper.setBorder(BorderFactory.createEtchedBorder());        
        lblZhuYin = new JLabel();
        lblZhuYin.setFont(inputFont);
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
        txtPinYin.setFont(inputFont);
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
        gc.add(messagePanel,BorderLayout.NORTH);
        gc.add(centerPanel,BorderLayout.CENTER);
        gc.add(buttonPanel,BorderLayout.SOUTH);
        gc.setBorder(BorderFactory.createEmptyBorder(PAD,PAD,PAD,PAD));
        btnOK.addKeyListener(zhuYinListener);
        btnCancel.addKeyListener(zhuYinListener);
        // Display
        pack();
        setVisible(true);
    }
    
    public void pinyinUpdate() {
        txtPinYin.setText(td.getPinYin());
    }
    
    public void zhuyinUpdate() {
        lblZhuYin.setText(td.getZhuYin());
    }
    
    private void setZhuYinFocus(boolean hasFocus) {
      if (hasFocus != focusZhuYin) {
          zhuYinWrapper.setBackground(hasFocus ? HIGHLIGHT_COLOR : NORMAL_COLOR);
          txtPinYin.setEnabled(!hasFocus);
          txtPinYin.setBackground(hasFocus ? NORMAL_COLOR : HIGHLIGHT_COLOR);
          if (hasFocus) {
              zhuYinListener.setDataHolder(td);
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
            if (PinZhuYin.toZhuYin(txtPinYin.getText()) == null) {
                if (JOptionPane.showConfirmDialog(this,"This is not valid PinYin.  Are you sure you want to save it?","Invalid PinYin",JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            PhoneticDatabase.addPhonetic(editCharacter,txtPinYin.getText());
            setVisible(false);
            dispose();
        }
        else if (cmd == CANCEL) {
            setVisible(false);
            dispose();
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        setZhuYinFocus(e.getSource() == lblZhuYin);
    }
    
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}    
    
    public boolean editingPinYin() {
        return txtPinYin.isEnabled();
    }
    
    private class PinYinDocument extends PlainDocument {
        
        public PinYinDocument() {
            super();
        }
        
        public void insertString(int offset, String string, AttributeSet attributes) throws BadLocationException {
            super.insertString(offset,string.toUpperCase(),attributes);
            if (editingPinYin()) {
                td.setPinYin(this.getText(0,this.getLength()));
            }
        }
        public void remove(int offset, int length) throws BadLocationException {
            super.remove(offset,length);
            if (editingPinYin()) {
                td.setPinYin(this.getText(0,this.getLength()));
            }
        }
    }
}
