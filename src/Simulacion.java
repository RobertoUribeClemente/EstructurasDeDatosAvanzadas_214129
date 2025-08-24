import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Simulacion {
    private static int aux = 0;
    private static Random rand = new Random();

    public static void main(String[] args) {
        System.out.println("Inicio de la simulación...");

        // Estructuras
        ArbolBinario arbol = new ArbolBinario();
        ListaDoble lista = new ListaDoble();

        // Listas para registrar tiempos
        ArrayList<Long> tiemposInsertArbol = new ArrayList<>();
        ArrayList<Long> tiemposInsertLista = new ArrayList<>();
        ArrayList<Long> tiemposPollArbol = new ArrayList<>();
        ArrayList<Long> tiemposPollLista = new ArrayList<>();

        try {
            while(aux < 1000){
                if(rand.nextBoolean()) {
                    Thread.sleep(100); // simula espera
                    continue;
                }

                // Crear paciente
                Paciente p = new Paciente(
                    "Paciente" + aux,
                    30 + aux % 10,
                    "ID" + aux,
                    "Dirección" + aux,
                    1 + aux % 5 // prioridad
                );

                // --------------------
                // INSERT
                // --------------------
                long startArbol = System.nanoTime();
                arbol.agregarPaciente(p);
                long endArbol = System.nanoTime();
                tiemposInsertArbol.add(endArbol - startArbol);

                long startLista = System.nanoTime();
                lista.agregaOrdenado(p);
                long endLista = System.nanoTime();
                tiemposInsertLista.add(endLista - startLista);

                System.out.println("Insertado: " + p);

                aux++;

                // --------------------
                // ATENDER (cada 5 pacientes)
                // --------------------
                if(aux % 5 == 0) {
                    System.out.println("\nAtendiendo pacientes...");

                    long startPollArbol = System.nanoTime();
                    Paciente atendidoArbol = arbol.pollNext();
                    long endPollArbol = System.nanoTime();
                    tiemposPollArbol.add(endPollArbol - startPollArbol);

                    long startPollLista = System.nanoTime();
                    Paciente atendidoLista = lista.delFirst();
                    long endPollLista = System.nanoTime();
                    tiemposPollLista.add(endPollLista - startPollLista);

                    System.out.println("Paciente atendido Árbol: " + atendidoArbol);
                    System.out.println("Paciente atendido Lista: " + atendidoLista);
                }

                Thread.sleep(100); // simula 1 minuto (.1s)
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // --------------------
        // Exportar resultados a CSV
        // --------------------
        try (FileWriter writer = new FileWriter("resultados.csv")) {
            writer.write("Operacion,Tiempo_Arbol(ns),Tiempo_Lista(ns)\n");

            int max = Math.max(tiemposInsertArbol.size(), tiemposPollArbol.size());
            for(int i = 0; i < max; i++) {
                if(i < tiemposInsertArbol.size()) {
                    writer.write("Insert," + tiemposInsertArbol.get(i) + "," + tiemposInsertLista.get(i) + "\n");
                }
                if(i < tiemposPollArbol.size()) {
                    writer.write("Poll," + tiemposPollArbol.get(i) + "," + tiemposPollLista.get(i) + "\n");
                }
            }

            // Promedios
            long promInsertArbol = tiemposInsertArbol.stream().mapToLong(Long::longValue).sum() / tiemposInsertArbol.size();
            long promInsertLista = tiemposInsertLista.stream().mapToLong(Long::longValue).sum() / tiemposInsertLista.size();
            long promPollArbol   = tiemposPollArbol.stream().mapToLong(Long::longValue).sum() / tiemposPollArbol.size();
            long promPollLista   = tiemposPollLista.stream().mapToLong(Long::longValue).sum() / tiemposPollLista.size();

            writer.write("\nPROMEDIOS," + promInsertArbol + "," + promInsertLista + "\n");
            writer.write("PROMEDIOS_POLL," + promPollArbol + "," + promPollLista + "\n");

            System.out.println("\nArchivo 'resultados.csv' generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Fin de la simulación.");
    }
}


