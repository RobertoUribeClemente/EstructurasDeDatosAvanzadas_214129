public class ListaDoble<T> implements Iterable<T>{
    private NodoDoble<T> primero;
    private NodoDoble<T> ultimo;

    public ListaDoble() {
        primero = null;
        ultimo = null;
    }

    public boolean isEmpty(){
        return primero == null;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new IteradorDoble(primero);                         //Necesitamos este metodo
    }
    
    
    @Override
    public String toString(){
        StringBuilder sB = new StringBuilder();
        for(T dato: this)
            sB.append(dato).append(" ");
        return sB.toString();
    }

    public void addFirst(T dato){
        NodoDoble<T> nuevo = new NodoDoble(dato);
        if(!isEmpty()){
            nuevo.setNext(primero);
            primero.setBefore(nuevo);
        }
        else{
            ultimo = nuevo; 
        }
        primero = nuevo;
    }

    public T delFirst(){
        T eliminado = null;
        if(!isEmpty()){
            eliminado = primero.getDato();           
            primero = primero.getNext();
            if(primero==null){
                ultimo=null;
            }
        }
        return eliminado;
    }

    public void addLast(T dato){
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if(isEmpty())
            primero = nuevo;
        else{
            ultimo.setNext(nuevo);
            nuevo.setBefore(ultimo);
            
        }
        ultimo = nuevo;
    }
        
    public T delLast(){
        T eliminado = null;
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
    
    public void addAfter(NodoDoble<T> actual, T dato){
        NodoDoble<T> aux=primero;
        while(aux != null && !aux.equals(actual)){
            aux = aux.getNext();
        }
        if(aux != null){
            NodoDoble<T> nuevo = new NodoDoble<>(dato);
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
    
    public T delDato(T dato) {
        T eliminado = null;
        if (!isEmpty() && dato != null) {
            NodoDoble<T> actual = primero;
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
    private T delNodo(NodoDoble<T> nodo) {
        T dato = nodo.getDato();

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
    
    public void intercalaListas(ListaDoble<T> otraL){
        if(!isEmpty()){
            if(!otraL.isEmpty()){
                
            }
        }
        else
            if(!otraL.isEmpty()){
                
            }
    }
}