import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Pruebas {

    
    private static final int COSTO_GAP = 1;
    private static final int COSTO_SUSTITUCION = 2;
    private static final int TIMEOUT_SEGUNDOS = 10;
    private static final String ARCHIVO_CSV = "resultados_practica4.csv";


    private static String generarTextoAleatorio(int longitud) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) 
            sb.append((char) ('A' + rand.nextInt(5))); 
        
        return sb.toString();
    }


    
    @FunctionalInterface
    interface DistanciaFunction {
        int calcular(String s1, String s2, int gap, int sub);
    }

    
    private static void ejecutarPrueba(String metodo, int L, String A, String B, 
                                        DistanciaFunction func, FileWriter writer) throws IOException {
        
        System.out.printf("  Ejecutando: %-15s... ", metodo);
        long startTime = System.nanoTime();
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            return func.calcular(A, B, COSTO_GAP, COSTO_SUSTITUCION);
        });

        String resultadoCSV;
        try {
            int distancia = future.get(TIMEOUT_SEGUNDOS, TimeUnit.SECONDS);
            long duracion = System.nanoTime() - startTime;
            System.out.printf("Dist: %d, Tiempo: %,d ns\n", distancia, duracion);
            resultadoCSV = String.format("%s,%d,%d,%d,Exitoso\n", metodo, L, distancia, duracion);

        } catch (TimeoutException e) {
            future.cancel(true); 
            long duracion = System.nanoTime() - startTime;
            System.out.println("TIEMPO LÍMITE EXCEDIDO (> 10s)");
            resultadoCSV = String.format("%s,%d,-1,%d,Timeout\n", metodo, L, duracion);

        } catch (Exception e) { // Captura ExecutionException, InterruptedException
            long duracion = System.nanoTime() - startTime;
            // 'e.getCause()' nos da el error real que ocurrió dentro del hilo (ej. StackOverflowError)
            String errorMsg = (e.getCause() != null) ? e.getCause().getClass().getSimpleName() : e.getClass().getSimpleName();
            
            // Caso especial para OutOfMemoryError, que a veces no es 'getCause()'
            if (e.getCause() instanceof OutOfMemoryError) 
                errorMsg = "OutOfMemoryError";
            

            System.out.println("ERROR: " + errorMsg);
            resultadoCSV = String.format("%s,%d,-1,%d,Error (%s)\n", metodo, L, duracion, errorMsg);
        } finally {
            executor.shutdownNow(); 
        }
        writer.write(resultadoCSV);
    }

    public static void main(String[] args) {
        Distancia calculator = new Distancia();
        int[] longitudes = {5, 50, 500, 5000, 50000}; 

        System.out.println("Iniciando Práctica 4: Pruebas de Desempeño...");

        try (FileWriter writer = new FileWriter(ARCHIVO_CSV)) {
            writer.write("Metodo,Longitud,Distancia,Tiempo_ns,Resultado\n");

            for (int L : longitudes) {
                System.out.println("\n--- PROBANDO CON LONGITUD = " + L + " ---");

                // Generar textos aleatorios A y B
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
            }
        } catch (IOException e) {
            System.err.println("Error fatal al escribir el archivo CSV: " + e.getMessage());
        }
        System.out.println("\n--- Pruebas completadas ---");
        System.out.println("Resultados guardados en: " + ARCHIVO_CSV);
    }
    
}