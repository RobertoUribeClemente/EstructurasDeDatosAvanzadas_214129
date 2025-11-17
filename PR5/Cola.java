public class Cola {

    private Nodo front;  
    private int n;   

    public Cola() {
        front = null;
        n = 0;
    }

    /**
     * Inserta un nodo en la lista, manteniéndola ordenada
     * de menor a mayor getFrecuencia.
     */
    public void insert(int i, int fi) {
        Nodo nuevoNodo = new Nodo(i, fi);
        if (isEmpty() || fi < front.getFrecuencia()) {
            nuevoNodo.setNext(front);
            front = nuevoNodo;
        } 
        else {
            Nodo actual = front;

            while (actual.getNext() != null && actual.getNext().getFrecuencia() <= fi) {
                actual = actual.getNext();
            }
            nuevoNodo.setNext(actual.getNext());
            actual.setNext(nuevoNodo);
        }
        n++;
    }

    public int extractMin() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }

        // Obtiene el índice del primer nodo (el de menor getFrecuencia)
        int minIndex = front.getIndice();
        front = front.getNext();

        n--;
        return minIndex;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return n;
    }
}