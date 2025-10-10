// ComparacionHamburguesas.java
import java.util.Arrays;

public class ComparacionHamburguesas {

    /**
     * Clase interna para encapsular el resultado de una forma estructurada.
     */
    public static class Resultado {
        public final int hamburguesas;
        public final int tiempoUsado;
        public final int tiempoSobrante;

        public Resultado(int hamburguesas, int tiempoUsado, int tiempoTotal) {
            this.hamburguesas = hamburguesas;
            this.tiempoUsado = tiempoUsado;
            this.tiempoSobrante = (tiempoUsado != -1) ? tiempoTotal - tiempoUsado : -1;
        }

        @Override
        public String toString() {
            if (hamburguesas == -1) return "No se encontró solución.";
            return String.format("%d hamburguesas en %d min. (Sobrante: %d)", hamburguesas, tiempoUsado, tiempoSobrante);
        }
    }

    // --- 1. SOLUCIÓN RECURSIVA PURA ---
    private int n_rec, m_rec;

    public Resultado solveRecursivoPuro(int n, int m, int T) {
        this.n_rec = n;
        this.m_rec = m;

        int hExactas = calcularMaxHRecursivo(T);
        if (hExactas != -1) {
            return new Resultado(hExactas, T, T);
        }

        int maxHamb = -1, mejorTiempo = -1;
        for (int t = T - 1; t >= 0; t--) {
            int hActual = calcularMaxHRecursivo(t);
            if (hActual > maxHamb) {
                maxHamb = hActual;
                mejorTiempo = t;
            }
        }
        return new Resultado(maxHamb, mejorTiempo, T);
    }

    private int calcularMaxHRecursivo(int t) {
        if (t < 0) return -1;
        if (t == 0) return 0;
        int opcionN = calcularMaxHRecursivo(t - n_rec);
        int opcionM = calcularMaxHRecursivo(t - m_rec);
        if (opcionN == -1 && opcionM == -1) return -1;
        return 1 + Math.max(opcionN, opcionM);
    }
    
    // --- 2. SOLUCIÓN CON MEMOIZACIÓN ---
    private int[] memo;

    public Resultado solveConMemoizacion(int n, int m, int T) {
        this.memo = new int[T + 1];
        Arrays.fill(memo, -2);

        calcularMaxHMemo(n, m, T);

        if (memo[T] != -1) {
            return new Resultado(memo[T], T, T);
        }

        int maxHamb = -1, mejorTiempo = -1;
        for (int t = T - 1; t >= 0; t--) {
            if (memo[t] > maxHamb) {
                maxHamb = memo[t];
                mejorTiempo = t;
            }
        }
        return new Resultado(maxHamb, mejorTiempo, T);
    }

    private int calcularMaxHMemo(int n, int m, int t) {
        if (t < 0) return -1;
        if (t == 0) return 0;
        if (memo[t] != -2) return memo[t];

        int opcionN = calcularMaxHMemo(n, m, t - n);
        int opcionM = calcularMaxHMemo(n, m, t - m);

        return memo[t] = (opcionN == -1 && opcionM == -1) ? -1 : 1 + Math.max(opcionN, opcionM);
    }

    // --- 3. SOLUCIÓN CON PROGRAMACIÓN DINÁMICA ---
    public Resultado solveDinamico(int n, int m, int T) {
        int[] dp = new int[T + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int t = 1; t <= T; t++) {
            int opcionN = (t >= n && dp[t - n] != -1) ? dp[t - n] + 1 : -1;
            int opcionM = (t >= m && dp[t - m] != -1) ? dp[t - m] + 1 : -1;
            dp[t] = Math.max(opcionN, opcionM);
        }
        
        if (dp[T] != -1) {
            return new Resultado(dp[T], T, T);
        }

        int maxHamb = -1, mejorTiempo = -1;
        for (int t = T - 1; t >= 0; t--) {
            if (dp[t] > maxHamb) {
                maxHamb = dp[t];
                mejorTiempo = t;
            }
        }
        return new Resultado(maxHamb, mejorTiempo, T);
    }
}