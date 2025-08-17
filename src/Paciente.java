public class Paciente {
    private String nombre;
    private int edad;
    private String identificacion;
    private String direccion;
    private int prioridad;

    public Paciente(String nombre, int edad, String identificacion, String direccion, int prioridad) {
        this.nombre = nombre;
        this.edad = edad;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.prioridad = prioridad;
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

    @Override
    public String toString() {
        return String.format("Paciente[nombre=%s, edad=%d, identificacion=%s, direccion=%s, prioridad=%d]",
                nombre, edad, identificacion, direccion, prioridad);
    }
}