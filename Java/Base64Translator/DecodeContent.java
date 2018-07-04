package soajp.utils.base64;

import java.nio.charset.StandardCharsets;
import java.util.Base64.Decoder;

public final class DecodeContent {
    
    /**
     * prevents class instantiation.
     */
    private DecodeContent() {
        super();
    }
    
    /**
     * 
     * @param base64
     * @return The content of the Base64
     */
    public static String getStringContent(String base64) {
        try {
            Decoder decoder = java.util.Base64.getDecoder();
            byte[] decodedValue = decoder.decode(base64);
            return new String(decodedValue, StandardCharsets.UTF_8);
        } catch (Exception e) {  
            return("Decode Content Exception: " + e.getMessage());  
        }
    }
    
}
