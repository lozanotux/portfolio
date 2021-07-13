package modelo;

import java.io.Serializable;

/**
 *
 * @author Juan Pablo Lozano
 */
public class Perro implements Serializable {
    
    private String nombre;
    private String raza;

    public Perro(String nombre, String raza) {
        this.nombre = nombre;
        this.raza = raza;
    }
    
    
}
