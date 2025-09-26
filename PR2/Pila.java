
import java.util.*;


public class Pila<T> {

    private T[] pila;
    protected int tope;
    private final int MAX = 10;

    @SuppressWarnings("unchecked")
    public Pila() {
        pila = (T[]) new Object[MAX];
        tope = -1; // indica pila vacía
    }

    @SuppressWarnings("unchecked")
    public Pila(int max) {
        if (max <= 0) max = MAX;
        pila = (T[]) new Object[max];
        tope = -1;
    }

    /** Duplica el arreglo cuando se llena. */
    @SuppressWarnings("unchecked")
    private void expande() {
        T[] masGrande = (T[]) new Object[pila.length * 2];
        for (int i = 0; i <= tope; i++)
            masGrande[i] = pila[i];
        pila = masGrande;
    }

    /** Mete un elemento en el tope. */
    public void push(T dato) {
        if (dato == null) throw new NullPointerException("No se permiten null en la pila");
        if (tope == pila.length - 1)
            expande();
        pila[++tope] = dato;
    }

    /** Saca y devuelve el tope. */
    public T pop() {
        if (isEmpty())
            throw new NoSuchElementException("La pila no tiene elementos.");
        T resultado = pila[tope];
        pila[tope--] = null;
        return resultado;
    }

    /** Revisa el tope sin sacarlo. */
    public T peek() {
        if (isEmpty())
            throw new NoSuchElementException("La pila no tiene elementos.");
        return pila[tope];
    }

    /** true si está vacía. */
    public boolean isEmpty() {
        return tope == -1;
    }

    /** Número de elementos. */
    public int size() {
        return tope + 1;
    }

    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder("\n      Datos de la pila:  ");
        for (int i = 0; i <= tope; i++)
            sB.append(pila[i]).append(" ");
        return sB.toString();
    }

    /**
     * multiPop: intenta desapilar n elementos.
     * - Si hay suficientes, los devuelve en una lista (tope primero).
     * - Si no hay suficientes, restaura la pila y devuelve lista vacía.
     */
    public ArrayList<T> multiPop(int n) {
        if (n < 0) throw new IllegalArgumentException("n no puede ser negativo");
        ArrayList<T> resp = new ArrayList<>();
        ArrayList<T> temp = new ArrayList<>();
        int i = 0;

        while (i < n && !isEmpty()) {
            temp.add(pop());
            i++;
        }

        if (i == n) {
            resp.addAll(temp);
        } else {
            // restaurar en orden
            for (int j = temp.size() - 1; j >= 0; j--)
                push(temp.get(j));
        }
        return resp;
    }

    /**
     * equals: compara contenido y tamaño.
     * Usa pilas auxiliares y luego restaura.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean equals(Object obj) {
        boolean resp = true;
        if (this == obj)
            return true;

        if (obj instanceof Pila) {
            Pila aux1 = new Pila();
            Pila aux2 = new Pila();
            Pila other = (Pila) obj;

            try {
                while (resp && !this.isEmpty() && !other.isEmpty()) {
                    Object a = this.peek();
                    Object b = other.peek();
                    if (Objects.equals(a, b)) {
                        aux1.push(this.pop());
                        aux2.push(other.pop());
                    } else {
                        resp = false;
                    }
                }
                if (resp && (!this.isEmpty() || !other.isEmpty()))
                    resp = false;
            } finally {
                while (!aux1.isEmpty())
                    this.push((T) aux1.pop());
                while (!aux2.isEmpty())
                    other.push(aux2.pop());
            }
        } else {
            resp = false;
    }
        return resp;
    }

   
}
