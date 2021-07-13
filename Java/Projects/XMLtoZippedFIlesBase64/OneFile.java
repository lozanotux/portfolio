package com.soajp.utils.zip;

import javax.xml.bind.DatatypeConverter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class OneFile {
    public static byte[] create(NodeList nodeArray) {
        
        String content = "";
        
        // Add the header to CSV
        content = content + "Header,Of,CSV File,Data1,Data2,DataN" + "\n";
        
        for (int i=0; i < nodeArray.getLength(); i++) {
            // Add current node to Variable
            Element oneElement = (Element)nodeArray.item(i);

            // Add the values to CSV
            content = content + "ValueHeader,ValueOf,ValueCSVFile," + 
                               oneElement.getElementsByTagName("data1").item(0).getTextContent() +
                               oneElement.getElementsByTagName("data2").item(0).getTextContent() +
                               oneElement.getElementsByTagName("dataN").item(0).getTextContent() + "\n";

        }
        
        // Clean the last break line
        content = content.trim();
        
        // convert CSV file to Base64
        byte[] contentBytes = content.getBytes();
        String encodedString = DatatypeConverter.printBase64Binary(contentBytes);

        // Get bytes of CSV File
        byte[] rbytes = DatatypeConverter.parseBase64Binary(encodedString);  
           
        
        return rbytes;
        
    }
}
