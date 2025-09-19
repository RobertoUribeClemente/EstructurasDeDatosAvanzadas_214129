
public class Pila<T> {
    private T[] datos;
    private int tope;
    private static final int CAPACIDAD_INICIAL = 10;

    @SuppressWarnings("unchecked")
    public Pila() {
        datos = (T[]) new Object[CAPACIDAD_INICIAL];
        tope = 0;
    }

    public boolean isEmpty() {
        return tope == 0;
    }

    public int size() {
        return tope;
    }

    public void push(T elem) {
        if (tope == datos.length) {
            expande();
        }
        datos[tope++] = elem;
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Pila vacía");
        }
        T elem = datos[--tope];
        datos[tope] = null; // limpiar referencia
        return elem;
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Pila vacía");
        }
        return datos[tope - 1];
    }

    @SuppressWarnings("unchecked")
    private void expande() {
        T[] nuevo = (T[]) new Object[datos.length * 2];
        System.arraycopy(datos, 0, nuevo, 0, datos.length);
        datos = nuevo;
    }

    public void clear() {
        for (int i = 0; i < tope; i++) datos[i] = null;
        tope = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pila: [");
        for (int i = tope - 1; i >= 0; i--) {
            sb.append(datos[i]);
            if (i > 0) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
