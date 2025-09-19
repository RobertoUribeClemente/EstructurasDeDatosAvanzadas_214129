public class NodoDoble<T> {
private NodoDoble<T> izquierdo;
private NodoDoble<T> derecho;
private T dato;


public NodoDoble(T dato) {
this.dato = dato;
this.izquierdo = null;
this.derecho = null;
}


public NodoDoble(NodoDoble<T> izquierdo, NodoDoble<T> derecho, T dato) {
this.izquierdo = izquierdo;
this.derecho = derecho;
this.dato = dato;
}


public NodoDoble<T> getIzquierdo() { return izquierdo; }
public void setIzquierdo(NodoDoble<T> izquierdo) { this.izquierdo = izquierdo; }


public NodoDoble<T> getDerecho() { return derecho; }
public void setDerecho(NodoDoble<T> derecho) { this.derecho = derecho; }


public T getDato() { return dato; }
public void setDato(T dato) { this.dato = dato; }


public boolean esHoja() { return izquierdo == null && derecho == null; }
}