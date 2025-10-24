
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Distancia {
     public boolean agregarPacientes(String name) {
        boolean res = false;
        try {
            FileReader file = new FileReader(name);
            Scanner lec = new Scanner(file);

            if (lec.hasNext()) {
                
            }
            lec.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Archivo no encontrado: "+name);
        }
        return res;
    }
    public int calculaED(String s1, String s2){
        int n1=s1.length();
        int n2=s2.length();
        return calculaEDR(s1, n1, s2, n2);
    }
    public int calculaEDR(String s1, int n1, String s2, int n2){
        if(n1==0||n2==0){
            return -1;
        }
        int subsCost=(s1.charAt(n1-1))==s2.charAt(n2-1)? 0:1;
        int delOP=1+calculaEDR(s1,n1-1, s2, n2);
        int insOP=1+calculaEDR(s1,n1, s2, n2-1);
        int subOP=calculaEDR(s1,n1, s2, n2-1);
        int result= Math.min(Math.min(delOP, insOP), subOP);

        return result;
    }
    public int calculaEDP(String s1, String s2){
        int n1=s1.length();
        int n2=s2.length();
        return calculaEDR(s1, n1, s2, n2);
    }
    public int calculaEDP2(String s1, int n1, String s2, int n2){
        int[][] db= new int[n1+1][n2+1];
        for(int i=0; i<=n1; i++){
            db[i]
        }
    }
}
