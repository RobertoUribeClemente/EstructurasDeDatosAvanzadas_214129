import java.util.Iterator;

public class ListaDoble<Paciente> implements Iterable<Paciente>{
    private NodoDoble<Paciente> primero;
    private NodoDoble<Paciente> ultimo;

    public ListaDoble() {
        primero = null;
        ultimo = null;
    }

    public boolean isEmpty(){
        return primero == null;
    }
    
    @Override
    public Iterator<Paciente> iterator() {
        return new IteradorDoble<>(primero);                         //Necesitamos este metodo
    }
    
    
    @Override
    public String toString(){
        StringBuilder sB = new StringBuilder();
        for(Paciente dato: this)
            sB.append(dato).append(" ");
        return sB.toString();
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
                ultimo=null;
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
            } //LA LISTA QUEDA VACIA
            else {
                ultimo.setNext(null); //Eliminamos el enlace al último nodo
            }
        }
        return eliminado;
    }
    
    public void addAfter(NodoDoble<Paciente> actual, Paciente dato){
        NodoDoble<Paciente> aux=primero;
        while(aux != null && !aux.equals(actual)){
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

// Método auxiliar para eliminar un nodo y actualizar los enlaces
    private Paciente delNodo(NodoDoble<Paciente> nodo) {
        Paciente dato = nodo.getDato();

        // Caso 1: Nodo es el único en la lista
        if (nodo == primero && nodo == ultimo) {
            primero = null;
            ultimo = null;
        }
        // Caso 2: Nodo es el primero
        else if (nodo == primero) {
            primero = primero.getNext();
            primero.setBefore(null);
        }
        // Caso 3: Nodo es el último
        else 
            if (nodo == ultimo) {
            ultimo = ultimo.getBefore();
            ultimo.setNext(null);
        }
        // Caso 4: Nodo está en el medio
        else {
            nodo.getBefore().setNext(nodo.getNext());
            nodo.getNext().setBefore(nodo.getBefore());
        }
        return dato;
    }
    
    public void agregaOrdenado(Paciente obj, int p) {
        if(isEmpty()) {
            addFirst(obj);
        } else {
            NodoDoble<Paciente> actual = primero;
            while(actual != null && actual.getPrioridad() >= p) {
                    actual = actual.getNext();
            }
                if(actual!=null){
                    addAfter(actual, obj);
                }
                else{
                    addLast(obj);
                }
        }
    }