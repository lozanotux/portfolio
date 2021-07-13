package soajp.csv;

import java.util.ArrayList;

public interface IOutboundUtil {
    ArrayList<CsvStruct> getZipContentAsOpaqueList(String base64);
}