import java.io.*;
import java.util.*;


public class Distancia {


    /**
     * Método para leer un archivo de texto completo y devolverlo como un solo String.
     */
    public String leerArchivoComoString(String nombreArchivo) throws FileNotFoundException {
        StringBuilder contenido = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(nombreArchivo));
            while (scanner.hasNextLine()) {
                contenido.append(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Archivo no encontrado: " + nombreArchivo);
            throw ex;
        }
        return contenido.toString();
    }

    // --- 1. Versión Recursiva Pura (Sin Memoización) --- [cite: 20]
    public int calculaED(String s1, String s2, int costoGap, int costoSustitucion) {
        return calculaEDR(s1, s1.length(), s2, s2.length(), costoGap, costoSustitucion);
    }

    private int calculaEDR(String s1, int n1, String s2, int n2, int costoGap, int costoSustitucion) {
        if (n1 == 0) return n2 * costoGap;
        if (n2 == 0) return n1 * costoGap;

        int subsCost = (s1.charAt(n1 - 1) == s2.charAt(n2 - 1)) ? 0 : costoSustitucion;

        int delOP = costoGap + calculaEDR(s1, n1 - 1, s2, n2, costoGap, costoSustitucion);
        int insOP = costoGap + calculaEDR(s1, n1, s2, n2 - 1, costoGap, costoSustitucion);
        int subOP = subsCost + calculaEDR(s1, n1 - 1, s2, n2 - 1, costoGap, costoSustitucion);

        return Math.min(Math.min(delOP, insOP), subOP);
    }

    // --- 2. Versión Recursiva con Memoización --- 
    public int calculaEDMemo(String s1, String s2, int costoGap, int costoSustitucion) {
        int n1 = s1.length();
        int n2 = s2.length();
        // La tabla "memo" guarda los resultados. Se inicializa con null.
        Integer[][] memo = new Integer[n1 + 1][n2 + 1];
        return calculaEDMemoR(s1, n1, s2, n2, costoGap, costoSustitucion, memo);
    }

    private int calculaEDMemoR(String s1, int n1, String s2, int n2, int costoGap, int costoSustitucion, Integer[][] memo) {
        if (n1 == 0) return n2 * costoGap;
        if (n2 == 0) return n1 * costoGap;

        // 1. Si ya lo calculamos, lo devolvemos
        if (memo[n1][n2] != null) {
            return memo[n1][n2];
        }

        int subsCost = (s1.charAt(n1 - 1) == s2.charAt(n2 - 1)) ? 0 : costoSustitucion;

        int delOP = costoGap + calculaEDMemoR(s1, n1 - 1, s2, n2, costoGap, costoSustitucion, memo);
        int insOP = costoGap + calculaEDMemoR(s1, n1, s2, n2 - 1, costoGap, costoSustitucion, memo);
        int subOP = subsCost + calculaEDMemoR(s1, n1 - 1, s2, n2 - 1, costoGap, costoSustitucion, memo);

        // 2. Guardamos el resultado en la tabla antes de devolverlo
        int result = Math.min(Math.min(delOP, insOP), subOP);
        memo[n1][n2] = result;
        return result;
    }

    // --- 3. Versión Iterativa con Programación Dinámica (PD) --- [cite: 22]
    public int calculaEDP(String str1, String str2, int costoGap, int costoSustitucion) {
        int n = str1.length();
        int m = str2.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) dp[i][0] = i * costoGap;
        for (int j = 0; j <= m; j++) dp[0][j] = j * costoGap;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int substitutionCost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : costoSustitucion;
                int deleteOp = dp[i - 1][j] + costoGap;
                int insertOp = dp[i][j - 1] + costoGap;
                int substituteOp = dp[i - 1][j - 1] + substitutionCost;
                dp[i][j] = Math.min(deleteOp, Math.min(insertOp, substituteOp));
            }
        }
        return dp[n][m];
    }
}

    




