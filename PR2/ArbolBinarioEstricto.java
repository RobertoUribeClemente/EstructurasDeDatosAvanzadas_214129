import java.io.*;
import java.util.*;

public class ArbolBinarioEstricto {
    private NodoDoble<Parada> raiz;

    public ArbolBinarioEstricto() {
        this.raiz = null;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public boolean crearArbol(String nArchivo) {
        if (!this.estaVacio()) {
            return false;
        }

        String input;
        try {
            input = leerArchivoCompleto(nArchivo);
        } catch (IOException e) {
            System.err.println("No se encontró/leyó el archivo: " + e.getMessage());
            return false;
        }

        // Validar paréntesis del mismo texto que vamos a parsear
        if (!validar(input)) {
            System.err.println("Paréntesis desbalanceados o en orden inválido.");
            return false;
        }

        try (Scanner sc = new Scanner(input)) {
            sc.useDelimiter("\\s*|(?=[()])|(?<=[()])");
            NodoDoble<Parada> arbol = crearRama(sc);

            // Verificar que no queden tokens basura 
            if (sc.hasNext()) {
                String tok = sc.next();
                throw new IllegalArgumentException("Token basura al final: " + tok);
            }

            this.raiz = arbol;
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("Error de sintaxis: " + e.getMessage());
            return false;
        }
    }

    public boolean validar(String texto) {
        int balance = 0;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
                if (balance < 0) return false; // cierre antes de apertura
            }
        }
        return balance == 0;
    }

    private NodoDoble<Parada> crearRama(Scanner sc) {
        if (!sc.hasNext()) {
            throw new IllegalArgumentException("EOF inesperado: se esperaba número o '('");
        }
        // 2) Caso hoja: número
        if (sc.hasNextInt()) {
            int valor = sc.nextInt();
            return new NodoDoble<>(new Parada(valor));
        }
        // 3) Caso rama: '('
        if (sc.hasNext("\\(")) {
            sc.next(); 
            // Subárbol izquierdo
            if (!sc.hasNext()) {
                throw new IllegalArgumentException("EOF inesperado: se esperaba subárbol izquierdo");
            }
            NodoDoble<Parada> izq = crearRama(sc);
            // Subárbol derecho
            if (!sc.hasNext()) {
                throw new IllegalArgumentException("EOF inesperado: se esperaba subárbol derecho");
            }
            NodoDoble<Parada> der = crearRama(sc);

            // Cierre ')'
            if (!sc.hasNext()) {
                throw new IllegalArgumentException("EOF inesperado: se esperaba ')'");
            }
            if (!sc.hasNext("\\)")) {
                String tok = sc.next();
                throw new IllegalArgumentException("Token inesperado: " + tok + " (se esperaba ')')");
            }
            sc.next(); 

            NodoDoble<Parada> nodo = new NodoDoble<>(null);
            nodo.setIzquierdo(izq);
            nodo.setDerecho(der);
            return nodo;
        }
        // 4) Token inválido
        String tok = sc.next();
        throw new IllegalArgumentException("Token inesperado: " + tok + " (se esperaba número o '(')");
    }

    private String leerArchivoCompleto(String ruta) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (!first) sb.append('\n');
                sb.append(line);
                first = false;
            }
        }
        return sb.toString();
    }
}
