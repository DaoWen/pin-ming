/**
 * ZhuYinListener.java
 *
 * Created on July 18, 2007, 9:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.input;

import pinming.conversions.PinZhuYin;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author OfficeRecorder
 */
public class ZhuYinListener implements KeyListener {
    
    private TransData tDat;
    
    /** Creates a new instance of ZhuYinListener */
    public ZhuYinListener() {}
    
    public void setDataHolder(TransData dataHolder) {
        tDat = dataHolder;
    }
    
    public TransData getDataHolder() {
        return tDat;
    }
    
    public void keyPressed(KeyEvent e) {
        if (tDat == null) {
            return;
        }
        switch (e.getKeyCode()) {
            // Inintials
            case KeyEvent.VK_1:
              tDat.setInitial(PhoneticTranslator.ZY_B);
              break;
            case KeyEvent.VK_Q:
              tDat.setInitial(PhoneticTranslator.ZY_P);
              break;
            case KeyEvent.VK_A:
              tDat.setInitial(PhoneticTranslator.ZY_M);
              break;
            case KeyEvent.VK_Z:
              tDat.setInitial(PhoneticTranslator.ZY_F);
              break;
            case KeyEvent.VK_2:
              tDat.setInitial(PhoneticTranslator.ZY_D);
              break;
            case KeyEvent.VK_W:
              tDat.setInitial(PhoneticTranslator.ZY_T);
              break;
            case KeyEvent.VK_S:
              tDat.setInitial(PhoneticTranslator.ZY_N);
              break;
            case KeyEvent.VK_X:
              tDat.setInitial(PhoneticTranslator.ZY_L);
              break;
            case KeyEvent.VK_E:
              tDat.setInitial(PhoneticTranslator.ZY_G);
              break;
            case KeyEvent.VK_D:
              tDat.setInitial(PhoneticTranslator.ZY_K);
              break;
            case KeyEvent.VK_C:
              tDat.setInitial(PhoneticTranslator.ZY_H);
              break;
            case KeyEvent.VK_R:
              tDat.setInitial(PhoneticTranslator.ZY_J);
              break;
            case KeyEvent.VK_F:
              tDat.setInitial(PhoneticTranslator.ZY_Q);
              break;
            case KeyEvent.VK_V:
              tDat.setInitial(PhoneticTranslator.ZY_X);
              break;
            case KeyEvent.VK_5:
              tDat.setInitial(PhoneticTranslator.ZY_ZH);
              break;
            case KeyEvent.VK_T:
              tDat.setInitial(PhoneticTranslator.ZY_CH);
              break;
            case KeyEvent.VK_G:
              tDat.setInitial(PhoneticTranslator.ZY_SH);
              break;
            case KeyEvent.VK_B:
              tDat.setInitial(PhoneticTranslator.ZY_R);
              break;
            case KeyEvent.VK_Y:
              tDat.setInitial(PhoneticTranslator.ZY_Z);
              break;
            case KeyEvent.VK_H:
              tDat.setInitial(PhoneticTranslator.ZY_C);
              break;
            case KeyEvent.VK_N:
              tDat.setInitial(PhoneticTranslator.ZY_S);
              break;
            // Middles
            case KeyEvent.VK_U:
              tDat.setMiddle(PhoneticTranslator.ZY_I);
              break;
            case KeyEvent.VK_J:
              tDat.setMiddle(PhoneticTranslator.ZY_U);
              break;
            case KeyEvent.VK_M:
              tDat.setMiddle(PhoneticTranslator.ZY_YU);
              break;
            // Finals
            case KeyEvent.VK_8:
              tDat.setFinal(PhoneticTranslator.ZY_A);
              break;
            case KeyEvent.VK_I:
              tDat.setFinal(PhoneticTranslator.ZY_O);
              break;
            case KeyEvent.VK_K:
              tDat.setFinal(PhoneticTranslator.ZY_E);
              break;
            case KeyEvent.VK_COMMA:
              tDat.setFinal(PhoneticTranslator.ZY_EH);
              break;
            case KeyEvent.VK_9:
              tDat.setFinal(PhoneticTranslator.ZY_AI);
              break;
            case KeyEvent.VK_O:
              tDat.setFinal(PhoneticTranslator.ZY_EI);
              break;
            case KeyEvent.VK_L:
              tDat.setFinal(PhoneticTranslator.ZY_AO);
              break;
            case KeyEvent.VK_PERIOD:
              tDat.setFinal(PhoneticTranslator.ZY_OU);
              break;
            case KeyEvent.VK_0:
              tDat.setFinal(PhoneticTranslator.ZY_AN);
              break;
            case KeyEvent.VK_P:
              tDat.setFinal(PhoneticTranslator.ZY_EN);
              break;
            case KeyEvent.VK_SEMICOLON:
              tDat.setFinal(PhoneticTranslator.ZY_ANG);
              break;
            case KeyEvent.VK_SLASH:
              tDat.setFinal(PhoneticTranslator.ZY_ENG);
              break;
            case KeyEvent.VK_MINUS:
              tDat.setFinal(PhoneticTranslator.ZY_ER);
              break;
            case KeyEvent.VK_DELETE:
            case KeyEvent.VK_BACK_SPACE:
                tDat.clearZhuYin();
        }
    }
    
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    
}
