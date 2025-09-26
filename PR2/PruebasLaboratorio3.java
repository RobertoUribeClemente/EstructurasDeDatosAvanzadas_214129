import java.io.*;


public class PruebasLaboratorio3 {

    public static void main(String[] args) {
        String archivoMapas = "PR2/mapas.txt"; // Asegúrate que la ruta sea correcta
        String archivoResultados = "resultados_laboratorio3.csv";
        String archivoTemporal = "temp_mapa.txt";

        System.out.println("Iniciando pruebas de rendimiento para recorridos de árbol...");

        try (FileWriter csvWriter = new FileWriter(archivoResultados)) {
            // Escribir la cabecera del archivo CSV
            csvWriter.write("Dia,Algoritmo,Tiempo(ns),Resultado\n");

            // Leer cada mapa del archivo de entrada
            try (BufferedReader br = new BufferedReader(new FileReader(archivoMapas))) {
                String mapa;
                int dia = 1;
                while ((mapa = br.readLine()) != null) {
                    if (mapa.trim().isEmpty()) continue;

                    System.out.println("--- Procesando Mapa del Día " + dia + " ---");
                    
                    // Crear un archivo temporal para el mapa actual
                    crearArchivoTemporal(archivoTemporal, mapa);

                    // --- Prueba del Algoritmo Recursivo ---
                    ArbolBinarioEstricto arbolRec = new ArbolBinarioEstricto();
                    if (arbolRec.crearArbol(archivoTemporal)) {
                        long inicioRec = System.nanoTime();
                        String resultadoRec = arbolRec.recorridoRecursivo();
                        long finRec = System.nanoTime();
                        long tiempoRec = finRec - inicioRec;
                        
                        System.out.println("[Recursivo] " + resultadoRec);
                        csvWriter.write(dia + ",Recursivo," + tiempoRec + ",\"" + resultadoRec + "\"\n");
                    } else {
                        System.out.println("[Recursivo] Falló la creación del árbol.");
                    }


                    // --- Prueba del Algoritmo Iterativo con Pila ---
                    ArbolBinarioEstricto arbolIte = new ArbolBinarioEstricto();
                    if (arbolIte.crearArbol(archivoTemporal)) {
                        long inicioIte = System.nanoTime();
                        String resultadoIte = arbolIte.recorridoIterativoConPila();
                        long finIte = System.nanoTime();
                        long tiempoIte = finIte - inicioIte;
                        
                        System.out.println("[Iterativo] " + resultadoIte);
                        csvWriter.write(dia + ",Iterativo," + tiempoIte + ",\"" + resultadoIte + "\"\n");
                    } else {
                        System.out.println("[Iterativo] Falló la creación del árbol.");
                    }
                    
                    System.out.println(); // Espacio entre días
                    dia++;
                }
            }
            System.out.println("Pruebas completadas. Los resultados se han guardado en: " + archivoResultados);

        } catch (IOException e) {
            System.err.println("Ocurrió un error al procesar los archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método auxiliar para crear un archivo temporal con el contenido de un mapa.
     */
    private static void crearArchivoTemporal(String nombreArchivo, String contenido) throws IOException {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(contenido);
        }
    }
}