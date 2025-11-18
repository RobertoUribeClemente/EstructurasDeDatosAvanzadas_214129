public class Nodo {
    private Nodo siguiente;
    private int indice;
    private int frecuencia;

    public Nodo(int i, int fi) {
        this.indice = i;
        this.frecuencia = fi;
        this.siguiente = null;
    }

    public Nodo(Nodo siguiente, int i, int fi) {
        this.siguiente = siguiente;
        this.indice = i;
        this.frecuencia = fi;
    }

    public Nodo getNext() {
        return this.siguiente;
    }

    public void setNext(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public int getFrecuencia() {
        return this.frecuencia;
    }

    public int getIndice() {
        return indice;
    }
}