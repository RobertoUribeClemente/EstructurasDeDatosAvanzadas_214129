public class ListaDoble {
    private NodoDoble<Paciente> primero;
    private NodoDoble<Paciente> ultimo;
    private int indice;
    private int contador;

    public ListaDoble(int sumaAsignada) {
        primero = null;
        ultimo = null;
        this.indice=sumaAsignada;
        this.contador=0;
    }

    public int getIndice(){
        return indice;
    }
    public boolean isEmpty() {
        return primero == null;
    }

    public void addLast(Paciente dato) {
        NodoDoble<Paciente> nuevo = new NodoDoble<>(dato, contador++);
        if (isEmpty()) {
            primero = nuevo;
        } else {
            ultimo.setDerecho(nuevo);
            nuevo.setIzquierdo(ultimo);
        }
        ultimo = nuevo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        NodoDoble<Paciente> aux = primero;
        while (aux != null) {
            sb.append(aux.getDato().toString());
            if (aux.getDerecho() != null) sb.append(System.lineSeparator());
            aux = aux.getDerecho();
        }
        return sb.toString();
    }
    public boolean masDeUno(){
        boolean res=false;
        if(primero.getId()!=ultimo.getId()){
            res=true;
        }
        return res;
    }

     public int hayDuplicados() {
        if (primero == null) 
            return 0; // lista vacía

        int duplicados = 0;
        NodoDoble<Paciente> actual = primero;

        while (actual != null) {
            int repeticiones = 1;
            NodoDoble<Paciente> comparador = actual.getDerecho();

            while (comparador != null) {
                if (actual.getDato().equals(comparador.getDato())) 
                    repeticiones++;
                
                comparador = comparador.getDerecho();
            }

            if (repeticiones > 1) 
                duplicados += repeticiones;
            

            actual = actual.getDerecho();
        }

        return duplicados;
    }

    public NodoDoble<Paciente> getPrimero() {
    return primero;
}

}
