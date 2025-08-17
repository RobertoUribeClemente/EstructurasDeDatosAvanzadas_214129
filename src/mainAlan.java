public class mainAlan {
    public static void main(String[] args) {
        ListaDoble listaPacientes = new ListaDoble();
        
        Paciente paciente1 = new Paciente("John Doe", 24, "ID001", "123 Main St", 2);
        Paciente paciente2 = new Paciente("Jane Smith", 32, "ID002", "456 Elm St", 5);
        Paciente paciente3 = new Paciente("Alice Johnson", 13, "ID003", "789 Oak St", 3);

        listaPacientes.agregaOrdenado(paciente1);
        listaPacientes.agregaOrdenado(paciente2);
        listaPacientes.agregaOrdenado(paciente3);
        
        listaPacientes.toString();
    }
}