import java.io.FileWriter;
import java.io.IOException;

public class Pruebas {
    public static void main(String[] args) {
        String archivo = "pacientes.txt"; // nombre del archivo con los datos

        try (FileWriter csv = new FileWriter("resultados.csv")) {
            csv.write("Estructura,Tiempo(ns),Duplicados\n");

            // --- Prueba con Arreglo ---
            ArregloPacientes arreglo = new ArregloPacientes(0);
            long inicio = System.nanoTime();
            arreglo.agregarPacientes(archivo);
            int dupArreglo = arreglo.hayDuplicados();
            long fin = System.nanoTime();
            csv.write("Arreglo," + (fin - inicio) + "," + dupArreglo + "\n");

            // --- Prueba con Hash ---
            int capacidad = 10007; // número primo cercano al número de pacientes
            TablaHash hash = new TablaHash(capacidad);
            inicio = System.nanoTime();
            hash.agregarPacientes(archivo);
            int dupHash = hash.hayDuplicados();
            fin = System.nanoTime();
            csv.write("Hash," + (fin - inicio) + "," + dupHash + "\n");

            System.out.println("Pruebas completadas. Resultados en resultados.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

