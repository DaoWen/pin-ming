/*
 * Messages.java
 *
 * Created on July 27, 2007, 5:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.locale;

import java.util.ArrayList;

/**
 *
 * @author OfficeRecorder
 */
public class Messages {
    
    private static ArrayList<String> errorMessages;
    
    /** Creates a new instance of Messages */
    private Messages() {}
    
    public static void initialize() {
        errorMessages = new ArrayList<String>();
        errorMessages.ensureCapacity(Msg.values().length);
        errorMessages.add(Msg.INVALID_GEDCOM.ordinal(),"\"%s\" is not a valid GEDCOM file.");
        errorMessages.add(Msg.GEDCOM_IO_ERROR.ordinal(),"\"%s\" is not accessible.");
        errorMessages.add(Msg.NO_UTF_SUPPORT.ordinal(),"You do not have UTF-8 support.");
    }
    
    public static String get(Msg msgNumber) {
        return errorMessages.get(msgNumber.ordinal());
    }
    
}
