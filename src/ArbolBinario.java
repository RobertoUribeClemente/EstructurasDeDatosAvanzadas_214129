public class ArbolBinario {
    private NodoDoble<Paciente> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }
    public boolean estaVacio() {
        return raiz == null;
    }

    public void agregarPaciente(Paciente paciente) {
        NodoDoble<Paciente> nuevo = new NodoDoble<>(paciente);
        if(estaVacio()){
            raiz= nuevo;
        }
        else{
            NodoDoble<Paciente> actual = raiz;
            NodoDoble<Paciente> padre = null;
            int prioridad = paciente.getPrioridad();
            int auxPrioridad;
            boolean esIzquierdo=false;
            while(actual != null){
                auxPrioridad = actual.getDato().getPrioridad();
                padre=actual;

                if(prioridad<auxPrioridad){
                    actual=actual.getIzquierdo();
                    esIzquierdo=true;
                }
                else{
                    actual=actual.getDerecho();
                    esIzquierdo=false;
                }
            }

            if(esIzquierdo){
                padre.setIzquierdo(nuevo);
            }
            else{
                padre.setDerecho(nuevo);
            }
        }
    }

    public NodoDoble<Paciente> buscaPaciente(String id){
        return buscaPaciente(raiz, id);
    }
    private NodoDoble<Paciente> buscaPaciente(NodoDoble<Paciente> nodo, String id){
        if(nodo!=null){
            Paciente aux=nodo.getDato();
            if(aux.getID().equals(id)){
                return nodo;
            }
            else{
                NodoDoble<Paciente> izquierdo = buscaPaciente(nodo.getIzquierdo(), id);
                if(izquierdo!=null){
                    return izquierdo;
                }
                return buscaPaciente(nodo.getDerecho(), id);
            }
        }
        else{
            return null;
        }
    }
    public boolean eliminarPaciente(String id){
        NodoDoble<Paciente> target = buscaPaciente(id); // debe devolver el Nodo
        if (target == null) return false;
        raiz = eliminarNodo(raiz, target);
        return true;
    }
    private NodoDoble<Paciente> eliminarNodo(NodoDoble<Paciente> raiz, NodoDoble<Paciente> target){
        if (raiz == null || target == null) return raiz;

        // 2 hijos → sucesor in-orden
        if (target.getIzquierdo()!=null && target.getDerecho()!=null){
            NodoDoble<Paciente> p = target;
            NodoDoble<Paciente> s = target.getDerecho();
            while (s.getIzquierdo()!=null){
                p = s;
                s = s.getIzquierdo();
            }
            target.setDato(s.getDato());
            if (p.getIzquierdo()==s) p.setIzquierdo(s.getDerecho());
            else p.setDerecho(s.getDerecho());
            return raiz;
        }

        // 0 ó 1 hijo
        NodoDoble<Paciente> h = (target.getIzquierdo()!=null) ? target.getIzquierdo() : target.getDerecho();

        if (target == raiz) return h;

        // enlazar padre→hijo
        NodoDoble<Paciente> padre = raiz;
        int pT = target.getDato().getPrioridad();
        while (padre!=null && padre.getIzquierdo()!=target && padre.getDerecho()!=target){
            int pP = padre.getDato().getPrioridad();
            padre = (pT < pP) ? padre.getIzquierdo() : padre.getDerecho();
        }
        if (padre==null) return raiz;

        if (padre.getIzquierdo()==target) padre.setIzquierdo(h);
        else padre.setDerecho(h);

        return raiz;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringInOrder(raiz, sb);
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }
    private void toStringInOrder(NodoDoble<Paciente> n, StringBuilder sb) {
        if (n == null) return;
        toStringInOrder(n.getIzquierdo(), sb);
        sb.append(n.getDato().toString()).append('\n');
        toStringInOrder(n.getDerecho(), sb);
    }

    public String toStringSimpleArbol() {
        StringBuilder sb = new StringBuilder();
        toStringInOrderSimple(raiz, sb);
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private void toStringInOrderSimple(NodoDoble<Paciente> n, StringBuilder sb) {
        if (n == null) return;
        toStringInOrderSimple(n.getIzquierdo(), sb);
        sb.append(n.getDato().toStringSimple()).append('\n');
        toStringInOrderSimple(n.getDerecho(), sb);
    }


    //Los tres métodos de abajo son para desempatar a los pacientes
    public Paciente pollNext() {
        if (estaVacio()) 
            return null;
    NodoDoble<Paciente> mejor = encontrarMejor(raiz);
    Paciente p = mejor.getDato();
    eliminarPaciente(p.getID());
    return p;
}

    private NodoDoble<Paciente> encontrarMejor(NodoDoble<Paciente> nodo) {
    if (nodo == null) 
        return null;

    NodoDoble<Paciente> izq = encontrarMejor(nodo.getIzquierdo());
    NodoDoble<Paciente> der = encontrarMejor(nodo.getDerecho());
    NodoDoble<Paciente> mejor = nodo;

    if (izq != null && compararPacientes(izq.getDato(), mejor.getDato()) < 0) 
        mejor = izq;
    
    if (der != null && compararPacientes(der.getDato(), mejor.getDato()) < 0) 
        mejor = der;
    
    return mejor;
}

    // devuelve <0 si p1 es mejor (más prioritario) que p2
    private int compararPacientes(Paciente p1, Paciente p2) {
    if (p1.getPrioridad() != p2.getPrioridad()) 
        return Integer.compare(p1.getPrioridad(), p2.getPrioridad());
    
    return Integer.compare(p1.getLlegada(), p2.getLlegada());
    }


}
