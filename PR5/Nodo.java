public class Nodo <T> {
    private Nodo<T> siguiente;
    private int indice;
    private char letra;

    public Nodo(int i, char fi) {
        this.indice=i;
        this.letra=fi;

    }

    public Nodo(Nodo<T> siguiente, int i, char fi) {
        this.siguiente = siguiente;
        this.indice=i;
        this.letra=fi;
    }

    public Nodo<T> getNext() {
        return this.siguiente;
    }

    public void setNext(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

    public char getDato() {
        return this.letra;
    }

}