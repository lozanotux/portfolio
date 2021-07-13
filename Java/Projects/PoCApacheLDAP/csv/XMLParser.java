package soajp.csv;

import javax.xml.bind.DatatypeConverter;

public class XMLParser {
    
    public static void main(String[] args) throws Exception {
        String base64 = "ABC+DefgHIJKLM9NOp1=";
        
        byte[] bytes = DatatypeConverter.parseBase64Binary(base64);
        
        String salida = new String(bytes, "UTF-8");
        
        int indice = salida.indexOf("<ROOT_DATA>");

        System.out.println(salida.substring(indice));
    }
}
