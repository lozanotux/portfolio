package soajp.utils.base64;

import java.util.Base64.Encoder;

public final class EncodeContent {
    
    /**
     * prevents class instantiation.
     */
    private EncodeContent() {
        super();
    }
    
    /**
     * 
     * @param contentString
     * @return Encoded String
     */
    public static String getEncodedString(String value) {

        try {
            byte[] contentBytes = value.getBytes();
            Encoder encoder = java.util.Base64.getEncoder();
            String encodedStr = encoder.encodeToString(contentBytes);  
            return encodedStr;
        } catch (Exception e) {  
            return("Encode Content Exception: " + e.getMessage());  
        } 
  }
}