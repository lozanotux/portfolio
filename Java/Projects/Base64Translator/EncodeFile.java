package soajp.utils.base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.bind.DatatypeConverter;

public class EncodeFile {
    
    /**
     * prevents class instantiation.
     */
    private EncodeFile() {
        super();
    }
    
    /**
     * 
     * @param pathToFile
     * @return Encoded String from the File 
     */
    public static String getEncodedContentFile(String pathToFile) {
        try {
            File fileRef = new File(pathToFile);
            byte[] bytes = loadFile(fileRef);
            String encoded = DatatypeConverter.printBase64Binary(bytes);
            return encoded;
        } catch (IOException e) {  
            return("Encode File Exception: " + e.getMessage());  
        }
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        
        if (length > Integer.MAX_VALUE) {
          throw new IOException("File too large " + file.getName() + " - length: " + length);
        }
        
        byte[] bytes = new byte[(int)length];
        int offset = 0;
        int numRead = 0;
        
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        
        is.close();
        return bytes;
    }
}
