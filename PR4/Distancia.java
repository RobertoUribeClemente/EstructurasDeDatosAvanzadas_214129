import java.io.*;
import java.util.*;


public class Distancia {

    /**
     *Método para leer un archivo de texto completo y devolverlo como un solo String.
    */

    public String leerArchivoComoString(String nombreArchivo) throws FileNotFoundException {

        // Usamos un StringBuilder para construir eficientemente el string completo
        StringBuilder contenido = new StringBuilder();
        try {
            Scanner s = new Scanner(new File(nombreArchivo));
            while (s.hasNextLine()) {
                contenido.append(s.nextLine());

                // Opcional: añade un espacio o salto de línea si la separación es importante
                // contenido.append(" "); 

            }
            s.close();

        } catch (FileNotFoundException ex) {
            System.err.println("Archivo no encontrado: " + nombreArchivo);
            throw ex;
        }
        return contenido.toString();
    }


    /**
     * Wrapper para la función recursiva.
     * Pasa los costos personalizados a la función recursiva.
     */
    public int calculaED(String s1, String s2, int costoGap, int costoSustitucion) {
        int n1 = s1.length();
        int n2 = s2.length();
        return calculaED(s1, n1, s2, n2, costoGap, costoSustitucion);
    }


    //Este es el método recursivo con costos personalizados.
    private int calculaED(String s1, int n1, String s2, int n2, int costoGap, int costoSustitucion) {
        
        // Caso base 1. Si s1 está vacío, el costo es insertar todos los caracteres de s2.
        if (n1 == 0) 
            return n2 * costoGap;
        
        // Caso base 2. Si s2 está vacío, el costo es eliminar todos los caracteres de s1.
        if (n2 == 0) 
            return n1 * costoGap;

        // Costo de sustitución: 0 si los caracteres son iguales, 'costoSustitucion' si son diferentes.
        int subsCost = (s1.charAt(n1 - 1) == s2.charAt(n2 - 1)) ? 0 : costoSustitucion;


        // Op. 1: Eliminar (Delete) - Avanza en s1 pero no en s2
        int delOP = costoGap + calculaED(s1, n1 - 1, s2, n2, costoGap, costoSustitucion);
        
        // Op. 2: Insertar (Insert) - Avanza en s2 pero no en s1
        int insOP = costoGap + calculaED(s1, n1, s2, n2 - 1, costoGap, costoSustitucion);
        
        // Op. 3: Sustituir (Substitute) - Avanza en ambos
        //  La llamada recursiva ahora avanza en ambos strings (n1-1, n2-1)
        int subOP = subsCost + calculaED(s1, n1 - 1, s2, n2 - 1, costoGap, costoSustitucion);

        // El resultado es el mínimo de las tres operaciones
        int result = Math.min(Math.min(delOP, insOP), subOP);

        return result;
    }



    //Función con Programación Dinámica (PD) y costos personalizados.
    public int calculaEDP(String str1, String str2, int costoGap, int costoSustitucion) {
        int n = str1.length();
        int m = str2.length();

        // Crear la tabla DP de (n+1) x (m+1)
        int[][] dp = new int[n + 1][m + 1];
    

        // 1. Inicialización de la primera fila y columna (casos base)

        for (int i = 0; i <= n; i++) 
            // Costo de eliminar i caracteres de str1 (costo_gap * i)
            dp[i][0] = i * costoGap;
        
        for (int j = 0; j <= m; j++) 
            // Costo de insertar j caracteres en str1 (costo_gap * j)
            dp[0][j] = j * costoGap;
    

        // 2. Llenado del resto de la tabla
        for (int i = 1; i <= n; i++) 
            for (int j = 1; j <= m; j++) {
                
                // Costo de sustitución (0 si son iguales, costoSustitucion si no)
                int substitutionCost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : costoSustitucion;

                // Costo de eliminar (viene de la celda de arriba)
                // Se usa costoGap
                int deleteOp = dp[i - 1][j] + costoGap;

                // Costo de insertar (viene de la celda de la izquierda) usando costoGap
                int insertOp = dp[i][j - 1] + costoGap;

                // Costo de sustituir (viene de la celda diagonal)
                int substituteOp = dp[i - 1][j - 1] + substitutionCost;

                // dp[i][j] es el mínimo de las tres opciones
                dp[i][j] = Math.min(deleteOp, Math.min(insertOp, substituteOp));
            }
        

        // 3. El resultado está en la esquina inferior derecha
        return dp[n][m];
    }

    
    public static void main(String[] args) {
        Distancia calculator = new Distancia();
        
        final int COSTO_GAP = 1; 
        
        final int COSTO_SUSTITUCION = 2;
        
        
        System.out.println("\n--- Práctica 4: Lectura de Archivos ---");

        try {
            // Leemos los archivos t1.txt y t2.txt
            String T1 = calculator.leerArchivoComoString("t1.txt");
            String T2 = calculator.leerArchivoComoString("t2.txt");

            System.out.println("Archivo T1 leído (primeros 50 chars): " + (T1.length() > 50 ? T1.substring(0, 50) + "..." : T1));
            System.out.println("Archivo T2 leído (primeros 50 chars): " + (T2.length() > 50 ? T2.substring(0, 50) + "..." : T2));

            // D(T1, T2) - Recursiva (¡CUIDADO! Si T1 y T2 son largos, esto puede tardar horas o fallar)
            // System.out.println("Calculando D(T1, T2) [Recursiva]...");
            // int distR_T1_T2 = calculator.calculaED(T1, T2, COSTO_GAP, COSTO_SUSTITUCION);
            // System.out.println("Distancia Recursiva (T1, T2) = " + distR_T1_T2);

            // D(T2, T1) - PD (Programación Dinámica) (Esta es la forma eficiente)
            System.out.println("\nCalculando D(T2, T1) [Programación Dinámica]...");
            int distPD_T2_T1 = calculator.calculaEDP(T2, T1, COSTO_GAP, COSTO_SUSTITUCION);
            System.out.println("Distancia PD (T2, T1) = " + distPD_T2_T1);
            
            // Nota: La distancia de T1 a T2 es la misma que de T2 a T1
            System.out.println("Calculando D(T1, T2) [Programación Dinámica]...");
            int distPD_T1_T2 = calculator.calculaEDP(T1, T2, COSTO_GAP, COSTO_SUSTITUCION);
            System.out.println("Distancia PD (T1, T2) = " + distPD_T1_T2);


        } catch (FileNotFoundException e) {
            System.err.println("Error: Asegúrate de que los archivos 't1.txt' y 't2.txt' existan en el mismo directorio.");
        }
    }
}


