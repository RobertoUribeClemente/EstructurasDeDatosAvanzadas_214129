// Pruebas.java
import java.io.*;


public class Pruebas {

    public static void main(String[] args) {
        int n = 7;
        int m = 11;
        String nombreArchivoCSV = "resultados.csv";

        ComparacionHamburguesas solver = new ComparacionHamburguesas();

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivoCSV))) {
            writer.println("Metodo,N,M,T,Hamburguesas,TiempoUsado,TiempoSobrante,Duracion_ns");
            System.out.println("Iniciando pruebas... Los resultados se guardarán en " + nombreArchivoCSV);

            for (int T = 15; T <= 40; T++) {
                System.out.println("\n--- Probando con T = " + T + " ---");
                if (T <= 35) { 
                    ejecutarYGuardar("Recursivo Puro", solver, n, m, T, writer);
                }
                ejecutarYGuardar("Con Memoizacion", solver, n, m, T, writer);
                ejecutarYGuardar("Dinamico Iterativo", solver, n, m, T, writer);
            }
            
            System.out.println("\nPruebas finalizadas exitosamente.");

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo CSV: " + e.getMessage());
        }
    }
    
    private static void ejecutarYGuardar(String nombreMetodo, ComparacionHamburguesas solver, int n, int m, int T, PrintWriter writer) {
        long startTime = System.nanoTime();
        int[] resultado; // Ahora es un arreglo de enteros

        switch (nombreMetodo) {
            case "Recursivo Puro":
                resultado = solver.solveRecursivoPuro(n, m, T);
                break;
            case "Con Memoizacion":
                resultado = solver.solveConMemoizacion(n, m, T);
                break;
            default: // Dinamico Iterativo
                resultado = solver.solveDinamico(n, m, T);
                break;
        }

        long endTime = System.nanoTime();
        long duracion = endTime - startTime;

        // Extraemos los valores del arreglo
        int hamburguesas = resultado[0];
        int tiempoUsado = resultado[1];
        
        // Formateamos la salida para la consola
        String salidaConsola;
        if (hamburguesas == -1) {
            salidaConsola = "No se encontró solución.";
        } else {
            int tiempoSobrante = T - tiempoUsado;
            salidaConsola = String.format("%d hamburguesas en %d min. (Sobrante: %d)", hamburguesas, tiempoUsado, tiempoSobrante);
        }
        System.out.printf("  %-20s -> %-50s (Tiempo: %d ns)\n", nombreMetodo, salidaConsola, duracion);

        // Creamos y escribimos la línea para el archivo CSV
        String lineaCSV = String.join(",",
                nombreMetodo,
                String.valueOf(n),
                String.valueOf(m),
                String.valueOf(T),
                String.valueOf(hamburguesas),
                String.valueOf(tiempoUsado),
                (tiempoUsado != -1) ? String.valueOf(T - tiempoUsado) : "-1",
                String.valueOf(duracion)
        );
        writer.println(lineaCSV);
    }
}