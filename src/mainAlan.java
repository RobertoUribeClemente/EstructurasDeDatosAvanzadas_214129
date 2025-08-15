public class mainAlan {
    private ListaDoble listaPacientes;

    public agregaOrdenado(Paciente paciente) {
        if(ListaDoble.isEmpty()) {
            listaPacientes.addFirst(paciente);
        } else {
                int p= paciente.getPrioridad();
                Paciente actual = listaPacientes.getFirst();
                while(actual != null && actual.getPrioridad() >= p) {
                    actual = listaPacientes.getNext(actual);
                }
                listaPacientes.addAfter(actual, paciente);
        }
    }
    public static void main(String[] args) {

    }
}