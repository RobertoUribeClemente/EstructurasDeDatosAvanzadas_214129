
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TablaHash {
    private ArrayList<ListaDoble> tabla;

    public TablaHash() {
        tabla = new ArrayList<>();
    }

    public boolean agregarPaciente(String nombre) {

        try {
            FileReader file= new FileReader(nombre);
            Scanner lec = new Scanner(file);

            if(lec.hasNextInt()){
                int n= lec.nextInt();

                for(int i=0; i<n; i++){
                    int suma=0;
                    String datos="";

                    for(int j=0; j<10; j++){
                        datos=lec.nextLine();
                        suma+=lec.nextInt();
                    }

                    Paciente p= new Paciente(datos);
                    int k =buscarIndice(suma);

                    if(k!=-1){
                        tabla.get(k).addLast(p);
                    }
                    else{
                        ListaDoble nueva= new ListaDoble(suma);
                        tabla.add(nueva);
                    }
                }
            }

        } 
        catch (FileNotFoundException ex) {
        
        }

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
}
