public class ABinBusPrioridad {

    private NodoDoble<Paciente> raiz;

    public ABinBusPrioridad() {
        raiz = null;
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    // Insertar: ordena por prioridad ascendente.
    // Si la prioridad es igual, el nuevo paciente VA A LA DERECHA (dup-right) para preservar FIFO por llegada.
    public void insert(Paciente p) {
        if (raiz == null) {
            raiz = new NodoDoble<>(p);
            return;
        }
        NodoDoble<Paciente> actual = raiz;
        NodoDoble<Paciente> padre = null;
        boolean aIzq = false;
        int pr = p.getPrioridad();

        while (actual != null) {
            int nodePr = actual.getDato().getPrioridad();
            if (pr < nodePr) {
                padre = actual; aIzq = true; actual = actual.getBefore();
            } else {
                // pr >= nodePr → a la derecha (incluye duplicados)
                padre = actual; aIzq = false; actual = actual.getNext();
            }
        }
        if (aIzq) padre.setBefore(new NodoDoble<>(p));
        else padre.setNext(new NodoDoble<>(p));
    }

    // Buscar paciente por ID (DFS)
    public Paciente searchById(String id) {
        return searchByIdRec(raiz, id);
    }

    private Paciente searchByIdRec(NodoDoble<Paciente> node, String id) {
        if (node == null) return null;
        Paciente cur = node.getDato();
        if (cur != null && cur.getIdentificacion().equalsIgnoreCase(id)) return cur;
        Paciente left = searchByIdRec(node.getBefore(), id);
        if (left != null) return left;
        return searchByIdRec(node.getNext(), id);
    }

    // Eliminar por ID: devuelve el paciente eliminado (o null si no se encuentra)
    public Paciente deleteById(String id) {
        Paciente[] eliminado = new Paciente[1];
        raiz = deleteByIdRec(raiz, id, eliminado);
        return eliminado[0];
    }

    private NodoDoble<Paciente> deleteByIdRec(NodoDoble<Paciente> node, String id, Paciente[] eliminado) {
        if (node == null) return null;

        // ¿Este nodo?
        if (node.getDato().getIdentificacion().equalsIgnoreCase(id)) {
            eliminado[0] = node.getDato();
            return deleteRoot(node);
        }

        // Buscar en subárbol izquierdo
        node.setBefore(deleteByIdRec(node.getBefore(), id, eliminado));
        if (eliminado[0] != null) return node;

        // Buscar en subárbol derecho
        node.setNext(deleteByIdRec(node.getNext(), id, eliminado));
        return node;
    }

    // Eliminar la raíz de un subárbol y devolver la nueva raíz del subárbol
    private NodoDoble<Paciente> deleteRoot(NodoDoble<Paciente> node) {
        if (node.getBefore() == null && node.getNext() == null) return null;
        if (node.getBefore() == null) return node.getNext();
        if (node.getNext() == null) return node.getBefore();

        // Dos hijos: reemplazar por sucesor inorden (mínimo del subárbol derecho)
        NodoDoble<Paciente>[] minNode = new NodoDoble[1];
        node.setNext(removeMin(node.getNext(), minNode));
        node.setDato(minNode[0].getDato());
        return node;
    }

    // Quita el mínimo (extremo izquierdo) de un subárbol y devuelve el nuevo subárbol;
    // deja el nodo mínimo en minNode[0].
    private NodoDoble<Paciente> removeMin(NodoDoble<Paciente> node, NodoDoble<Paciente>[] minNode) {
        if (node.getBefore() == null) {
            minNode[0] = node;
            return node.getNext();
        }
        node.setBefore(removeMin(node.getBefore(), minNode));
        return node;
    }

    // Saca el siguiente paciente (más urgente / FIFO entre iguales)
    public Paciente pollNext() {
        if (raiz == null) return null;
        Paciente[] out = new Paciente[1];
        raiz = removeLeftmost(raiz, out);
        return out[0];
    }

    // Elimina el nodo más a la izquierda del árbol (mínima prioridad) y devuelve el nuevo árbol.
    private NodoDoble<Paciente> removeLeftmost(NodoDoble<Paciente> node, Paciente[] out) {
        if (node.getBefore() == null) {
            out[0] = node.getDato();
            return node.getNext();
        }
        node.setBefore(removeLeftmost(node.getBefore(), out));
        return node;
    }

    // Inorden: listado por prioridad ascendente; mismo formato que ListaDoble.toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringInOrder(raiz, sb);
        return sb.toString();
    }

    private void toStringInOrder(NodoDoble<Paciente> node, StringBuilder sb) {
        if (node == null) return;
        toStringInOrder(node.getBefore(), sb);
        sb.append(node.getDato().toString());
        // Añadir salto de línea si no es el último (detectable si hay algo a la derecha
        // o si queda algo a la izquierda de esa derecha). Para simplicidad, agrega y recorta al final:
        sb.append(System.lineSeparator());
        toStringInOrder(node.getNext(), sb);
    }
    // Inorden: listado por prioridad ascendente (1..10)
    public String inOrden() {
        StringBuilder sb = new StringBuilder();
        inOrdenRec(raiz, sb);
        return sb.toString();
    }

    private void inOrdenRec(NodoDoble<Paciente> node, StringBuilder sb) {
        if (node == null) return;
        inOrdenRec(node.getBefore(), sb);
        sb.append(node.getDato().toString()).append(System.lineSeparator());
        inOrdenRec(node.getNext(), sb);
    }

}
