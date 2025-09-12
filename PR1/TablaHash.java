import java.io.*;
import java.util.*;

public class TablaHash {
    private ArrayList<ListaDoble> tabla;
    private int capacidad; // tamaño fijo de la tabla (ej. primo)

    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        tabla = new ArrayList<>(capacidad);
        for (int i = 0; i < capacidad; i++) {
            tabla.add(null);
        }
    }

    private int funcionHash(Paciente p) {
        return Math.abs(p.hashCode() % capacidad);
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
                    int indice = funcionHash(p);

                    if (tabla.get(indice) == null) {
                        ListaDoble nueva = new ListaDoble(indice);
                        nueva.addLast(p);
                        tabla.set(indice, nueva);
                    } else {
                        tabla.get(indice).addLast(p);
                    }
                }
                res = true;
            }
        } catch (IOException ex) {
            System.err.println("Error al leer archivo: " + ex.getMessage());
        }
        return res;
    }

    /**
     * Busca duplicados en TODA la tabla.
     */
public int hayDuplicados() {
    Map<Paciente, Integer> conteo = new HashMap<>();

    for (ListaDoble lista : tabla) {
        if (lista != null && !lista.isEmpty()) {
            NodoDoble<Paciente> actual = lista.getPrimero();
            while (actual != null) {
                Paciente p = actual.getDato();
                conteo.put(p, conteo.getOrDefault(p, 0) + 1);
                actual = actual.getDerecho();
            }
        }
    }

    int duplicados = 0;
    for (int repeticiones : conteo.values()) {
        if (repeticiones > 1) {
            duplicados += repeticiones;
        }
    }

    return duplicados;
}



}
