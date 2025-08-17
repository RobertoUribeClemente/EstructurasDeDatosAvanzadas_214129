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
                if(actual!=null){
                    listaPacientes.addAfter(actual, paciente);
                }
                else{
                    listaPacientes.addLast(paciente);
                }
        }
    }
    public static void main(String[] args) {
        listaPacientes = new ListaDoble();
        
        Paciente paciente1 = new Paciente("John Doe", 2);
        Paciente paciente2 = new Paciente("Jane Smith", 5);
        Paciente paciente3 = new Paciente("Alice Johnson", 3);
        
        listaPacientes.agregaOrdenado(paciente1);
        listaPacientes.agregaOrdenado(paciente2);
        listaPacientes.agregaOrdenado(paciente3);
        
        listaPacientes.toString();
    }
}