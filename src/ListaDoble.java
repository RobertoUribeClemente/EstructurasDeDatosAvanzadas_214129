public class ListaDoble<T> implements Iterable<T>{
    private NodoDoble<T> primero;
    private NodoDoble<T> ultimo;

    public ListaDoble() {
        primero = null;
        ultimo = null;
    }

    public void agregaInicio(T dato){
        NodoDoble<T> nuevo = new NodoDoble(dato);
        if(estaVacia())
            ultimo = nuevo;
        else{
            nuevo.setSiguiente(primero);
            primero.setAnterior(nuevo);
        }
        primero = nuevo;
    }
    
    public boolean estaVacia(){
        return primero == null;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new IteradorDoble(primero);
    }
    
    public T quitaPrimero(){
        T eliminado = null;
        if(!estaVacia()){
            eliminado = primero.getDato();
            primero = primero.getSiguiente();
            if(primero == null) //Había 1 sólo nodo
                ultimo = null; //LA LISTA QUEDA VACIA
            else{
                primero.getAnterior().setSiguiente(null);
                primero.setAnterior(null);
            }
                
        }
        return eliminado;
    }
    
    @Override
    public String toString(){
        StringBuilder sB = new StringBuilder();
        for(T dato: this)
            sB.append(dato).append(" ");
        return sB.toString();
    }
    
    public T quitaUltimo(){
        T eliminado = null;
        if(!estaVacia()){
            eliminado = ultimo.getDato();
            ultimo = ultimo.getAnterior();
            if(ultimo == null) //Había 1 sólo nodo
                primero = null; //LA LISTA QUEDA VACIA
            else{
                ultimo.getSiguiente().setAnterior(null);
                ultimo.setSiguiente(null);
            }
                
        }
        return eliminado;
    }
    
    public void agregaFinal(T dato){
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if(estaVacia())
            primero = nuevo;
            
        else{
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
            
        }
        ultimo = nuevo;
    }
    
    public T quitaDato(T dato) {
        T eliminado = null;
        if (!estaVacia() && dato != null) {
            NodoDoble<T> actual = primero;
            while (actual != null && eliminado == null) {
                if (dato.equals(actual.getDato())) {
                    eliminado = eliminarNodo(actual);
                }
                actual = actual.getSiguiente();
            }
        }
        return eliminado;
    }

// Método auxiliar para eliminar un nodo y actualizar los enlaces
    private T eliminarNodo(NodoDoble<T> nodo) {
        T dato = nodo.getDato();

        // Caso 1: Nodo es el único en la lista
        if (nodo == primero && nodo == ultimo) {
            primero = null;
            ultimo = null;
        }
        // Caso 2: Nodo es el primero
        else if (nodo == primero) {
            primero = primero.getSiguiente();
            primero.setAnterior(null);
        }
        // Caso 3: Nodo es el último
        else 
            if (nodo == ultimo) {
            ultimo = ultimo.getAnterior();
            ultimo.setSiguiente(null);
        }
        // Caso 4: Nodo está en el medio
        else {
            nodo.getAnterior().setSiguiente(nodo.getSiguiente());
            nodo.getSiguiente().setAnterior(nodo.getAnterior());
        }

        // Limpia los enlaces del nodo eliminado
        nodo.setAnterior(null);
        nodo.setSiguiente(null);

        return dato;
    }
    
    public void intercalaListas(ListaDoble<T> otraL){
        if(!estaVacia()){
            if(!otraL.estaVacia()){
                
            }
        }
        else
            if(!otraL.estaVacia()){
                
            }
    }
}