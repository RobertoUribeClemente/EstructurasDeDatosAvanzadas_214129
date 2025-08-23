public class mainPruebas {
    private static boolean comparar(ArbolBinario arbol, ListaDoble lista) {

        String sa = normaliza(arbol.toString());      
        String sl = normaliza(lista.toString());    


        boolean iguales = sa.equals(sl);
        System.out.println("\n=== COMPARACIÓN ===");
        System.out.println(iguales ? "OK ✅ Árbol y Lista coinciden" : "FAIL Árbol y Lista difieren");

        if (!iguales) {
            // Muestra primer diferencia útil
            String[] la = sa.split("\n");
            String[] ll = sl.split("\n");
            int n = Math.min(la.length, ll.length);
            for (int i = 0; i < n; i++) {
                if (!la[i].equals(ll[i])) {
                    System.out.println("\n-- Diferencia en línea " + (i+1) + " --");
                    System.out.println("Árbol: " + la[i]);
                    System.out.println("Lista: " + ll[i]);
                    break;
                }
            }
            if (la.length != ll.length) {
                System.out.println("\n-- Diferencia de tamaño --  árbol=" + la.length + " líneas, lista=" + ll.length + " líneas");
            }

            System.out.println("\n-- Árbol (normalizado) --\n" + sa);
            System.out.println("\n-- Lista (normalizada) --\n" + sl);
        }
        return iguales;
    }

    private static String normaliza(String s) {
        return s
            .replace('\r', '\n')                 // CRLF -> LF
            .replace('\u00A0', ' ')              // NBSP -> espacio normal
            .replaceAll("[ \\t]+", " ")          // colapsa espacios/tabs
            .replaceAll("\\s*\\n\\s*", "\n")     // recorta espacios alrededor de saltos
            .replaceAll("\\n{2,}", "\n")         // colapsa saltos múltiples
            .trim();
    }

    private static void imprimirEstados(ArbolBinario arbol, ListaDoble lista, String titulo) {
        System.out.println("\n================ " + titulo + " ================");
        System.out.println("-- Árbol (in-orden) --");
        System.out.println(arbol.toString());
        System.out.println("-- Lista (izq→der) --");
        System.out.println(lista.toString());
    }

    private static Paciente[] generarPacientesBase() {
        // prioridades con duplicados para probar orden estable (FIFO entre iguales)
        return new Paciente[]{
            new Paciente("Ana",   30, "A01", "Calle 1", 3),
            new Paciente("Beto",  45, "A02", "Calle 2", 1),
            new Paciente("Caro",  25, "A03", "Calle 3", 2),
            new Paciente("Dani",  40, "A04", "Calle 4", 1),
            new Paciente("Ema",   28, "A05", "Calle 5", 3),
            new Paciente("Fede",  33, "A06", "Calle 6", 2),
            new Paciente("Gina",  22, "A07", "Calle 7", 4),
            new Paciente("Hugo",  36, "A08", "Calle 8", 3),
            new Paciente("Iris",  27, "A09", "Calle 9", 2),
            new Paciente("Javi",  50, "A10", "Calle 10",1)
        };
    }

    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();
        ListaDoble lista = new ListaDoble();

        // 1) Inserción ordenada en ambas estructuras
        for (Paciente p : generarPacientesBase()) {
            arbol.agregarPaciente(p);
            lista.agregaOrdenado(p);
        }
        imprimirEstados(arbol, lista, "DESPUÉS DE INSERTAR");
        comparar(arbol, lista);

        // 2) Búsqueda por ID (existente y no existente)
        String idOk = "A06";
        String idNo = "Z99";
        var nodoA06 = arbol.buscaPaciente(idOk);
        var pacA06L = lista.searchById(idOk);
        System.out.println("\nBÚSQUEDA ID=" + idOk + ": árbol=" + (nodoA06 != null) + ", lista=" + (pacA06L != null));
        var nodoZ = arbol.buscaPaciente(idNo);
        var pacZ  = lista.searchById(idNo);
        System.out.println("BÚSQUEDA ID=" + idNo + ": árbol=" + (nodoZ != null) + ", lista=" + (pacZ != null));

        // 3) Eliminación por ID (un intermedio)
        String idEliminar = "A05";
        boolean delA = arbol.eliminarPaciente(idEliminar);
        Paciente enLista = lista.searchById(idEliminar);
        boolean delL = false;
        if (enLista != null) {
            delL = (lista.delDato(enLista) != null);
        }
        System.out.println("\nELIMINAR " + idEliminar + ": árbol=" + delA + ", lista=" + delL);
        imprimirEstados(arbol, lista, "TRAS ELIMINAR " + idEliminar);
        comparar(arbol, lista);

        // 4) popDerecho() de la lista ≙ eliminar el mismo del árbol
        Paciente pop = lista.popDerecho(); // quita el primero (mínima prioridad; FIFO entre iguales)
        if (pop != null) {
            boolean delA2 = arbol.eliminarPaciente(pop.getID());
            System.out.println("\nPOP lista: " + pop.toStringSimple() + " | eliminar en árbol=" + delA2);
        }
        imprimirEstados(arbol, lista, "TRAS popDerecho()");
        comparar(arbol, lista);

        // 5) delLast() de la lista ≙ eliminar el mismo del árbol
        Paciente ult = lista.delLast();
        if (ult != null) {
            boolean delA3 = arbol.eliminarPaciente(ult.getID());
            System.out.println("\nDEL LAST lista: " + ult.toStringSimple() + " | eliminar en árbol=" + delA3);
        }
        imprimirEstados(arbol, lista, "TRAS delLast()");
        comparar(arbol, lista);

        // 6) delDato() (otro ID) ≙ eliminar en árbol
        String idEliminar2 = "A02";
        Paciente enLista2 = lista.searchById(idEliminar2);
        if (enLista2 != null) {
            Paciente borradoL = lista.delDato(enLista2);
            boolean borradoA = arbol.eliminarPaciente(idEliminar2);
            System.out.println("\nDEL DATO " + idEliminar2 + ": árbol=" + borradoA + ", lista=" + (borradoL != null));
        }
        imprimirEstados(arbol, lista, "TRAS delDato(" + idEliminar2 + ")");
        comparar(arbol, lista);

        // 7) Inserciones adicionales (ordenadas) y comparación
        Paciente k1 = new Paciente("Karo", 29, "A11", "Calle 11", 2);
        Paciente k2 = new Paciente("Leo",  41, "A12", "Calle 12", 3);
        arbol.agregarPaciente(k1); lista.agregaOrdenado(k1);
        arbol.agregarPaciente(k2); lista.agregaOrdenado(k2);
        imprimirEstados(arbol, lista, "TRAS INSERTAR A11 y A12");
        comparar(arbol, lista);

        // 8) Pruebas individuales de Lista (addFirst / addLast) – no se comparan con el árbol
        Paciente x1 = new Paciente("Xim", 20, "X01", "Extra 1", 1);
        Paciente x2 = new Paciente("Yon", 21, "X02", "Extra 2", 5);
        lista.addFirst(x1);
        lista.addLast(x2);
        System.out.println("\n(Pruebas Lista) addFirst(X01), addLast(X02)");
        System.out.println(lista.toString());

        // 9) Restaurar orden de Lista quitando los extras para volver a comparar
        Paciente rem1 = lista.delDato(x1);
        Paciente rem2 = lista.delDato(x2);
        System.out.println("\n(Pruebas Lista) remover extras: X01=" + (rem1 != null) + ", X02=" + (rem2 != null));
        imprimirEstados(arbol, lista, "ESTADO FINAL");
        comparar(arbol, lista);

        System.out.println("\n== FIN DE PRUEBAS ==");
    }
}
