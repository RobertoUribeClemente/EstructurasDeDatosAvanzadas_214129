// Pruebas.java (Corregido)
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Pruebas {

    public static void main(String[] args) {
        // --- 1. CONFIGURACIÓN DE PARÁMETROS Y ARCHIVOS ---
        int n = 7;
        int m = 11;
        String carpetaSalida = "PR3";
        String archivoResultados = carpetaSalida + "/resultados_hamburguesas.csv";

        System.out.println("Iniciando pruebas de rendimiento para los algoritmos de hamburguesas...");

        // --- 2. PREPARACIÓN DEL ENTORNO ---
        File directorio = new File(carpetaSalida);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // --- 3. EJECUCIÓN DE PRUEBAS Y ESCRITURA DE CSV ---
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoResultados))) {
            
            writer.println("Metodo,N,M,T,Hamburguesas,TiempoUsado,TiempoSobrante,Duracion_ns");

            ComparacionHamburguesas solver = new ComparacionHamburguesas();

            for (int T = 15; T <= 40; T++) {
                System.out.println("\n--- Procesando para T = " + T + " ---");

                // --- Prueba 1: Algoritmo Recursivo Puro ---
                if (T <= 35) {
                    long inicioRec = System.nanoTime();
                    Resultado resRec = solver.solveRecursivoPuro(n, m, T);
                    long finRec = System.nanoTime();
                    long tiempoRec = finRec - inicioRec;
                    escribirResultado("Recursivo Puro", n, m, T, resRec, tiempoRec, writer);
                }

                // --- Prueba 2: Algoritmo con Memoización ---
                long inicioMemo = System.nanoTime();
                Resultado resMemo = solver.solveConMemoizacion(n, m, T);
                long finMemo = System.nanoTime();
                long tiempoMemo = finMemo - inicioMemo;
                escribirResultado("Con Memoizacion", n, m, T, resMemo, tiempoMemo, writer);

                // --- Prueba 3: Algoritmo Dinámico Iterativo ---
                long inicioDin = System.nanoTime(); // <--- ESTA LÍNEA FUE CORREGIDA
                Resultado resDin = solver.solveDinamico(n, m, T);
                long finDin = System.nanoTime();
                long tiempoDin = finDin - inicioDin;
                escribirResultado("Dinamico Iterativo", n, m, T, resDin, tiempoDin, writer);
            }

            System.out.println("\nPruebas completadas. Los resultados se han guardado en: " + archivoResultados);

        } catch (IOException e) {
            System.err.println("Ocurrió un error al procesar los archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void escribirResultado(String metodo, int n, int m, int T, Resultado res, long duracion, PrintWriter writer) {
        int hamburguesas = res.hamburguesas;
        int tiempoUsado = res.tiempoUsado;
        int tiempoSobrante = (tiempoUsado != -1) ? T - tiempoUsado : -1;

        String salidaConsola = (hamburguesas == -1) ? "No se encontró solución." 
            : String.format("%d hamburguesas en %d min. (Sobrante: %d)", hamburguesas, tiempoUsado, tiempoSobrante);
        System.out.printf("  %-20s -> %-50s (Tiempo: %d ns)\n", metodo, salidaConsola, duracion);

        String lineaCSV = String.join(",",
                metodo,
                String.valueOf(n),
                String.valueOf(m),
                String.valueOf(T),
                String.valueOf(hamburguesas),
                String.valueOf(tiempoUsado),
                String.valueOf(tiempoSobrante),
                String.valueOf(duracion)
        );
        writer.println(lineaCSV);
    }
}