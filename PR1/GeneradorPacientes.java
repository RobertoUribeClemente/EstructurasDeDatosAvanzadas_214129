import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeneradorPacientes {
    public static void main(String[] args) {
        int n = 10000; // número de pacientes
        String archivo = "pacientes.txt";
        Random rand = new Random();

        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write(n + "\n");

            // Generar la mitad de pacientes aleatorios
            for (int i = 0; i < n / 2; i++) {
                for (int j = 0; j < 10; j++) {
                    int dato = rand.nextInt(10_000_001); // rango 0..10,000,000
                    fw.write(dato + (j < 9 ? " " : ""));
                }
                fw.write("\n");
            }

            // Generar duplicados de algunos pacientes ya creados
            // Aquí duplicamos los primeros (n/2) pacientes
            for (int i = 0; i < n / 2; i++) {
                for (int j = 0; j < 10; j++) {
                    int dato = rand.nextInt(10_000_001);
                    fw.write(dato + (j < 9 ? " " : ""));
                }
                fw.write("\n");
            }

            System.out.println("Archivo generado: " + archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

