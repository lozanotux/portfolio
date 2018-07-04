package soajp.csv;

import java.util.ArrayList;
import java.util.Iterator;

public class TestZipUtil {
    public static void main(String[] args){
        ArrayList<CsvStruct> arreglo;
        arreglo = new OutboundUtil().getZipContentAsOpaqueList("ABC+DEfghi9JKLMN1=");
        
        Iterator<CsvStruct> iter;
        iter = arreglo.iterator();
        
        while(iter.hasNext()){
            CsvStruct cvs = iter.next();
            System.out.println("Archivo: " + cvs.getFileName());
            System.out.println("Contenido: " + cvs.getFileContent());
        }
    }
}
