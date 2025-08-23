public class ListaDoble {
    private NodoDoble<Paciente> primero;
    private NodoDoble<Paciente> ultimo;

    public ListaDoble() {
        primero = null;
        ultimo = null;
    }

    public boolean isEmpty() {
        return primero == null;
    }

    public void addFirst(Paciente dato) {
        NodoDoble<Paciente> nuevo = new NodoDoble<>(dato);
        if (!isEmpty()) {
            nuevo.setNext(primero);
            primero.setBefore(nuevo);
        } else {
            ultimo = nuevo;
        }
        primero = nuevo;
    }

    public void addLast(Paciente dato) {
        NodoDoble<Paciente> nuevo = new NodoDoble<>(dato);
        if (isEmpty()) {
            primero = nuevo;
        } else {
            ultimo.setNext(nuevo);
            nuevo.setBefore(ultimo);
        }
        ultimo = nuevo;
    }

    private void addBefore(NodoDoble<Paciente> actual, Paciente dato) {
        if (actual == primero) {
            addFirst(dato);
            return;
        }
        NodoDoble<Paciente> nuevo = new NodoDoble<>(dato);
        NodoDoble<Paciente> prev = actual.getBefore();
        prev.setNext(nuevo);
        nuevo.setBefore(prev);
        nuevo.setNext(actual);
        actual.setBefore(nuevo);
    }

    public Paciente delFirst() {
        Paciente eliminado = null;
        if (!isEmpty()) {
            eliminado = primero.getDato();
            primero = primero.getNext();
            if (primero == null) {
                ultimo = null;
            } else {
                primero.setBefore(null);
            }
        }
        return eliminado;
    }

    public Paciente delLast() {
        Paciente eliminado = null;
        if (!isEmpty()) {
            eliminado = ultimo.getDato();
            ultimo = ultimo.getBefore();
            if (ultimo == null) primero = null;
            else ultimo.setNext(null);
        }
        return eliminado;
    }

    public void agregaOrdenado(Paciente obj) {
        if (isEmpty()) {
            addFirst(obj);
            return;
        }
        NodoDoble<Paciente> actual = primero;
        // buscamos el primer nodo cuya dato > obj (según compareTo)
        while (actual != null && actual.getDato().compareTo(obj) <= 0) {
            actual = actual.getNext();
        }
        if (actual == primero) {
            addFirst(obj);
        } else if (actual == null) {
            addLast(obj);
        } else {
            addBefore(actual, obj);
        }
    }

    public Paciente delDato(Paciente dato) {
        Paciente eliminado = null;
        if (!isEmpty() && dato != null) {
            NodoDoble<Paciente> actual = primero;
            while (actual != null && eliminado == null) {
                if (dato.equals(actual.getDato())) {
                    eliminado = delNodo(actual);
                } else {
                    actual = actual.getNext();
                }
            }
        }
        return eliminado;
    }

    private Paciente delNodo(NodoDoble<Paciente> nodo) {
        Paciente dato = nodo.getDato();
        if (nodo == primero && nodo == ultimo) {
            primero = null;
            ultimo = null;
        } else if (nodo == primero) {
            primero = primero.getNext();
            if (primero != null) primero.setBefore(null);
        } else if (nodo == ultimo) {
            ultimo = ultimo.getBefore();
            if (ultimo != null) ultimo.setNext(null);
        } else {
            nodo.getBefore().setNext(nodo.getNext());
            nodo.getNext().setBefore(nodo.getBefore());
        }
        return dato;
    }

    public Paciente searchById(String id) {
        NodoDoble<Paciente> aux = primero;
        while (aux != null) {
            if (aux.getDato().getIdentificacion().equalsIgnoreCase(id)) {
                return aux.getDato();
            }
            aux = aux.getNext();
        }
        return null;
    }

    // Saca el siguiente paciente (más urgente / FIFO entre iguales)
    public Paciente popNext() {
        return delFirst();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        NodoDoble<Paciente> aux = primero;
        while (aux != null) {
            sb.append(aux.getDato().toString());
            if (aux.getNext() != null) sb.append(System.lineSeparator());
            aux = aux.getNext();
        }
        return sb.toString();
    }
}
