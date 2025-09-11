import java.util.Arrays;

public class Paciente {
    private int[] datos;

    public Paciente(int[] datos){
        if(datos.length != 10)
            throw new IllegalArgumentException("Un paciente debe tener exactamente 10 datos");
            //Esto se hizo con el propósito de que se pueda comparar los datos unos con otros. No es lo mismo comparar "002" que "2" ocn el String
        this.datos = datos.clone();
    }
    
    public int[] getDatos(){
        return datos.clone();
    }
    @Override
    public int hashCode() {
       return Arrays.hashCode(datos);
    }
    @Override
    public boolean equals(Object otro){
        boolean resp = false;
        if (this == otro)
            resp = true;
        if(!(otro instanceof Paciente))
            return resp;
        Paciente p = (Paciente) otro;
        resp = Arrays.equals(this.datos, p.datos);
        
        return resp;
    }
    
    public String toString(){
        return Arrays.toString(datos);
    }
}
