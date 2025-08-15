public class NodoDoble <T> {
    private NodoDoble<T> anterior;
    private NodoDoble<T> siguiente;
    private T dato;

    public NodoDoble(T dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }

    public NodoDoble(NodoDoble<T> anterior, NodoDoble<T> siguiente, T dato) {
        this.anterior = anterior;
        this.siguiente = siguiente;
        this.dato = dato;
    }

    public NodoDoble<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDoble<T> anterior) {
        this.anterior = anterior;
    }

    public NodoDoble<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDoble<T> siguiente) {
        this.siguiente = siguiente;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }
    
    
    
}