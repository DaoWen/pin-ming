/*
 * TransData.java
 *
 * Created on August 15, 2007, 5:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.input;

import pinming.conversions.*;

import java.awt.event.*;

/**
 *
 * @author OfficeRecorder
 */
public class TransData {
    
    private static final int INITIAL = 0, MIDDLE = 1, FINAL = 2;
    private char[] zhuyin = new char[3];
    private char hanzi;
    private String pinyin;
    private UpdateListener ul;
    
    /** Creates a new instance of TransData */
    public TransData(char chineseCharacter, UpdateListener listener) {
        hanzi = chineseCharacter;
        ul = listener;
    }
    
    private void doUpdate() {
        if (pinyin == null) {
            pinyin = PinZhuYin.toPinYin(getZhuYin());
            ul.pinyinUpdate();
            ul.zhuyinUpdate();
        }
        else if (zhuyin[INITIAL] == 0 && zhuyin[MIDDLE] == 0 && zhuyin[FINAL] == 0) {
            zhuyin = PinZhuYin.toZhuYinArray(pinyin);
            ul.zhuyinUpdate();
        }
    }
    
    public void setInitial(char init) {
        zhuyin[INITIAL] = init;
        pinyin = null;
        doUpdate();
    }
    
    public void setMiddle(char mid) {
        zhuyin[MIDDLE] = mid;
        pinyin = null;
        doUpdate();
    }
    
    public void setFinal(char fin) {
        zhuyin[FINAL] = fin;
        pinyin = null;
        doUpdate();
    }
    
    public void setPinYin(String pin) {
        pinyin = pin;
        clearZhuYin();
        doUpdate();
    }
    
    public String getPinYin() {
        return pinyin == null ? "" : pinyin;
    }
    
    public String getZhuYin() {
        return (new String(zhuyin)).trim();
    }
    
    public void clearZhuYin() {
        zhuyin[INITIAL] = zhuyin[MIDDLE] = zhuyin[FINAL] = 0;    
        doUpdate();
    }
    
    public char getChar() {
        return hanzi;
    }
    
}
