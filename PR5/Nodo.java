public class Nodo <T> {
    private Nodo<T> siguiente;
    private int indice;
    private int frecuencia;

    public Nodo(int i, char fi) {
        this.indice=i;
        this.frecuencia=fi;

    }

    public Nodo(Nodo<T> siguiente, int i, int fi) {
        this.siguiente = siguiente;
        this.indice=i;
        this.frecuencia=fi;
    }

    public Nodo<T> getNext() {
        return this.siguiente;
    }

    public void setNext(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

    public int getDato() {
        return this.frecuencia;
    }

}