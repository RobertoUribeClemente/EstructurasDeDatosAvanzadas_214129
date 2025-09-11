

public class ArregloPacientes {
    private Paciente[] pacientes;
    private int cantidad; //Esto representa el número real de pacientes leídos

    public ArregloPacientes(int capacidad){
        pacientes = new Paciente[capacidad];
        cantidad = 0;
    }
}
