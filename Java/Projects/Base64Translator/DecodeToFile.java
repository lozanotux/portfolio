package soajp.utils.base64;

import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.bind.DatatypeConverter;

public class DecodeToFile {
    
    /**
     * prevents class instantiation.
     */
    private DecodeToFile() {
        super();
    }
    
    /**
     * 
     * @param base64
     * @param pathToFile
     * @return String message for OK or ERROR
     */
    public static String writeFile(String base64, String path) {
        // Run the api to perform the decoding
        byte[] rbytes = DatatypeConverter.parseBase64Binary(base64);

        // Put the location for the output file
        try {
            FileOutputStream os = new FileOutputStream(path);
            os.write(rbytes);
            return "File written successfully";
        } catch (IOException e) {  
            return("Decode To File Exception: " + e.getMessage());  
        }
    }
    
}
