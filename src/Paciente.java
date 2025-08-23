public class Paciente {
    private String nombre;
    private int edad;
    private final String identificacion;
    private String direccion;
    private int prioridad;
    private final int llegada;
    private static int aux = 0;

    public Paciente(String nombre, int edad, String identificacion, String direccion, int prioridad) {
        this.nombre = nombre;
        this.edad = edad;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.prioridad = prioridad;
        this.llegada = aux++;
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
    public String getID() { 
        return identificacion; 
    }
    public String getDireccion() { 
        return direccion; 
    }
    public void setDireccion(String direccion) { 
        this.direccion = direccion; 
    }
    public int getPrioridad() { 
        return prioridad; 
    }
    public void setPrioridad(int prioridad) { 
        this.prioridad = prioridad; 
    }
    public int getLlegada() { return llegada; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paciente p = (Paciente) obj;
        return identificacion.equalsIgnoreCase(p.identificacion);
    }
    @Override
    public String toString() {
        return String.format("""
                             Paciente{
                               id         = %s
                               nombre     = %s
                               edad       = %d
                               direccion  = %s
                               prioridad  = %d
                               llegada    = %d
                             }""",
            identificacion, nombre, edad, direccion, prioridad, llegada
        );
    }
     public String toStringSimple() {
        return String.format("%s | %s (%d) | p=%d | t=%d",
                identificacion, nombre, edad, prioridad, llegada);
    }


}
