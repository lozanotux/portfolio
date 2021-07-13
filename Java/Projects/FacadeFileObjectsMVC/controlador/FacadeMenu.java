package controlador;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method; // Java Reflection
import java.util.ArrayList;
import java.util.Scanner; 

/**
 *
 * @author Juan Pablo Lozano
 */
public class FacadeMenu {
    
   private Object invocador;
   private ArrayList<String> opciones;
   private ArrayList<Method> metodos;
   
   private Scanner S = new Scanner(System.in);
   
   public FacadeMenu(Object _invocador) {
       invocador = _invocador;
       opciones = new ArrayList();
       metodos = new ArrayList();
   }
   
   public void ejecutar() { // 18
       int opcion;
       do {
           int i = 0;
           opcion = -1;
           while(i < opciones.size()) {
               System.out.println((i + 1) + ". " + opciones.get(i++));
           }
           System.out.println((i + 1) + ". Cerrar menú");
           System.out.print("Seleccione opción: ");
           opcion = S.nextInt();
           System.out.println("OPCION: " + opcion);
           ejecutarOpcion(opcion-1);
       } while (opcion < 5);
   }
   
   public void agregarOpcion(String opcion, String metodo) { // 12
       opciones.add(opcion);
       Method m = null;
       try {
           m = invocador.getClass().getMethod(metodo, null);
       } catch (NoSuchMethodException | SecurityException ex) {
           System.out.println("Ocurrió un problema al tratar de agregar metodo");
       }
       metodos.add(m);
   }
   
   public void ejecutarOpcion(int i) { // 9
       if (metodos.size() > i) {
           try {
               System.out.println("ELEGIDA: " + metodos.get(i).getName());
               metodos.get(i).invoke(invocador);
           } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
               System.out.println("Error al ejecutar un método.");
           }
       }
   }
    
}
