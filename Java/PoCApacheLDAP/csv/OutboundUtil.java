package soajp.csv;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.xml.bind.DatatypeConverter;

public class OutboundUtil implements IOutboundUtil {
    public OutboundUtil() {
        super();
    }
    @Override
    public ArrayList<CsvStruct> getZipContentAsOpaqueList(String base64) {
        ArrayList<CsvStruct> opaque_fileList;
        opaque_fileList = new ArrayList();
        
        try {
            byte[] rbytes = DatatypeConverter.parseBase64Binary(base64);  
  
            InputStream stream = new ByteArrayInputStream(rbytes);  
            ZipInputStream zipStream = new ZipInputStream(stream);

            ZipEntry entry;
  
            while ((entry = zipStream.getNextEntry()) != null) {
                CsvStruct cvs = new CsvStruct();
                
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count;

                while ((count = zipStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                }

                cvs.setFileName(entry.getName());
                

                byte[] bytes = baos.toByteArray();
                
                cvs.setFileContent(DatatypeConverter.printBase64Binary(bytes));
                opaque_fileList.add(cvs);
                
                zipStream.closeEntry();
            }  
  
            return opaque_fileList;  
        } catch (IOException ex) {  
            ex.printStackTrace();
            return opaque_fileList;
        }
    }
}
