
import java.io.*;
import java.util.*;

public class TablaHash {
    private ArrayList<ListaDoble> tabla;

    public TablaHash() {
        tabla = new ArrayList<>();
    }

    public boolean agregarPacientes(String nombreArchivo) {
        boolean res = false;
        try (Scanner lec = new Scanner(new FileReader(nombreArchivo))) {
            if (lec.hasNextInt()) {
                int n = lec.nextInt(); // número de pacientes

                for (int i = 0; i < n; i++) {
                    int[] datos = new int[10];
                    for (int j = 0; j < 10; j++) {
                        if (lec.hasNextInt()) {
                            datos[j] = lec.nextInt();
                        } else {
                            throw new IllegalArgumentException("Archivo mal formado en paciente " + (i + 1));
                        }
                    }

                    Paciente p = new Paciente(datos);
                    int indice = p.hashCode();
                    int k = buscarIndice(indice);

                    if (k != -1) {
                        tabla.get(k).addLast(p);
                    } else {
                        ListaDoble nueva = new ListaDoble(indice);
                        nueva.addLast(p);
                        tabla.add(nueva);
                    }
                }
                res = true;
            }
        } catch (IOException ex) {
            System.err.println("Error al leer archivo: " + ex.getMessage());
        }
        return res;
    }

    private int buscarIndice(int indice) {
        boolean encontrado = false;
        int res = -1;
        if (!tabla.isEmpty()) {
            int i = 0;
            int n = tabla.size();
            while (!encontrado && i < n) {
                if (tabla.get(i).getIndice() == indice) {
                    encontrado = true;
                } else {
                    i++;
                }
            }
            if (encontrado) {
                res = i;
            }
        }
        return res;
    }

    /**
     * Busca si hay pacientes duplicados en TODA la tabla.
     * Devuelve el número total de duplicados (m).
     */
    public int hayDuplicados() {
        int duplicados = 0;
        int n = tabla.size();

        for (int i = 0; i < n; i++) {
            duplicados += tabla.get(i).hayDuplicados();
        }

        return duplicados;
    }
}
