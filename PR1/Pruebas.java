import java.io.FileWriter;
import java.io.IOException;

public class Pruebas {
    public static void main(String[] args) {
        String archivo = "pacientes.txt"; // archivo generado

        try (FileWriter csv = new FileWriter("resultados.csv")) {
            csv.write("Estructura,Tiempo(ns),Duplicados\n");

            // --- Prueba con Arreglo Lineal ---
            ArregloPacientes arreglo = new ArregloPacientes(0);
            arreglo.agregarPacientes(archivo);

            long inicio = System.nanoTime();
            int dupLineal = arreglo.hayDuplicadosLineal();
            long fin = System.nanoTime();
            csv.write("ArregloLineal," + (fin - inicio) + "," + dupLineal + "\n");

            if (dupLineal == 0) {
                System.out.println("no hay dos pacientes con registros idénticos (Arreglo Lineal)");
            } else {
                System.out.println("se encontraron " + dupLineal + " pacientes idénticos (Arreglo Lineal)");
            }

            // --- Prueba con Hash Propio ---
            int capacidad = 10007;
            TablaHash hash = new TablaHash(capacidad);
            inicio = System.nanoTime();
            hash.agregarPacientes(archivo);
            int dupHash = hash.hayDuplicados();
            fin = System.nanoTime();
            csv.write("Hash," + (fin - inicio) + "," + dupHash + "\n");

            if (dupHash == 0) {
                System.out.println("no hay dos pacientes con registros idénticos (Hash)");
            } else {
                System.out.println("se encontraron " + dupHash + " pacientes idénticos (Hash)");
            }

            System.out.println("Pruebas completadas. Resultados en resultados.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


