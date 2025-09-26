import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PruebasLaboratorio3 {

    public static void main(String[] args) {
        String archivoMapas = "mapas.txt";
        String archivoResultados = "resultados_laboratorio3.csv";

        System.out.println("Iniciando pruebas de rendimiento para recorridos de árbol...");

        try (FileWriter csvWriter = new FileWriter(archivoResultados)) {
            // Escribir la cabecera del archivo CSV
            csvWriter.write("Dia,Algoritmo,Tiempo(ns),Resultado\n");

            // Leer cada mapa del archivo de entrada
            try (BufferedReader br = new BufferedReader(new FileReader(archivoMapas))) {
                String mapa;
                int dia = 1; // Para contar la línea/día
                while ((mapa = br.readLine()) != null) {
                    if (mapa.trim().isEmpty()) continue;

                    System.out.println("--- Procesando Mapa del Día " + dia + " ---");
                    
                    // --- Prueba del Algoritmo Recursivo ---
                    ArbolBinarioEstricto arbolRec = new ArbolBinarioEstricto();
                    
                    // Creamos el árbol a partir del String del mapa
                    // Nota: Tu método crearArbol necesita un archivo. 
                    // Para simplificar, crearemos un archivo temporal para cada mapa.
                    crearArchivoTemporal("temp_mapa.txt", mapa);
                    arbolRec.crearArbol("temp_mapa.txt");
                    
                    long inicioRec = System.nanoTime();
                    String resultadoRec = arbolRec.recorridoRecursivo();
                    long finRec = System.nanoTime();
                    long tiempoRec = finRec - inicioRec;
                    
                    System.out.println("[Recursivo] " + resultadoRec);
                    csvWriter.write(dia + ",Recursivo," + tiempoRec + ",\"" + resultadoRec + "\"\n");

                    // --- Prueba del Algoritmo Iterativo con Pila ---
                    ArbolBinarioEstricto arbolIte = new ArbolBinarioEstricto();
                    
                    // Reutilizamos el archivo temporal
                    arbolIte.crearArbol("temp_mapa.txt");
                    
                    long inicioIte = System.nanoTime();
                    String resultadoIte = arbolIte.recorridoIterativoConPila();
                    long finIte = System.nanoTime();
                    long tiempoIte = finIte - inicioIte;
                    
                    System.out.println("[Iterativo] " + resultadoIte);
                    csvWriter.write(dia + ",Iterativo," + tiempoIte + ",\"" + resultadoIte + "\"\n");
                    
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
     * Esto es necesario porque tu método crearArbol lee desde un archivo.
     */
    private static void crearArchivoTemporal(String nombreArchivo, String contenido) throws IOException {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(contenido);
        }
    }
}