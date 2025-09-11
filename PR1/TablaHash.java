
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

    public int buscarIndice(int indice) {
        for (int i = 0; i < tabla.size(); i++) {
            if (tabla.get(i).getIndice() == indice) {
                return i;
            }
        }
        return -1;
    }

    public boolean hayIguales() {
        for (ListaDoble lista : tabla) {
            if (lista.hayDuplicados()) {
                return true;
            }
        }
        return false;
    }
}
