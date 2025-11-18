
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pruebas {
    public static void main(String[] args) {
        
        System.out.println("--- Compresor Huffman ---");
        Scanner consoleScanner = new Scanner(System.in);
        System.out.print("Introduce la ruta del archivo de entrada (ej. input.txt): ");
        String filePath = consoleScanner.nextLine();

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            
            if (lines.size() < 3) {
                System.err.println("Error: El archivo debe contener 3 líneas (alfabeto, frecuencias, texto).");
                return;
            }

            // 1. Procesar Alfabeto (Línea 1)
            String[] simbolosStr = lines.get(0).split(" ");
            char[] A = new char[simbolosStr.length];
            for (int i = 0; i < simbolosStr.length; i++) {
                A[i] = simbolosStr[i].charAt(0);
            }
            int n = A.length;

            // 2. Procesar Frecuencias (Línea 2)
            String[] frecsStr = lines.get(1).split(" ");
            if (frecsStr.length != n) {
                System.err.println("Error: La cantidad de símbolos y frecuencias no coincide.");
                return;
            }
            int[] F = new int[n];
            for (int i = 0; i < n; i++) {
                F[i] = Integer.parseInt(frecsStr[i]);
            }

            // 3. Procesar Texto (Línea 3)
            String textoOriginal = lines.get(2);
            
            // 4. Crear instancia y medir tiempo
            long startTime = System.nanoTime();
            Huffman huffman;
            huffman = new Huffman(F, A, n);
            
            String textoCodificado = huffman.huffmanEncode(textoOriginal);
            
            long endTime = System.nanoTime();
            long duracionTotal_ns = endTime - startTime; 

            String textoDecodificado = huffman.huffmanDecode(textoCodificado);

            // --- Generar Salida ---
            System.out.println("\n--- RESULTADOS ---");

            // 1. Alfabeto
            System.out.println("Alfabeto: " + Arrays.toString(A));

            // 2. Códigos Huffman
            System.out.println("Códigos Huffman:");
            StringBuilder codigos = new StringBuilder();
            for (char c : A) {
                codigos.append(huffman.getCodeForChar(c)).append(" ");
            }
            System.out.println(codigos.toString().trim());

            // 3. Estadísticas
            int simbolosAntes = textoOriginal.length();
            int bitsCodificado = textoCodificado.length();
            int bitsUtf8 = simbolosAntes * 8; 
            System.out.println("\nEstadísticas:");
            System.out.printf("Símbolos antes: %d | Bits codificado: %d | Bits UTF-8 (8-bit): %d\n",
                    simbolosAntes, bitsCodificado, bitsUtf8);

            // 4. Tiempo
            System.out.println("Tiempo (Build + Encode): " + duracionTotal_ns + " ns (" + (duracionTotal_ns / 1_000_000.0) + " ms)");

            // 5. Texto Codificado
            System.out.println("\nTexto Codificado:");
            System.out.println(textoCodificado);

            // 6. Texto Decodificado
            System.out.println("\nTexto Decodificado:");
            System.out.println(textoDecodificado);

            // Verificación
            boolean exito = textoOriginal.equals(textoDecodificado);
            System.out.println("\nVerificación (Original == Decodificado): " + (exito ? "Éxito" : "Fallo"));


        } catch (IOException e) {
            System.err.println("Error al leer el archivo '" + filePath + "': " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear las frecuencias. Asegúrate de que sean números.");
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            consoleScanner.close();
        }
    }
}
