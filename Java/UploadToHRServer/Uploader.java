package oracle.hr.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceResponse;

public class Uploader implements IUploadFile {
    
    public Uploader() {
        super();
    }
    
    public int record(String url, 
                      String user,
                      String pass,
                      String sourceFileFQP, 
                      String contentType, 
                      String dDocTitle, 
                      String dDocAuthor, 
                      String dSecurityGroup, 
                      String dDocAccount, 
                      String dDocName) {
        
        InputStream      is = null;
        IdcClient        idcClient;
        IdcClientManager m_clientManager;
        IdcContext       userContext;
        
        try { 
            String fileName = sourceFileFQP.substring(sourceFileFQP.lastIndexOf('/') + 1);
            is = new FileInputStream(sourceFileFQP);
            
            m_clientManager = new IdcClientManager();
            idcClient = m_clientManager.createClient(url);                

            userContext = new IdcContext(user, pass);
            
            long fileLength = new File(sourceFileFQP).length();
            
            TransferFile primaryFile = new TransferFile();
            primaryFile.setInputStream(is);
            primaryFile.setContentType(contentType);
            primaryFile.setFileName(fileName);
            primaryFile.setContentLength(fileLength); 
            
            
            DataBinder request = idcClient.createBinder();
            
            request.putLocal("IdcService", "CHECKIN_UNIVERSAL");
            request.addFile("primaryFile", primaryFile);
            request.putLocal("dDocTitle", dDocTitle);
            request.putLocal("dDocAuthor", dDocAuthor);
            request.putLocal("dDocType", contentType);
            request.putLocal("dSecurityGroup", dSecurityGroup); 
            request.putLocal("dDocAccount", dDocAccount == null ? "" : dDocAccount);
            
            if (dDocName != null && dDocName.trim().length() > 0) {
                request.putLocal("dDocName", dDocName);
            } 
            
            ServiceResponse response = idcClient.sendRequest(userContext, request);
            DataBinder responseBinder = response.getResponseAsBinder();
            System.out.println (responseBinder.toString());
        } catch (IOException | IdcClientException e) {
            e.printStackTrace(System.out);
            return 1;
        } finally { 
            if (is != null) {
                try { 
                    is.close(); 
                } catch (IOException ignore) {
                    System.out.println(ignore.toString());
                } 
            } 
        }
        
        return 0;
    }
}
