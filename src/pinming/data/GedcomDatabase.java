/*
 * GedcomDatabase.java
 *
 * Created on July 25, 2007, 3:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.data;

import pinming.locale.*;
import pinming.conversions.PinZhuYin;

import java.util.*;
import java.io.*;
import java.util.regex.*;

/**
 *
 * @author OfficeRecorder
 */
public class GedcomDatabase {
    
    /** This is the first line of every GEDCOM file, so if this isn't there then it's not the right kind of file. */
    private static final String GEDCOM_HEADER = "0 HEAD";
    private static final String INDIVIDUAL_MARK = "@ INDI";
    private static final String FAMILY_MARK = "@ FAM";
    private static final String NAME_MARK = "1 NAME ";
    private static final String FONE_MARK = "2 ROMN ";
    
    private static final String EMPTY = "?";
    private static final String UTF_8 = "UTF-8";
    
    public static final int CJK_CODEPOINT = 0x3400;
    
    /** Used to store all the names in the GEDCOM database. */
    private ArrayList<NameEntry> nameEntries;    
    
    private File gedcomFile, tempFile;
    
    private BufferedReader gedcomReader;
    private BufferedWriter gedcomWriter;
    
    private int incompleteCount;
    
    /** Creates a new instance of GedcomDatabase */
    public GedcomDatabase(File gFile) throws InvalidGedcomFileException, UnsupportedEncodingException {
        nameEntries = new ArrayList<NameEntry>();
        gedcomFile = gFile;
        try {
            tempFile = File.createTempFile(gFile.getName(),null);
            tempFile.deleteOnExit();
        }
        catch (IOException e) {
            throw new InvalidGedcomFileException(String.format( Messages.get(Msg.GEDCOM_IO_ERROR),gedcomFile.getName() ));
        }
        try {
            gedcomWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), UTF_8));
            gedcomReader = new BufferedReader(new InputStreamReader(new FileInputStream(gedcomFile), UTF_8));
        }
        catch (FileNotFoundException e) {
            throw new InvalidGedcomFileException(String.format( Messages.get(Msg.GEDCOM_IO_ERROR),gedcomFile.getName() ));
        }
        try {
            String firstLine = gedcomReader.readLine();
            if (firstLine.indexOf(GEDCOM_HEADER) == -1) {
                throw new InvalidGedcomFileException(String.format( Messages.get(Msg.INVALID_GEDCOM),gedcomFile.getName() ));
            }
            gedcomWriter.write(firstLine);
            gedcomWriter.write('\n');
            readNames();
            nameEntries.trimToSize();
            gedcomWriter.close();
            gedcomReader.close();
        }
        catch (IOException e) {
            throw new InvalidGedcomFileException(String.format( Messages.get(Msg.GEDCOM_IO_ERROR),gedcomFile.getName() ));
        }
    }
    
    private void readNames() throws IOException, InvalidGedcomFileException {
        boolean possibleNameLine = false;
        boolean possibleFoneLine = false;
        boolean doneReadingData = false;
        String entryName = null, entryFone = null;
        String thisLine = gedcomReader.readLine();
        int lineNumber = 2; // We just read in the 2nd line
        // Writing a copy to a temp file
        gedcomWriter.write(thisLine);
        gedcomWriter.write('\n');
        while (thisLine != null) {
            if (!doneReadingData) {
                // Looking for a Name
                if (possibleNameLine) {
                    if (thisLine.indexOf(NAME_MARK) == 0) {
                        entryName = thisLine.substring(NAME_MARK.length());
                        possibleNameLine = false;
                        possibleFoneLine = true;
                    }
                }
                // Looking for a Phonetic Name
                else if (possibleFoneLine) {
                    if (thisLine.indexOf(FONE_MARK) == 0) {
                        entryFone = thisLine.substring(FONE_MARK.length());
                        nameEntries.add(new NameEntry(entryName,entryFone,lineNumber));
                        possibleFoneLine = false;
                    }
                    else if (thisLine.charAt(0) != '2') {
                        possibleFoneLine = false;
                        nameEntries.add(new NameEntry(entryName,null,lineNumber));
                        continue;
                    }
                }
                // Looking for a new Individual
                else if (thisLine.charAt(0) == '0') {
                    if (thisLine.indexOf(INDIVIDUAL_MARK) > 0) {
                        entryName = null;
                        entryFone = null;
                        possibleFoneLine = false;
                        possibleNameLine = true;
                    }
                    else if (thisLine.indexOf(FAMILY_MARK) > 0) {
                        doneReadingData = true;
                    }
                }
            }
            thisLine = gedcomReader.readLine();
            lineNumber++;
            if (thisLine != null) {
                // Writing a copy to a temp file
                gedcomWriter.write(thisLine);
                gedcomWriter.write('\n');
            }
        }
    }
    
    public void saveFile() throws InvalidGedcomFileException, UnsupportedEncodingException {
        try {
            gedcomWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(gedcomFile), UTF_8));
            gedcomReader = new BufferedReader(new InputStreamReader(new FileInputStream(tempFile), UTF_8));
        }
        catch (FileNotFoundException e) {
            throw new InvalidGedcomFileException(String.format( Messages.get(Msg.GEDCOM_IO_ERROR),gedcomFile.getName() ));
        }
        try {
            String line = gedcomReader.readLine();
            int lineNum = 1; // Starts at 1
            Iterator<NameEntry> dataEntries = nameEntries.iterator();
            NameEntry entry = dataEntries.next();
            while (line != null) {
                if (entry != null && entry.foneLine() == lineNum) {
                    gedcomWriter.write(FONE_MARK);
                    gedcomWriter.write(entry.pinyinName());
                    gedcomWriter.write('\n');
                    if (entry.addFone()) {
                        gedcomWriter.write(line);
                        gedcomWriter.write('\n');
                    }
                    entry = dataEntries.hasNext() ? dataEntries.next() : null;
                }
                else {
                    gedcomWriter.write(line);
                    gedcomWriter.write('\n');
                }
                line = gedcomReader.readLine();
                lineNum++;
                gedcomWriter.flush();
            }
            gedcomWriter.close();
            gedcomReader.close();
        }
        catch (IOException e) {
            throw new InvalidGedcomFileException(String.format( Messages.get(Msg.GEDCOM_IO_ERROR),gedcomFile.getName() ));
        }
    }
    
    public NameEntry getEntry(int index) {
        return nameEntries.get(index);
    }
    
    public int size() {
        return nameEntries.size();
    }
    
    public int incompleteSize() {
        return incompleteCount;
    }
    
    /** Data structure for storing the GEDCOM name entries. */
    public class NameEntry {
        private char[] characterName;
        private String[] phoneticName;
        private int phoneticLineNum;
        private boolean addPhonetic, incompletePinYin = false;
        private int startSlash = -1, endSlash = -1;

        /** Creates a new instance of GedcomNameEntry */
        public NameEntry(String charName, String foneName, int lineNum) {
            characterName = charName.toCharArray();
            int charCount = 0;
            for (int i=0;i<characterName.length;i++) {
                if (characterName[i] >= CJK_CODEPOINT) {
                    charCount++;
                }
                else if (characterName[i] == '/') {
                    if (startSlash == -1) {
                        startSlash = charCount;
                    }
                    else if (endSlash == -1) {
                        endSlash = charCount;
                    }
                    else {
                        System.err.println("Why does \"" + charName + "\" have so many slashes?");
                    }
                }
            }
            phoneticName = new String[charCount];
            for (int i=0,j=0;j<charCount;i++) {
                if (characterName[i] >= CJK_CODEPOINT) {
                    phoneticName[j] = PhoneticDatabase.getPhonetic(characterName[i]);
                    if (phoneticName[j] == null) {
                        phoneticName[j] = EMPTY;
                        incompletePinYin = true;
                        incompleteCount++;
                    }
                    j++;
                }
            }
            addPhonetic = foneName == null;
            phoneticLineNum = lineNum;
        }
        
        private void setComplete(boolean isComplete) {
            if (incompletePinYin == isComplete) { // if it's changing
                incompleteCount += isComplete ? -1 : 1;
                incompletePinYin = !isComplete;
            }
        }
        
        public String charName() {
            return new String(characterName);
        }
        
        private String foneName(boolean pinyin) {
            if (phoneticName != null) {
                StringBuffer val = new StringBuffer();
                int i;
                for (i=0; i<phoneticName.length;i++) {
                    if (i == startSlash) {
                        val.append('/');
                    }
                    if (i == endSlash) {
                        val.append('/');
                    }
                    if (i != 0) {
                        val.append(' ');
                    }
                    if (pinyin) {
                        val.append(phoneticName[i]);
                    }
                    else {
                        if (phoneticName[i] == null) {
                            int asd = 1;
                        }
                            String zh = PinZhuYin.toZhuYin(phoneticName[i]);
                            val.append(zh == null ? '?' : zh);
                    }
                }
                if (i == startSlash) {
                    val.append('/');
                }
                if (i == endSlash) {
                    val.append('/');
                }
                return val.toString();
            }
            return null;
        }
        
        public void updatePinYin(int index, String pinyin) {
          phoneticName[index] = pinyin;
          if (incompletePinYin) {
              boolean incomplete = true;
              for (int i=0;i<phoneticName.length;i++) {
                incomplete = incomplete && (phoneticName[i] == EMPTY);
              }
              if (!incomplete) {
                setComplete(true);
              }
          }
        }
        
        public String pinyinName() {
            return foneName(true);
        }
        
        public String zhuyinName() {
            return foneName(false);
        }
        
        public boolean incomplete() {
            return incompletePinYin;
        }
        
        public int foneLine() {
            return phoneticLineNum;
        }
        
        public boolean addFone() {
            return addPhonetic;
        }
        
        public String toString() {
            return "{\"" + charName() + "\", \"" + pinyinName() + "\"}";
        }
        
        public String getPinYinAt(int index) {
            return phoneticName[index];
        }
        
    }
    
}
