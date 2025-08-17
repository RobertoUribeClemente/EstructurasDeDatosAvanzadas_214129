public class ListaDoble {
    private NodoDoble<Paciente> primero;
    private NodoDoble<Paciente> ultimo;

    public ListaDoble() {
        primero = null;
        ultimo = null;
    }

    public boolean isEmpty(){
        return primero == null;
    }

    public void addFirst(Paciente dato){
        NodoDoble<Paciente> nuevo = new NodoDoble<>(dato);
        if(!isEmpty()){
            nuevo.setNext(primero);
            primero.setBefore(nuevo);
        }
        else{
            ultimo = nuevo;
        }
        primero = nuevo;
    }

    public Paciente delFirst(){
        Paciente eliminado = null;
        if(!isEmpty()){
            eliminado = primero.getDato();
            primero = primero.getNext();
            if(primero==null){
                ultimo = null;
            } else {
                primero.setBefore(null); // corregido: actualizar enlace backward
            }
        }
        return eliminado;
    }

    public void addLast(Paciente dato){
        NodoDoble<Paciente> nuevo = new NodoDoble<>(dato);
        if(isEmpty())
            primero = nuevo;
        else{
            ultimo.setNext(nuevo);
            nuevo.setBefore(ultimo);
        }
        ultimo = nuevo;
    }

    public Paciente delLast(){
        Paciente eliminado = null;
        if(!isEmpty()){
            eliminado = ultimo.getDato();
            ultimo = ultimo.getBefore();
            if(ultimo == null){ //Había 1 sólo nodo
                primero = null;
            } else {
                ultimo.setNext(null); //Eliminamos el enlace al último nodo
            }
        }
        return eliminado;
    }

    public void addAfter(NodoDoble<Paciente> actual, Paciente dato){
        NodoDoble<Paciente> aux = primero;
        while(aux != null && aux != actual){ // comparar referencia para verificar presencia
            aux = aux.getNext();
        }
        if(aux != null){
            NodoDoble<Paciente> nuevo = new NodoDoble<>(dato);
            nuevo.setNext(actual.getNext());
            nuevo.setBefore(actual);
            actual.setNext(nuevo);
            if(nuevo.getNext() != null) {
                nuevo.getNext().setBefore(nuevo);
            } else {
                ultimo = nuevo; // Actualizamos el último nodo si es necesario
            }
        }
        else {
            throw new IllegalArgumentException("El nodo actual no se encuentra en la lista.");
        }
    }

    public Paciente delDato(Paciente dato) {
        Paciente eliminado = null;
        if (!isEmpty() && dato != null) {
            NodoDoble<Paciente> actual = primero;
            while (actual != null && eliminado == null) {
                if (dato.equals(actual.getDato())) {
                    eliminado = delNodo(actual);
                }
                actual = actual.getNext();
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

    public void agregaOrdenado(Paciente obj) {
        if(isEmpty()) {
            addFirst(obj);
        } else {
            NodoDoble<Paciente> actual = primero;
            int p = obj.getPrioridad();
            while(actual != null && actual.getDato().getPrioridad() >= p) {
                actual = actual.getNext();
            }
            if(actual != null){
                addAfter(actual, obj);
            }
            else{
                addLast(obj);
            }
        }
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