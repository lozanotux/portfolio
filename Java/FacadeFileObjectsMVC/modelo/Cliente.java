package modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Juan Pablo Lozano
 */
public class Cliente implements Serializable {
    
    private String nombre;
    private int edad;
    private ArrayList<Perro> mascotas;
    
    public Cliente(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
        mascotas = new ArrayList();
    }
    
    public Cliente(String nombre) {
        this.nombre = nombre;
    }
    
    public void agregarMascota(String nombre, String raza) {
        mascotas.add(new Perro(nombre, raza));
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public ArrayList<Perro> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ArrayList<Perro> mascotas) {
        this.mascotas = mascotas;
    }
    
}
