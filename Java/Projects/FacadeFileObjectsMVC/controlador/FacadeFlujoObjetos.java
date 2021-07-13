package controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Juan Pablo Lozano
 */
public class FacadeFlujoObjetos {
   
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private String nombre; // del archivo
    
    public FacadeFlujoObjetos(String _nombre) {
        this.nombre = _nombre;
    }
    
    public Object leerArchivo() {
        try {
            entrada = new ObjectInputStream(new FileInputStream(nombre));
            Object o = entrada.readObject();
            entrada.close();
            return o;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }
    
    public void escribirArchivo(Serializable dato) {
        try {
            salida = new ObjectOutputStream(new FileOutputStream(nombre));
            salida.writeObject(dato);
            salida.close();
        } catch (FileNotFoundException ex) {            
        } catch (IOException ex) {
        }
    }
    
}
