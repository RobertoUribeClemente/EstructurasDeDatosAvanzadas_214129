
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

public class EditDistanceTabulation {

    public int calculateEditDistance(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();

        // Crear la tabla DP de (n+1) x (m+1)
        int[][] dp = new int[n + 1][m + 1];

        // 1. Inicialización de la primera fila y columna
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i; // Costo de eliminar i caracteres de str1 para llegar a ""
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j; // Costo de insertar j caracteres para llegar a str2 desde ""
        }

        // 2. Llenado del resto de la tabla
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // Costo de sustitución (0 si son iguales, 1 si no)
                int substitutionCost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;

                // Costo de eliminar (viene de la celda de arriba)
                int deleteOp = dp[i - 1][j] + 1;
                
                // Costo de insertar (viene de la celda de la izquierda)
                int insertOp = dp[i][j - 1] + 1;
                
                // Costo de sustituir (viene de la celda diagonal)
                int substituteOp = dp[i - 1][j - 1] + substitutionCost;

                // dp[i][j] es el mínimo de las tres opciones
                dp[i][j] = Math.min(deleteOp, Math.min(insertOp, substituteOp));
            }
        }

        // 3. El resultado está en la esquina inferior derecha
        return dp[n][m];
    }

    public static void main(String[] args) {
        EditDistanceTabulation calculator = new EditDistanceTabulation();
        String palabra1 = "INTENTION";
        String palabra2 = "EXECUTION";
        System.out.println("La distancia (Tabulación) es: " + calculator.calculateEditDistance(palabra1, palabra2)); // Salida: 8
    }
}

}
