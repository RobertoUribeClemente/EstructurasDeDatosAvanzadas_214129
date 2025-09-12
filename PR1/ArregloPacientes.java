
import java.io.*;
import java.util.*;

public class ArregloPacientes {
    private Paciente[] pacientes;
    private int cantidad; //Esto representa el número real de pacientes leídos

    public ArregloPacientes(int capacidad){
        pacientes = new Paciente[capacidad];
        cantidad = 0;
    }

    /**
     * Carga los pacientes desde un archivo de texto.
     * Cada paciente tiene exactamente 10 enteros.
     */
    public boolean agregarPacientes(String nombreArchivo) {
        boolean res = false;
        try {
            FileReader file = new FileReader(nombreArchivo);
            Scanner lec = new Scanner(file);

            if (lec.hasNextInt()) {
                int n = lec.nextInt(); // número de pacientes
                pacientes = new Paciente[n];
                cantidad = n;

                for (int i = 0; i < n; i++) {
                    int[] datosPaciente = new int[10];

                    for (int j = 0; j < 10; j++) {
                        if (lec.hasNextInt()) 
                            datosPaciente[j] = lec.nextInt();
                         else 
                            throw new IllegalArgumentException("Faltan datos para el paciente " + i);
                        
                    }

                    pacientes[i] = new Paciente(datosPaciente);
                }
                res = true;
            }
            lec.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Archivo no encontrado: " + nombreArchivo);
        }
        return res;
    }

    /**
     * Busca duplicados recorriendo el arreglo de forma lineal.
     * Devuelve el número total de pacientes duplicados (m).
     */
    public int hayDuplicados() {
        if (pacientes == null || cantidad == 0) 
        return 0;

    Map<Paciente, Integer> conteo = new HashMap<>();

    for (int i = 0; i < cantidad; i++) 
        conteo.put(pacientes[i], conteo.getOrDefault(pacientes[i], 0) + 1);

    int duplicados = 0;
    for (int repeticiones : conteo.values()) {
        if (repeticiones > 1) 
            duplicados += repeticiones; // contamos todas las apariciones
        
    }

        return duplicados;
    }   


}
