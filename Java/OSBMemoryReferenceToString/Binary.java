package soajp.decoding.tools;

public class Binary {
    
    public static String getBinaryContentAsString(Object param) {
        System.out.println("Class=" + param.getClass().getName());
        System.out.println("Param=" + param);
        byte[] bytes = (byte[])param;

        String content = new String(bytes);
        System.out.println("Content=" + content);
        return content;
    }
    
}
