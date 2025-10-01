public class HamburguesasFuerzaBruta {

    public static void solveBruteForce(int n, int m, int T) {
        int maxBurgers = 0;
        int bestTime = 0;

        // Bucle para la cantidad de hamburguesas tipo A
        for (int countA = 0; countA * n <= T; countA++) {
            
            // Bucle para la cantidad de hamburguesas tipo B
            for (int countB = 0; countA * n + countB * m <= T; countB++) {
                int currentBurgers = countA + countB;
                int currentTime = countA * n + countB * m;

                // Comprobar si encontramos una mejor solución
                if (currentBurgers > maxBurgers) {
                    maxBurgers = currentBurgers;
                    bestTime = currentTime;
                } else if (currentBurgers == maxBurgers) {
                    // Si tenemos el mismo número de hamburguesas,
                    // preferimos la que desperdicia menos tiempo (usa más tiempo)
                    if (currentTime > bestTime) {
                        bestTime = currentTime;
                    }
                }
            }
        }

        System.out.println("--- Solución Iterativa con Fuerza Bruta ---");
        if (maxBurgers > 0) {
            System.out.println("Máximo de hamburguesas: " + maxBurgers);
            System.out.println("Tiempo total usado: " + bestTime + " minutos.");
            System.out.println("Tiempo desperdiciado: " + (T - bestTime) + " minutos.");
        } else {
            System.out.println("No es posible comer ninguna hamburguesa en el tiempo dado.");
        }
    }

    public static void main(String[] args) {
        int n = 4, m = 9, T = 17;
        solveBruteForce(n, m, T);
    }
}