
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TablaHash {
    private ArrayList<ListaDoble> tabla;

    public TablaHash() {
        tabla = new ArrayList<>();
    }

    public boolean agregarPaciente(String nombre) {
        boolean res=false;
        try {
            FileReader file= new FileReader(nombre);
            Scanner lec = new Scanner(file);

            if(lec.hasNextInt()){
                int n= lec.nextInt();

                for(int i=0; i<n; i++){
                    String datos="";

                    for(int j=0; j<10; j++){
                        datos=lec.nextLine();
                    }

                    Paciente p= new Paciente(datos);
                    int indice=datos.hashCode();
                    int k =buscarIndice(indice);

                    if(k!=-1&&indice!=-1){
                        tabla.get(k).addLast(p);
                    }
                    else{
                        ListaDoble nueva= new ListaDoble(indice);
                        tabla.add(nueva);
                    }
                }
                res=true;
            }
            lec.close();
        } 
        catch (FileNotFoundException ex) {
            
        }
        return res;
    }
    public int buscarIndice(int indice){
        boolean encontrado=false;
        int res=-1;
        if(!tabla.isEmpty()){
            int i=0;
            int n=tabla.size();
            while(!encontrado&&i<n){
                if(tabla.get(i).getIndice()==indice){
                    encontrado=true;
                }
                else{
                    i++;
                }
            }
            if(encontrado){
                res=i;
            }
        }
        return res;
    }
    public boolean hayIguales(){
        boolean res=false;
        int n=tabla.size();
        boolean iguales=false;
        int i=0;
        while(!iguales&&i<n){
            if(tabla.get(i).masDeUno()){
                iguales=true;
            }
            i++;
        }
        return res;
    }
}
