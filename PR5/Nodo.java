public class Nodo <T> {
    private Nodo<T> siguiente;
    private int indice;
    private int frecuencia;

    public Nodo(int i, int fi) {
        this.indice=i;
        this.frecuencia=fi;
        this.siguiente=null;
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

    public int getFrecuencia() {
        return this.frecuencia;
    }

    public int getIndice(){
        return indice;
    }
}