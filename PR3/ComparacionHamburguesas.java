
import java.util.*;

public class ComparacionHamburguesas {

    // --- 1. SOLUCIÓN RECURSIVA PURA ---
    private int n_rec, m_rec;
    private int[] memo;

    /**
     * @return un arreglo int[] donde: [0] = hamburguesas, [1] = tiempo usado.
     * Retorna [-1, -1] si no hay solución.
     */
    public int[] solveRecursivoPuro(int n, int m, int T) {
        this.n_rec = n;
        this.m_rec = m;

        int hExactas = calcularMaxHRecursivo(T);
        if (hExactas != -1) 
            return new int[]{hExactas, T};
        

        int maxHamb = -1, mejorTiempo = -1;
        for (int t = T - 1; t >= 0; t--) {
            int hActual = calcularMaxHRecursivo(t);
            if (hActual > maxHamb) {
                maxHamb = hActual;
                mejorTiempo = t;
            }
        }
        return new int[]{maxHamb, mejorTiempo};
    }

    private int calcularMaxHRecursivo(int t) {
        if (t < 0) return -1;
        if (t == 0) return 0;
        int opcionN = calcularMaxHRecursivo(t - n_rec);
        int opcionM = calcularMaxHRecursivo(t - m_rec);
        if (opcionN == -1 && opcionM == -1) 
            return -1;
        return 1 + Math.max(opcionN, opcionM);
    }
    







      // --- 2. SOLUCIÓN CON MEMOIZACIÓN  ---


    public int[] solveConMemoizacion(int n, int m, int T) {
        this.memo = new int[T + 1];
        Arrays.fill(memo, -2); // -2 significa "no calculado"
        calcularMaxHMemo(n, m, T);

        if (memo[T] != -1) 
            return new int[]{memo[T], T};
    

        int maxHamb = -1, mejorTiempo = -1;
        for (int t = T - 1; t >= 0; t--) {
            if (memo[t] > maxHamb) {
                maxHamb = memo[t];
                mejorTiempo = t;
            }
        }
    
        return new int[]{maxHamb, mejorTiempo};
    }

    /**
    * Método recursivo con memoización.
    */
    private int calcularMaxHMemo(int n, int m, int t) {
        // --- Casos Base ---
        if (t < 0) 
            return -1;
        if (t == 0) 
            return 0;

        // --- Revisión del Memo ---
        if (memo[t] != -2) 
            return memo[t];
        

        // --- Lógica Recursiva ---
        int opcionN = calcularMaxHMemo(n, m, t - n);
        int opcionM = calcularMaxHMemo(n, m, t - m);

        // --- Cálculo del Resultado (Lógica Simplificada) ---
        // Si ambas opciones son imposibles, el resultado es imposible.

        if (opcionN == -1 && opcionM == -1) 
        memo[t] = -1;
            else 
            // De lo contrario, es 1 más el máximo de las opciones válidas.
            memo[t] = 1 + Math.max(opcionN, opcionM);
        
    
    // Devolvemos el valor recién guardado en el memo.
        return memo[t];
    }






    // --- 3. SOLUCIÓN CON PROGRAMACIÓN DINÁMICA ---
    public int[] solveDinamico(int n, int m, int T) {
        int[] dp = new int[T + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int t = 1; t <= T; t++) {
            int opcionN = (t >= n && dp[t - n] != -1) ? dp[t - n] + 1 : -1;
            int opcionM = (t >= m && dp[t - m] != -1) ? dp[t - m] + 1 : -1;
            dp[t] = Math.max(opcionN, opcionM);
        }
        
        if (dp[T] != -1) {
            return new int[]{dp[T], T};
        }

        int maxHamb = -1, mejorTiempo = -1;
        for (int t = T - 1; t >= 0; t--) 
            if (dp[t] > maxHamb) {
                maxHamb = dp[t];
                mejorTiempo = t;
            }
        
        return new int[]{maxHamb, mejorTiempo};
    }
}