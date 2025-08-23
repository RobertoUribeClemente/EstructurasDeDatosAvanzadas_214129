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
            nuevo.setDerecho(primero);
            primero.setIzquierdo(nuevo);
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
            ultimo.setDerecho(nuevo);
            nuevo.setIzquierdo(ultimo);
        }
        ultimo = nuevo;
    }

    private void addIzquierdo(NodoDoble<Paciente> actual, Paciente dato) {
        if (actual == primero) {
            addFirst(dato);
            return;
        }
        NodoDoble<Paciente> nuevo = new NodoDoble<>(dato);
        NodoDoble<Paciente> prev = actual.getIzquierdo();
        prev.setDerecho(nuevo);
        nuevo.setIzquierdo(prev);
        nuevo.setDerecho(actual);
        actual.setIzquierdo(nuevo);
    }

    public Paciente delFirst() {
        Paciente eliminado = null;
        if (!isEmpty()) {
            eliminado = primero.getDato();
            primero = primero.getDerecho();
            if (primero == null) {
                ultimo = null;
            } else {
                primero.setIzquierdo(null);
            }
        }
        return eliminado;
    }

    public Paciente delLast() {
        Paciente eliminado = null;
        if (!isEmpty()) {
            eliminado = ultimo.getDato();
            ultimo = ultimo.getIzquierdo();
            if (ultimo == null) primero = null;
            else ultimo.setDerecho(null);
        }
        return eliminado;
    }

    public void agregaOrdenado(Paciente obj) {
        if (isEmpty()) {
            addFirst(obj);
            return;
        }
        NodoDoble<Paciente> actual = primero;
        while (actual != null && actual.getDato().getPrioridad() <= obj.getPrioridad()) {
            actual = actual.getDerecho();
        }
        if (actual == primero) {
            addFirst(obj);
        } else if (actual == null) {
            addLast(obj);
        } else {
            addIzquierdo(actual, obj);
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
                    actual = actual.getDerecho();
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
            primero = primero.getDerecho();
            if (primero != null) primero.setIzquierdo(null);
        } else if (nodo == ultimo) {
            ultimo = ultimo.getIzquierdo();
            if (ultimo != null) ultimo.setDerecho(null);
        } else {
            nodo.getIzquierdo().setDerecho(nodo.getDerecho());
            nodo.getDerecho().setIzquierdo(nodo.getIzquierdo());
        }
        return dato;
    }

    public Paciente searchById(String id) {
        NodoDoble<Paciente> aux = primero;
        while (aux != null) {
            if (aux.getDato().getID().equalsIgnoreCase(id)) {
                return aux.getDato();
            }
            aux = aux.getDerecho();
        }
        return null;
    }

    public Paciente popDerecho() {
        return delFirst();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        NodoDoble<Paciente> aux = primero;
        while (aux != null) {
            sb.append(aux.getDato().toString());
            if (aux.getDerecho() != null) sb.append(System.lineSeparator());
            aux = aux.getDerecho();
        }
        return sb.toString();
    }
}
