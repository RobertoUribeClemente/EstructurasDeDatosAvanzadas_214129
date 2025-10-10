// ComparacionHamburguesas.java
import java.util.concurrent.*;

public class ComparacionHamburguesas {

    // Clase interna para el resultado, ahora incluye las cuentas de cada hamburguesa.
    public static class Resultado {
        public final int totalHamburguesas, cuentaM, cuentaN, tiempoUsado, tiempoSobrante;
        public final boolean tiempoExcedido;

        public Resultado(int cuentaM, int cuentaN, int tiempoUsado, int T) {
            this.cuentaM = cuentaM;
            this.cuentaN = cuentaN;
            this.totalHamburguesas = (cuentaM != -1) ? cuentaM + cuentaN : -1;
            this.tiempoUsado = tiempoUsado;
            this.tiempoSobrante = (tiempoUsado != -1) ? T - tiempoUsado : -1;
            this.tiempoExcedido = false;
        }

        // Constructor para el caso de tiempo límite excedido
        public Resultado() {
            this.totalHamburguesas = -1;
            this.cuentaM = -1;
            this.cuentaN = -1;
            this.tiempoUsado = -1;
            this.tiempoSobrante = -1;
            this.tiempoExcedido = true;
        }
    }

    // --- Variables para el control de tiempo ---
    private long startTime;
    private static final long TIME_LIMIT_NS = 3_000_000_000L; // 3 segundos en nanosegundos

    // --- 1. SOLUCIÓN RECURSIVA PURA ---
    public Resultado solveRecursivoPuro(int n, int m, int T) {
        startTime = System.nanoTime();
        try {
            int[] cuentasExactas = calcularMaxHRecursivo(n, m, T);
            if (cuentasExactas != null) {
                return new Resultado(cuentasExactas[0], cuentasExactas[1], T, T);
            }

            int maxHamb = -1, mejorTiempo = -1;
            int[] mejoresCuentas = null;
            for (int t = T - 1; t >= 0; t--) {
                int[] cuentasActuales = calcularMaxHRecursivo(n, m, t);
                if (cuentasActuales != null) {
                    int hActual = cuentasActuales[0] + cuentasActuales[1];
                    if (hActual > maxHamb) {
                        maxHamb = hActual;
                        mejorTiempo = t;
                        mejoresCuentas = cuentasActuales;
                    }
                }
            }
            return (mejoresCuentas != null) ? new Resultado(mejoresCuentas[0], mejoresCuentas[1], mejorTiempo, T) : new Resultado(-1, -1, -1, T);
        } catch (TimeoutException e) {
            return new Resultado();
        }
    }

    private int[] calcularMaxHRecursivo(int n, int m, int t) throws TimeoutException {
        if (System.nanoTime() - startTime > TIME_LIMIT_NS) throw new TimeoutException();
        if (t < 0) return null;
        if (t == 0) return new int[]{0, 0};

        int[] opcionM = calcularMaxHRecursivo(n, m, t - m);
        int[] opcionN = calcularMaxHRecursivo(n, m, t - n);

        int totalM = (opcionM != null) ? opcionM[0] + opcionM[1] : -1;
        int totalN = (opcionN != null) ? opcionN[0] + opcionN[1] : -1;

        if (totalM == -1 && totalN == -1) return null;
        if (totalM > totalN) return new int[]{opcionM[0] + 1, opcionM[1]};
        return new int[]{opcionN[0], opcionN[1] + 1};
    }

    // --- 2. SOLUCIÓN CON MEMOIZACIÓN ---
    private int[][] memo;

    public Resultado solveConMemoizacion(int n, int m, int T) {
        startTime = System.nanoTime();
        this.memo = new int[T + 1][];
        try {
            calcularMaxHMemo(n, m, T);
            if (memo[T] != null) {
                return new Resultado(memo[T][0], memo[T][1], T, T);
            }
            int maxHamb = -1, mejorTiempo = -1;
            for (int t = T - 1; t >= 0; t--) {
                if (memo[t] != null) {
                    int hActual = memo[t][0] + memo[t][1];
                    if (hActual > maxHamb) {
                        maxHamb = hActual;
                        mejorTiempo = t;
                    }
                }
            }
            return (mejorTiempo != -1) ? new Resultado(memo[mejorTiempo][0], memo[mejorTiempo][1], mejorTiempo, T) : new Resultado(-1, -1, -1, T);
        } catch (TimeoutException e) {
            return new Resultado();
        }
    }

    private int[] calcularMaxHMemo(int n, int m, int t) throws TimeoutException {
        if (System.nanoTime() - startTime > TIME_LIMIT_NS) throw new TimeoutException();
        if (t < 0) return null;
        if (t == 0) return new int[]{0, 0};
        if (memo[t] != null) return memo[t];

        int[] opcionM = calcularMaxHMemo(n, m, t - m);
        int[] opcionN = calcularMaxHMemo(n, m, t - n);
        
        int totalM = (opcionM != null) ? opcionM[0] + opcionM[1] : -1;
        int totalN = (opcionN != null) ? opcionN[0] + opcionN[1] : -1;

        if (totalM == -1 && totalN == -1) return memo[t] = null;
        if (totalM > totalN) return memo[t] = new int[]{opcionM[0] + 1, opcionM[1]};
        return memo[t] = new int[]{opcionN[0], opcionN[1] + 1};
    }

    // --- 3. SOLUCIÓN CON PROGRAMACIÓN DINÁMICA ---
    public Resultado solveDinamico(int n, int m, int T) {
        startTime = System.nanoTime();
        int[][] dp = new int[T + 1][];
        dp[0] = new int[]{0, 0};

        try {
            for (int t = 1; t <= T; t++) {
                if (System.nanoTime() - startTime > TIME_LIMIT_NS) throw new TimeoutException();
                
                int[] opcionM = (t >= m && dp[t - m] != null) ? dp[t - m] : null;
                int[] opcionN = (t >= n && dp[t - n] != null) ? dp[t - n] : null;

                int totalM = (opcionM != null) ? opcionM[0] + opcionM[1] : -1;
                int totalN = (opcionN != null) ? opcionN[0] + opcionN[1] : -1;
                
                if (totalM == -1 && totalN == -1) continue;
                if (totalM > totalN) dp[t] = new int[]{opcionM[0] + 1, opcionM[1]};
                else dp[t] = new int[]{opcionN[0], opcionN[1] + 1};
            }

            if (dp[T] != null) {
                return new Resultado(dp[T][0], dp[T][1], T, T);
            }

            int maxHamb = -1, mejorTiempo = -1;
            for (int t = T - 1; t >= 0; t--) {
                if (dp[t] != null) {
                    int hActual = dp[t][0] + dp[t][1];
                    if (hActual > maxHamb) {
                        maxHamb = hActual;
                        mejorTiempo = t;
                    }
                }
            }
            return (mejorTiempo != -1) ? new Resultado(dp[mejorTiempo][0], dp[mejorTiempo][1], mejorTiempo, T) : new Resultado(-1, -1, -1, T);
        } catch (TimeoutException e) {
            return new Resultado();
        }
    }
}