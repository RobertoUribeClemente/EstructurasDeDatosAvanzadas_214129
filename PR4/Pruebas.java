import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Pruebas {

    // --- Configuración de la Práctica 4 ---
    // Costos según el PDF: insertar=1 , reemplazar=2 
    private static final int COSTO_GAP = 1;
    private static final int COSTO_SUSTITUCION = 2;
    // Límite de tiempo en segundos 
    private static final int TIMEOUT_SEGUNDOS = 10;
    // Archivo de salida para las gráficas
    private static final String ARCHIVO_CSV = "resultados_practica4.csv";

    public static void main(String[] args) {
        Distancia calculator = new Distancia();
        // Longitudes a probar según el PDF 
        int[] longitudes = {5, 50, 500, 5000, 50000}; 

        System.out.println("Iniciando Práctica 4: Pruebas de Desempeño...");

        try (FileWriter writer = new FileWriter(ARCHIVO_CSV)) {
            // Encabezado del CSV
            writer.write("Metodo,Longitud,Distancia,Tiempo_ns,Resultado\n");

            for (int L : longitudes) {
                System.out.println("\n--- PROBANDO CON LONGITUD = " + L + " ---");

                // --- Advertencia sobre Límite de Memoria ---
                // Una matriz de 50000x50000 requiere aprox. 10GB de RAM (50k * 50k * 4 bytes)
                // Tanto la versión PD como la de Memoización fallarán con OutOfMemoryError.
                if (L > 5000) { 
                    System.out.println("ADVERTENCIA: Longitud " + L + " excede límites de memoria. Omitiendo cálculos.");
                    writer.write("Recursivo," + L + ",-1,0,Omitido (OutOfMemory)\n");
                    writer.write("Memoizacion," + L + ",-1,0,Omitido (OutOfMemory)\n");
                    writer.write("Iterativo_PD," + L + ",-1,0,Omitido (OutOfMemory)\n");
                    continue; // Saltar a la siguiente longitud
                }

                // Generar textos aleatorios A y B
                String A = generarTextoAleatorio(L);
                String B = generarTextoAleatorio(L);
                // Salida requerida: Número de caracteres [cite: 15]
                System.out.println("Textos generados. Len(A)=" + A.length() + ", Len(B)=" + B.length());

                // --- 1. Prueba Recursiva Pura [cite: 20] ---
                ejecutarPrueba("Recursivo", L, A, B, calculator::calculaED, writer);
                
                // --- 2. Prueba Recursiva con Memoización  ---
                ejecutarPrueba("Memoizacion", L, A, B, calculator::calculaEDMemo, writer);
                
                // --- 3. Prueba Iterativa con PD [cite: 22] ---
                ejecutarPrueba("Iterativo_PD", L, A, B, calculator::calculaEDP, writer);
                
                // --- Salida de consola requerida por el PDF [cite: 16] ---
                // (Usamos la versión rápida PD para esto)
                System.out.println("Reporte de Distancias (con PD):");
                int dist_AB = calculator.calculaEDP(A, B, COSTO_GAP, COSTO_SUSTITUCION);
                int dist_BA = calculator.calculaEDP(B, A, COSTO_GAP, COSTO_SUSTITUCION);
                System.out.println("  D(A,B) = " + dist_AB);
                System.out.println("  D(B,A) = " + dist_BA);
            }
        } catch (IOException e) {
            System.err.println("Error fatal al escribir el archivo CSV: " + e.getMessage());
        }
        System.out.println("\n--- Pruebas completadas ---");
        System.out.println("Resultados guardados en: " + ARCHIVO_CSV);
    }

    /**
     * Interfaz funcional para unificar las tres funciones de distancia.
     */
    @FunctionalInterface
    interface DistanciaFunction {
        int calcular(String s1, String s2, int gap, int sub);
    }

    /**
     * Ejecuta una prueba, mide su tiempo y maneja el timeout de 10 segundos. 
     */
    private static void ejecutarPrueba(String metodo, int L, String A, String B, DistanciaFunction func, FileWriter writer) throws IOException {
        
        System.out.printf("  Ejecutando: %-15s... ", metodo);
        long startTime = System.nanoTime();
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Tarea a ejecutar en otro hilo
        Future<Integer> future = executor.submit(() -> {
            return func.calcular(A, B, COSTO_GAP, COSTO_SUSTITUCION);
        });

        String resultadoCSV;
        try {
            // Intentar obtener el resultado con un límite de 10 segundos
            int distancia = future.get(TIMEOUT_SEGUNDOS, TimeUnit.SECONDS);
            long duracion = System.nanoTime() - startTime;
            // Salida requerida: Tiempo de cálculo [cite: 17]
            System.out.printf("Dist: %d, Tiempo: %,d ns\n", distancia, duracion);
            resultadoCSV = String.format("%s,%d,%d,%d,Exitoso\n", metodo, L, distancia, duracion);

        } catch (TimeoutException e) {
            future.cancel(true); // Detener la tarea
            long duracion = System.nanoTime() - startTime;
            // Salida requerida: Mensaje de tiempo límite 
            System.out.println("TIEMPO LÍMITE EXCEDIDO (> 10s)");
            resultadoCSV = String.format("%s,%d,-1,%d,Timeout\n", metodo, L, duracion);

        } catch (Exception e) {
            // Captura otros errores (como OutOfMemoryError si la tarea falla)
            long duracion = System.nanoTime() - startTime;
            String errorMsg = e.getCause() != null ? e.getCause().getClass().getSimpleName() : e.getClass().getSimpleName();
            System.out.println("ERROR: " + errorMsg);
            resultadoCSV = String.format("%s,%d,-1,%d,Error (%s)\n", metodo, L, duracion, errorMsg);
        } finally {
            executor.shutdownNow(); // Asegurarse de limpiar el hilo
        }
        writer.write(resultadoCSV);
    }

    /**
     * Genera un texto aleatorio de una longitud dada.
     */
    private static String generarTextoAleatorio(int longitud) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(longitud);
        // Usamos un alfabeto pequeño ('A'-'E') para forzar más coincidencias
        for (int i = 0; i < longitud; i++) 
            sb.append((char) ('A' + rand.nextInt(5))); 
        
        return sb.toString();
    }
}