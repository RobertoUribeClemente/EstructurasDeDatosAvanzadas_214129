// Pruebas.java (Modificado para casos específicos)
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Pruebas {

    public static void main(String[] args) {
        String carpetaSalida = "PR3";
        String archivoResultados = carpetaSalida + "/resultados_hamburguesas.csv";

        // --- DEFINICIÓN DE LOS CASOS DE PRUEBA ---
        // Cada fila es un caso de prueba: {m, n, t}
        int[][] casosDePrueba = {
            {4, 9, 10},
            {4, 9, 100},
            {4, 9, 1000},
            {4, 9, 10000}
            // Puedes agregar más casos aquí, por ejemplo: {5, 8, 50}
        };

        System.out.println("Iniciando pruebas con casos de prueba definidos...");
        new File(carpetaSalida).mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoResultados))) {
            writer.println("Metodo,N,M,T,Hamburguesas,TiempoUsado,TiempoSobrante,Duracion_ns");
            ComparacionHamburguesas solver = new ComparacionHamburguesas();

            // --- Bucle que itera sobre cada caso de prueba ---
            for (int[] caso : casosDePrueba) {
                int m = caso[0];
                int n = caso[1];
                int T = caso[2];
                
                System.out.printf("\n--- Probando con m=%d, n=%d, T=%d ---\n", m, n, T);

                // No ejecutamos el recursivo puro para valores grandes de T
                if (T <= 35) {
                    ejecutarYGuardar("Recursivo Puro", solver, n, m, T, writer);
                }
                ejecutarYGuardar("Con Memoizacion", solver, n, m, T, writer);
                ejecutarYGuardar("Dinamico Iterativo", solver, n, m, T, writer);
            }
            
            System.out.println("\nPruebas finalizadas. Revisa el archivo: " + archivoResultados);

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo CSV: " + e.getMessage());
        }
    }
    
    // El método auxiliar "ejecutarYGuardar" no necesita cambios.
    private static void ejecutarYGuardar(String nombreMetodo, ComparacionHamburguesas solver, int n, int m, int T, PrintWriter writer) {
        long startTime = System.nanoTime();
        Resultado res;
        switch (nombreMetodo) {
            case "Recursivo Puro": res = solver.solveRecursivoPuro(n, m, T); break;
            case "Con Memoizacion": res = solver.solveConMemoizacion(n, m, T); break;
            default: res = solver.solveDinamico(n, m, T); break;
        }
        long endTime = System.nanoTime();
        long duracion = endTime - startTime;

        int hamburguesas = res.hamburguesas;
        int tiempoUsado = res.tiempoUsado;
        int tiempoSobrante = (tiempoUsado != -1) ? T - tiempoUsado : -1;

        String salidaConsola = (hamburguesas == -1) ? "No se encontró solución." 
            : String.format("%d h en %d min. (Sobrante: %d)", hamburguesas, tiempoUsado, tiempoSobrante);
        System.out.printf("  %-20s -> %-50s (Tiempo: %d ns)\n", nombreMetodo, salidaConsola, duracion);

        String lineaCSV = String.join(",", nombreMetodo, String.valueOf(n), String.valueOf(m), String.valueOf(T),
                String.valueOf(hamburguesas), String.valueOf(tiempoUsado), String.valueOf(tiempoSobrante), String.valueOf(duracion));
        writer.println(lineaCSV);
    }
}