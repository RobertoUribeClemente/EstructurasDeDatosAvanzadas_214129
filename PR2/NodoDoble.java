public class NodoDoble <T> {
    private NodoDoble<T> izquierdo;
    private NodoDoble<T> derecho;
    private T dato;

    public NodoDoble(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    public NodoDoble(NodoDoble<T> anterior, NodoDoble<T> siguiente, T dato) {
        this.izquierdo = anterior;
        this.derecho = siguiente;
        this.dato = dato;
    }

    public NodoDoble<T> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoDoble<T> anterior) {
        this.izquierdo = anterior;
    }

    public NodoDoble<T> getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoDoble<T> siguiente) {
        this.derecho = siguiente;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    } 
}