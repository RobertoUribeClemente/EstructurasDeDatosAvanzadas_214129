import java.util.*;

public class Pila<T> {

    @SuppressWarnings("unchecked")
    T[] pila;
    protected int tope;
    private final int MAX = 10;

    @SuppressWarnings("unchecked")
    public Pila() {
        pila = (T[]) new Object[MAX];
        tope = -1; // Indica pila vacía
    }

    @SuppressWarnings("unchecked")
    public Pila(int max) {
        pila = (T[]) new Object[max];
        tope = -1; // Indica pila vacía
    }

    @SuppressWarnings("unchecked")
    private void expande() {
        T[] masGrande = (T[]) new Object[pila.length * 2];

        for (int i = 0; i <= tope; i++)
            masGrande[i] = pila[i];
        pila = masGrande;
    }

    public void push(T dato) {
        if (tope == pila.length - 1)
            expande();
        tope++;
        pila[tope] = dato;
    }

    /**
     * @return un dato de Tipo T: es el dato del tope de la pila
     */
    public T pop() {

        if (!isEmpty()) {
            T resultado = pila[tope];
            pila[tope] = null;
            tope--;
            return resultado;
        }
        throw new ExcepcionColeccionVacia("La pila no tiene elementos.");
    }

    /**
     * @return un boolean
     * <ul>
     * <li> true si la pila esta vacia </li>
     * <li> false si la pila tiene, al menos, un elemento</li>
     * </ul>
     */
    public boolean isEmpty() {
        return tope == -1;
    }

    public T peek() {

        if (!isEmpty())
            return pila[tope];
        throw new ExcepcionColeccionVacia("La pila no tiene elementos.");
    }

    @Override
    public String toString() {
        StringBuilder sB = new StringBuilder("\n      Datos de la pila:  ");

        for (int i = 0; i <= tope; i++)
            sB.append(pila[i]).append(" ");
        return sB.toString();
    }

    // Problema 21, b)
    public ArrayList<T> multiPop(int n) {
        ArrayList<T> resp = new ArrayList<>();
        ArrayList<T> temp = new ArrayList<>();
        int i = 0;

        while (i < n && !isEmpty()) {
            temp.add(pop());
            i++;
        }

        if (i == n)
            resp.addAll(temp);

        else
            for (int j = temp.size() - 1; j >= 0; j--)
                push(temp.get(j));

        return resp;
    }

    // José Roberto Uribe Clemente 214129
    @Override
    public boolean equals(Object obj) {
        boolean resp = true;
        if (this == obj) // Solo si acaso son exactamente iguales
            return resp;

        if (obj instanceof Pila) {
            // uso raw Pila para auxiliares porque pueden contener objetos de distinto tipo
            Pila aux1 = new Pila(); // pila auxiliar para this
            Pila aux2 = new Pila(); // pila auxiliar para obj
            Pila other = (Pila) obj;

            while (resp && !this.isEmpty() && !other.isEmpty()) {
                Object a = this.peek();
                Object b = other.peek();
                if (a == null) {
                    if (b == null) {
                        aux1.push(this.pop());
                        aux2.push(other.pop());
                    } else {
                        resp = false;
                    }
                } else if (a.equals(b)) {
                    aux1.push(this.pop());
                    aux2.push(other.pop());
                } else
                    resp = false;
            }
            if (resp && (!this.isEmpty() || !other.isEmpty())) // tamaños distintos
                resp = false;

            // Reconstruir ambas pilas (restaurar estado original)
            while (!aux1.isEmpty()) // Se reconstruye la pila original
                this.push((T) aux1.pop());

            while (!aux2.isEmpty()) // Se reconstruye el obj
                other.push(aux2.pop());
        } else
            resp = false;

        return resp;
    }

    // Excepción simple incluida para que el archivo compile por sí solo.
    public static class ExcepcionColeccionVacia extends RuntimeException {
        public ExcepcionColeccionVacia(String msg) {
            super(msg);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {

        Pila<Integer> p1 = new Pila();
        Object o1 = new String("Hola");
        boolean resp;
        p1.push(1);
        p1.push(2);
        p1.push(3);

        resp = p1.equals(o1); // Se espera que resp sea falso, puesto que una pila no es lo mismo que un String
        if (resp)
            System.out.println("\nSon iguales");
        else
            System.out.println("\nNO son iguales");

        o1 = new Pila(); // Se cambia de variable a o1
        ((Pila) o1).push(1);
        ((Pila) o1).push(2);
        ((Pila) o1).push(3);

        resp = p1.equals(o1);
        if (resp) // Se espera un resp = true
            System.out.println("\nSon iguales");
        else
            System.out.println("\nNO son iguales");

        ((Pila) o1).pop();
        ((Pila) o1).pop();
        ((Pila) o1).pop();

        ((Pila) o1).push("Hola");
        ((Pila) o1).push(2);
        ((Pila) o1).push(3);

        resp = p1.equals(o1);
        if (resp) // Se espera un resp = false
            System.out.println("\nSon iguales");
        else
            System.out.println("\nNO son iguales");
        System.out.println("\n" + p1 + o1);

        // Ahora se probará con otra Pila, pero con más variables y no sólo con Integer

        Pila p2 = new Pila();
        p2.push(1);
        p2.push("Adios");
        p2.push(1.4);
        p2.push('c');
        p2.push(true);

        o1 = new Pila();
        ((Pila) o1).push(1);
        ((Pila) o1).push("Adios");
        ((Pila) o1).push(1.4);
        ((Pila) o1).push('c');
        ((Pila) o1).push(true);
        System.out.println("\n" + p2 + o1); // Se ven los datos tanto de o1 como de p2

        resp = p2.equals(o1);
        if (resp) // Se espera un resp = true
            System.out.println("\nSon iguales");
        else
            System.out.println("\nNO son iguales");
        System.out.println("\n" + p2 + o1);

        resp = p1.equals((Object) p2); // Se espera un resp = false
        if (resp)
            System.out.println("\nSon iguales");
        else
            System.out.println("\nNO son iguales");

        resp = p2.equals((Object) p2); // Se compara entre sí p1. Se espera un resp = true
        if (resp)
            System.out.println("\nSon iguales");
        else
            System.out.println("\nNO son iguales");
    }
}
