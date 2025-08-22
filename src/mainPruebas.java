import java.util.*;

public class mainPruebas {

    public static void main(String[] args) {
        // 1) Crear pacientes (llegada la asigna Paciente.aux de forma única y secuencial)
        List<Paciente> lote = new ArrayList<>();
        lote.add(new Paciente("Ana", 30, "A01", "Calle 1", 3));
        lote.add(new Paciente("Beto", 45, "A02", "Calle 2", 1));
        lote.add(new Paciente("Caro", 25, "A03", "Calle 3", 2));
        lote.add(new Paciente("Dani", 40, "A04", "Calle 4", 1));
        lote.add(new Paciente("Elena", 50, "A05", "Calle 5", 5));
        lote.add(new Paciente("Fabi", 28, "A06", "Calle 6", 3));
        lote.add(new Paciente("Gus", 35, "A07", "Calle 7", 2));
        lote.add(new Paciente("Hugo", 60, "A08", "Calle 8", 4));
        lote.add(new Paciente("Iris", 33, "A09", "Calle 9", 1));
        lote.add(new Paciente("Juan", 27, "A10", "Calle 10", 4));

        // 2) Insertar mismas instancias en ambas estructuras
        ListaDoble lista = new ListaDoble();
        ABinBusPrioridad arbol = new ABinBusPrioridad();
        for (Paciente p : lote) {
            lista.agregaOrdenado(p); // prioridad asc, luego llegada asc (compareTo)
            arbol.insert(p);         // prioridad asc; empates a la derecha (FIFO por estructura)
        }

        System.out.println("== ListaDoble (ordenado) ==");
        System.out.println(lista);
        System.out.println("\n== Árbol (inorden) ==");
        System.out.println(arbol.toString());

        // 3) Buscar por ID
        System.out.println("\nBuscar A05 en lista: " + lista.searchById("A05"));
        System.out.println("Buscar A05 en árbol: " + arbol.searchById("A05"));

        // 4) Eliminar por ID SIN crear nuevos Paciente (para no consumir 'aux')
        Paciente pA06 = lista.searchById("A06");
        if (pA06 != null) {
            System.out.println("\nEliminar A06 de lista: " + lista.delDato(pA06));
        } else {
            System.out.println("\nA06 no encontrado en lista");
        }
        System.out.println("Eliminar A06 de árbol: " + arbol.deleteById("A06"));

        Paciente pA02 = lista.searchById("A02");
        if (pA02 != null) {
            System.out.println("Eliminar A02 de lista: " + lista.delDato(pA02));
        } else {
            System.out.println("A02 no encontrado en lista");
        }
        System.out.println("Eliminar A02 de árbol: " + arbol.deleteById("A02"));

        // 5) Atender y comparar orden (Lista vs Árbol)
        System.out.println("\n== Atención secuencial (deben coincidir) ==");
        List<String> atendidosLista = new ArrayList<>();
        List<String> atendidosArbol = new ArrayList<>();

        while (!lista.isEmpty() || !arbol.isEmpty()) {
            Paciente p1 = lista.popNext();   // prioridad asc, FIFO por llegada
            Paciente p2 = arbol.pollNext();  // prioridad asc (mínimo); empates respetan llegada por dup-right

            System.out.println("Lista: " + p1 + "  |  Árbol: " + p2);

            if ((p1 == null) != (p2 == null)) {
                throw new AssertionError("Estructuras se vaciaron en momentos distintos");
            }
            if (p1 != null && p2 != null) {
                atendidosLista.add(p1.getIdentificacion());
                atendidosArbol.add(p2.getIdentificacion());
                if (!p1.getIdentificacion().equals(p2.getIdentificacion())) {
                    throw new AssertionError("Orden distinto: " + p1.getIdentificacion() + " vs " + p2.getIdentificacion());
                }
            }
        }

        System.out.println("\n✅ PRUEBAS TERMINADAS");
        System.out.println("Orden atendidos (ListaDoble): " + atendidosLista);
        System.out.println("Orden atendidos (Árbol)     : " + atendidosArbol);
    }
}
