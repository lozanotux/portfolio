package vista;

import controlador.*;
import modelo.*;
import java.util.*; // ArrayList y Scanner


/**
 *
 * @author Juan Pablo Lozano
 */
public class Principal {
    
    private static Scanner S = new Scanner(System.in);
    private static ArrayList<Cliente> listado;
    private static FacadeFlujoObjetos flujo;
    
    private static String leer(String mensaje) {
        System.out.print(mensaje);
        return S.nextLine();
    }
    
    public static void main(String[] args) {
        listado = new ArrayList();
        flujo = new FacadeFlujoObjetos("C:\\Users\\jlozanop\\Documents\\Facultad\\cliente.obj");
        
        FacadeMenu m = new FacadeMenu(new Principal());
        m.agregarOpcion("Registrar cliente.", "agregarCliente");
        m.agregarOpcion("Listar clientes.", "listarClientes");
        m.agregarOpcion("Guardar datos.", "guardarDatos");
        m.agregarOpcion("Cargar datos.", "cargarDatos");
        m.ejecutar();
    }
    
    public static void agregarCliente() {
        String nombre = leer("Ingrese nombre: ");
        int edad = Integer.parseInt(leer("Ingrese edad: "));
        ArrayList<Perro> mascotas = new ArrayList();
        int cMascotas = Integer.parseInt(leer("Cantidad de mascotas?: "));
        Cliente c = new Cliente(nombre, edad);
        for (int i = 0; i < cMascotas; i++) {
            System.out.println("Leyendo info de la mascota " + (i+1));
            String _nombre = leer("Nombre mascota: ");
            String _raza = leer("Nombre raza: ");
            c.agregarMascota(_nombre, _raza);
        }
        listado.add(c);
    }
    
    public static void listarClientes() {
        for (Cliente c : listado) {
            System.out.println(c.getNombre());
        }
    }
    
    public static void guardarDatos() {
        flujo.escribirArchivo(listado);
    }
    
    public static void cargarDatos() {
        listado = (ArrayList<Cliente>) flujo.leerArchivo();
    }
    
}