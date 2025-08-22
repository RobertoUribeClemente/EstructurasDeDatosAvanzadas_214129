public class Paciente implements Comparable <Paciente> {
    private String nombre;
    private int edad;
    private String identificacion;
    private String direccion;
    private int prioridad;
    private int llegada;
    private static int aux = 0;

    public Paciente(String nombre, int edad, String identificacion, String direccion, int prioridad) {
        this.nombre = nombre;
        this.edad = edad;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.prioridad = prioridad;
        this.llegada = aux;
        aux++;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getIdentificacion() {
        return identificacion;
    }
    public int getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getLlegada(){
        return llegada;
    }

    @Override
    public String toString() {
        return String.format("Paciente[nombre=%s, edad=%d, identificacion=%s, direccion=%s, prioridad=%d, llegada=%d]",
                nombre, edad, identificacion, direccion, prioridad, llegada);
    }

    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paciente paciente = (Paciente) obj;
        return identificacion.equalsIgnoreCase(paciente.identificacion);
    }

    @Override
    public int compareTo(Paciente obj){
        
        
        if(!this.getIdentificacion().equalsIgnoreCase(obj.getIdentificacion())){
        

        if(obj.getPrioridad() > this.getPrioridad())
            return -1;        //Si el obj tiene menos prioridad que el this
        
        else if(obj.getPrioridad() < this.getPrioridad())
            return 1;         //Si el obj tiene más prioridad que el this
        

        // misma prioridad → se compara llegada

        if (obj.getLlegada() > this.getLlegada())
            return -1;
        if (obj.getLlegada() < this.getLlegada())
            return 1;
        }
        return 0;
    }
}