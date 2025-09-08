public class Paciente {
    private String datos;

    public Paciente(String datos){
        this.datos=datos;
    }
    
    public String getDatos(){
        return datos;
    }
    @Override
    public int hashCode() {
        int suma=-1;
        if(!datos.isBlank()){
            String[] particion= datos.split(" ");
            suma=0;
            int aux=0;
            int n=particion.length;
            for(int i=0; i<n; i++){
                aux=isInt(particion[i]);
                if(aux!=-1){
                suma+=aux;
                }
                else{
                    return -1;
                }
            }
        }
        return suma;
    }
    @Override
    public boolean equals(Object otro){
        boolean res=false;
        if(otro instanceof Paciente paciente){
            if(paciente.getDatos().equalsIgnoreCase(datos)){
                res=true;
            }
        }
        return res;
    }
    private int isInt(String txt){
        int res=-1;
        try {
            res=Integer.parseInt(txt);
        } 
        catch (NumberFormatException e) {
        }
        return res;
    }
}
