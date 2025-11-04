import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Pruebas {

    // --- CONSTANTES ---
    private static final int COSTO_GAP = 1;
    private static final int COSTO_SUSTITUCION = 2;
    private static final int TIMEOUT_SEGUNDOS = 10;
    private static final String ARCHIVO_CSV = "resultados_practica4.csv";

    private static final Random RAND = new Random();

    /**
     * Genera un texto aleatorio de una longitud dada.
     * Usa un alfabeto de 5 caracteres ('A' a 'E').
     */
    private static String generarTextoAleatorio(int longitud) {
        // Usa la instancia estática RAND
        StringBuilder sb = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            sb.append((char) ('A' + RAND.nextInt(5)));
        }
        return sb.toString();
    }

    /**
     * Interfaz funcional para pasar los diferentes métodos de cálculo.
     */
    @FunctionalInterface
    interface DistanciaFunction {
        int calcular(String s1, String s2, int gap, int sub);
    }

    private static void ejecutarPrueba(String metodo, int L, String A, String B,
                                       DistanciaFunction func, Writer writer) throws IOException {
        
        System.out.printf("  Ejecutando: %-15s... ", metodo);
        long startTime = System.nanoTime();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            return func.calcular(A, B, COSTO_GAP, COSTO_SUSTITUCION);
        });

        String resultadoCSV;
        try {
            // Intenta obtener el resultado con timeout
            int distancia = future.get(TIMEOUT_SEGUNDOS, TimeUnit.SECONDS);
            long duracion = System.nanoTime() - startTime;
            System.out.printf("Dist: %d, Tiempo: %,d ns\n", distancia, duracion);
            resultadoCSV = String.format("%s,%d,%d,%d,Exitoso\n", metodo, L, distancia, duracion);

        } catch (TimeoutException e) {
            future.cancel(true); // Interrumpe el hilo de la tarea
            long duracion = System.nanoTime() - startTime;
            System.out.println("TIEMPO LÍMITE EXCEDIDO (> 10s)");
            resultadoCSV = String.format("%s,%d,-1,%d,Timeout\n", metodo, L, duracion);

        } catch (Exception e) { // Captura ExecutionException, InterruptedException
            long duracion = System.nanoTime() - startTime;
            
            String errorMsg;
            Throwable cause = e.getCause(); // El error real dentro del hilo (ej. StackOverflow)
            
            if (cause instanceof OutOfMemoryError) {
                errorMsg = "OutOfMemoryError";
            } else if (cause != null) {
                errorMsg = cause.getClass().getSimpleName();
            } else {
                errorMsg = e.getClass().getSimpleName(); // (ej. InterruptedException)
            }
            
            System.out.println("ERROR: " + errorMsg);
            resultadoCSV = String.format("%s,%d,-1,%d,Error (%s)\n", metodo, L, duracion, errorMsg);

        } finally {
            // Detiene el executor de esta prueba.
            // 'shutdownNow' es importante para matar el hilo si se quedó colgado.
            executor.shutdownNow();
        }
        
        // Escribe el resultado de esta prueba
        writer.write(resultadoCSV);
    }

    public static void main(String[] args) {
        Distancia calculator = new Distancia();
        // Ajustado para incluir un caso mediano que sí terminará con Memo/PD
        int[] longitudes = {5, 50, 500, 5000, 15000, 50000}; 

        System.out.println("Iniciando Práctica 4: Pruebas de Desempeño...");

        try (Writer writer = new BufferedWriter(new FileWriter(ARCHIVO_CSV))) {
            
            // Escribir la cabecera del CSV
            writer.write("Metodo,Longitud,Distancia,Tiempo_ns,Resultado\n");

            for (int L : longitudes) {
                System.out.println("\n--- PROBANDO CON LONGITUD = " + L + " ---");

                String A = generarTextoAleatorio(L);
                String B = generarTextoAleatorio(L);
                System.out.println("Textos generados. Len(A)=" + A.length() + ", Len(B)=" + B.length());

                // --- 1. Prueba Recursiva Pura ---
                ejecutarPrueba("Recursivo", L, A, B, calculator::calculaED, writer);
                
                // --- 2. Prueba Recursiva con Memoización ---
                ejecutarPrueba("Memoizacion", L, A, B, calculator::calculaEDMemo, writer);
                
                // --- 3. Prueba Iterativa con PD ---
                ejecutarPrueba("Iterativo_PD", L, A, B, calculator::calculaEDP, writer);
                
                // Solo reportamos distancias si L no es demasiado grande
                if (L <= 500) {
                    System.out.println("Reporte de Distancias (con PD):");
                    int dist_AB = calculator.calculaEDP(A, B, COSTO_GAP, COSTO_SUSTITUCION);
                    int dist_BA = calculator.calculaEDP(B, A, COSTO_GAP, COSTO_SUSTITUCION);
                    System.out.println("  D(A,B) = " + dist_AB);
                    System.out.println("  D(B,A) = " + dist_BA);
                }
                
                writer.flush();
            }
            
        } catch (IOException e) {
            System.err.println("Error fatal al escribir el archivo CSV: " + e.getMessage());
        } catch (OutOfMemoryError e) {
            // Captura OOM que puede ocurrir al generar los strings A y B
            // si L es excesivamente grande (ej. L = 2,000,000,000)
            System.err.println("\n--- ERROR FATAL: OutOfMemoryError ---");
            System.err.println("El programa se quedó sin memoria, probablemente al generar los textos.");
            System.err.println("Intenta con longitudes más pequeñas.");
        }
        
        System.out.println("\n--- Pruebas completadas ---");
        System.out.println("Resultados guardados en: " + ARCHIVO_CSV);
    }
}