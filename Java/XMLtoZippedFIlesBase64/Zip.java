package com.soajp.utils.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author jlozanop
 */
public class Zip {
    
    public static String getEncodedFile(String xmlAsString) {
        
        // Parsing entities to special character
        xmlAsString = xmlAsString.replaceAll("&lt;", "<");
        xmlAsString = xmlAsString.replaceAll("&gt;", ">");
        
        // Remove XML header
        xmlAsString = xmlAsString.indexOf("?>") > -1 ? xmlAsString.substring(xmlAsString.indexOf("?>") + 2) : xmlAsString;
        
        // Remove Comment
        xmlAsString = xmlAsString.indexOf("-->") > -1 ? xmlAsString.substring(xmlAsString.indexOf("-->") + 3) : xmlAsString;
        
        // Fix line break
        xmlAsString = xmlAsString.substring(xmlAsString.indexOf("<"));

        // Assign current namespace to a variable
        String ns;
        if(xmlAsString.indexOf(":xml-root-element") > -1) {
            // have namespace
            ns = xmlAsString.substring(xmlAsString.indexOf("<") + 1, xmlAsString.indexOf(":"));
            ns = ns + ":";
        } else {
            // not have namespace
            ns = "";
        }
        
        xmlAsString = xmlAsString.replaceAll(ns, "");
        
        // Build the XML
        Document doc;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlAsString)));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Zip.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        NodeList repetitiveElementArray = doc.getElementsByTagName("repetitive-element");
        NodeList nonRepetitiveElementArray = doc.getElementsByTagName("non-repetitive-element");
        Element xmlElement = (Element)nonRepetitiveElementArray.item(0);
        
        int size = repetitiveElementArray.getLength();
        
        
        // Create and get bytes of Organization File
        byte[] xmlFileBytes = XMLFileClass.create(xmlElement.getElementsByTagName("data1").item(0).getTextContent(),
                                                                xmlElement.getElementsByTagName("data2").item(0).getTextContent());
        
        byte[] oneFileBytes = OneFile.create(repetitiveElementArray);
        
        // byte[] ejemploFileBytesWithRepetitiveLines = NewClass.create(size);
        
        
        
        // starting compress process
        byte[] totalBytes = new byte[xmlFileBytes.length + oneFileBytes.length];
        byte[] buffer = new byte[1024];

        try {  
            // Initialize the ZIP File
            ByteArrayOutputStream ostream = new ByteArrayOutputStream(totalBytes.length);
            ZipOutputStream zos = new ZipOutputStream(ostream);
            int len;
            
            
            
            // Add XML_FILE.xml file within de Zip File
            ZipEntry ze0 = new ZipEntry("XML_FILE.xml");
            zos.putNextEntry(ze0);
            
            InputStream isFile0 = new ByteArrayInputStream(xmlFileBytes);

            while ((len = isFile0.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            isFile0.close();
            zos.closeEntry();
            
            
            
            // Add ONE_FILE.csv file within de Zip File
            ZipEntry ze1 = new ZipEntry("ONE_FILE.csv");
            zos.putNextEntry(ze1);
            
            InputStream isFile1 = new ByteArrayInputStream(oneFileBytes);

            while ((len = isFile1.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            isFile1.close();
            zos.closeEntry();
            
            
            

            //remember close it
            zos.close();

            String encodeString = DatatypeConverter.printBase64Binary(ostream.toByteArray());
            return encodeString;
        } catch (IOException ex) {
            Logger.getLogger(Zip.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
