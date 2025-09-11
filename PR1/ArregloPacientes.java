
import java.io.*;
import java.util.*;

public class ArregloPacientes {
    private Paciente[] pacientes;
    private int cantidad; //Esto representa el número real de pacientes leídos

    public ArregloPacientes(int capacidad){
        pacientes = new Paciente[capacidad];
        cantidad = 0;
    }

    public boolean agregarPacientes(String nombreArchivo) {
        boolean res = false;
        try (Scanner lec = new Scanner(new FileReader(nombreArchivo))) {
            if (lec.hasNextInt()) {
                int n = lec.nextInt(); // número de pacientes

                if (n > pacientes.length) {
                    throw new IllegalArgumentException("Capacidad insuficiente en el arreglo");
                }

                for (int i = 0; i < n; i++) {
                    int[] datos = new int[10];
                    for (int j = 0; j < 10; j++) {
                        if (lec.hasNextInt()) {
                            datos[j] = lec.nextInt();
                        } else {
                            throw new IllegalArgumentException("Archivo mal formado en paciente " + (i + 1));
                        }
                    }
                    pacientes[cantidad++] = new Paciente(datos);
                }
                res = true;
            }
        } catch (IOException ex) {
            System.err.println("Error al leer archivo: " + ex.getMessage());
        }
        return res;
    }
}
