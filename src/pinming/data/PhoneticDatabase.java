/*
 * PhoneticDatabase.java
 *
 * Created on August 6, 2007, 1:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.data;

import java.util.TreeMap;
import java.io.*;

/**
 *
 * @author OfficeRecorder
 */
public class PhoneticDatabase {
    
    private static TreeMap<Character,String> phoneticMap;
    
    private static File dataFile;
    
    private static boolean beenModified = false;
    
    /** Creates a new instance of PhoneticDatabase */
    private PhoneticDatabase() {}
        
    public static void initialize() throws FileNotFoundException, IOException, ClassNotFoundException {
        dataFile = new File("phonetics.dat");
        if (dataFile.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile));
            phoneticMap = (TreeMap<Character,String>) ois.readObject();
        }
        else {
            phoneticMap = new TreeMap<Character,String>();
        }
    }
    
    public static void export() throws IOException {
        dataFile.delete();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile));
        oos.writeObject(phoneticMap);
        beenModified = false;
    }
    
    public static void addPhonetic(char chineseChar, String pinyin) {
        phoneticMap.put(chineseChar,pinyin);
        beenModified = true;
    }
    
    public static String getPhonetic(char chineseChar) {
        return phoneticMap.get(chineseChar);
    }
    
    public static boolean modified() {
        return beenModified;
    }
    
}
