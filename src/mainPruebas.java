import java.util.*;
import java.util.concurrent.*;

public class mainPruebas {

    // ===== Configuración =====
    static final int N_PACIENTES = 20; // cámbialo a 1000 si quieres largo
    static final long DURACION_LLEGADA_MS = 60_000L;   // cada 5s llega 1 paciente
    static final long DURACION_ATENCION_MS = 90_000L; // cada 20s se atiende 1 paciente
    static final boolean VERBOSE_ATENCION = true;

    public static void main(String[] args) {
        // 1) Generar pacientes
        List<Paciente> lote = generarPacientes(N_PACIENTES);

        // 2) Estructuras
        ListaDoble lista = new ListaDoble();
        ABinBusPrioridad arbol = new ABinBusPrioridad();

        // 3) Estado
        final int[] idxLlegada = {0};
        List<String> atendidosLista = new ArrayList<>();
        List<String> atendidosArbol = new ArrayList<>();

        // 4) Ejecutores
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);

        // --- Llegadas cada 5s ---
        Runnable llegadaTask = () -> {
            if (idxLlegada[0] >= lote.size()) return;
            Paciente p = lote.get(idxLlegada[0]++);
            lista.agregaOrdenado(p);
            arbol.insert(p);
            System.out.println("\n[+" + (idxLlegada[0] * 5) + "s] ➡ Llega: " + p);
            System.out.println("Árbol (inorden simplificado):");
            System.out.println(arbol.inOrden());
        };

        // --- Atención cada 20s ---
        Runnable atencionTask = () -> {
            Paciente a = lista.popNext();
            Paciente b = arbol.pollNext();

            if ((a == null) && (b == null)) {
                if (idxLlegada[0] >= lote.size()) {
                    System.out.println("\n✅ SIMULACIÓN TERMINADA");
                    System.out.println("Atendidos total: " + atendidosLista.size());
                    System.out.println("Coincidencia lista/árbol: " + atendidosLista.equals(atendidosArbol));
                    int mostrar = Math.min(10, atendidosLista.size());
                    System.out.println("Primeros " + mostrar + " atendidos: "
                            + atendidosLista.subList(0, mostrar));
                    exec.shutdown();
                }
                return;
            }

            if ((a == null) != (b == null)) {
                System.err.println("❌ ERROR: estructuras se vaciaron en momentos distintos");
                exec.shutdownNow();
                return;
            }

            if (a != null && b != null) {
                atendidosLista.add(a.getIdentificacion());
                atendidosArbol.add(b.getIdentificacion());
                if (!a.getIdentificacion().equals(b.getIdentificacion())) {
                    System.err.println("❌ ERROR: orden distinto: "
                            + a.getIdentificacion() + " vs " + b.getIdentificacion());
                    exec.shutdownNow();
                    return;
                }
                if (VERBOSE_ATENCION) {
                    System.out.println("[Atención] " + a + "  |  (árbol) " + b);
                }
            }
        };

        System.out.println("⏳ Iniciando simulación (llegadas cada 5s, atención cada 20s)...");
        exec.scheduleAtFixedRate(llegadaTask, 0, DURACION_LLEGADA_MS, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(atencionTask, 20_000L, DURACION_ATENCION_MS, TimeUnit.MILLISECONDS);
    }

    // ===== Generador de pacientes =====
    private static List<Paciente> generarPacientes(int cantidad) {
        List<Paciente> pacientes = new ArrayList<>(cantidad);
        Random rnd = new Random();
        for (int i = 1; i <= cantidad; i++) {
            String nombre = "Paciente" + i;
            int edad = 1 + rnd.nextInt(90);
            String id = "ID" + i;
            String direccion = "Calle " + i;
            int prioridad = 1 + rnd.nextInt(5);
            pacientes.add(new Paciente(nombre, edad, id, direccion, prioridad));
        }
        return pacientes;
    }
}
