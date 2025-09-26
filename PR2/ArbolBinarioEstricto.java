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

    // --- SECCIÓN DE PARSEO Y CREACIÓN DEL ÁRBOL ---

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

        if (!validarParentesis(input)) {
            System.err.println("Error: Paréntesis desbalanceados en el mapa.");
            return false;
        }

        try (Scanner sc = new Scanner(input)) {
            // Este delimitador es clave para separar números y paréntesis correctamente
            sc.useDelimiter("\\s+|(?=[()])|(?<=[()])");
            this.raiz = crearRama(sc);
            return true;
        } catch (Exception e) {
            System.err.println("Error de sintaxis al crear el árbol: " + e.getMessage());
            this.raiz = null; // Asegurarse que el árbol quede vacío si falla
            return false;
        }
    }

    private String leerArchivoCompleto(String ruta) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
        }
        return sb.toString();
    }

    private boolean validarParentesis(String texto) {
        int balance = 0;
        for (char c : texto.toCharArray()) {
            if (c == '(') balance++;
            else if (c == ')') balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }

    /**
     * VERSIÓN CORREGIDA Y ÚNICA DEL PARSER RECURSIVO.
     * Esta es la versión que funciona.
     */
    private NodoDoble<Parada> crearRama(Scanner sc) {
        if (!sc.hasNext()) {
            throw new IllegalStateException("Error: Se alcanzó el fin del mapa inesperadamente.");
        }
        String token = sc.next();

        if (token.equals("(")) {
            // Es un nodo interno, debe tener dos hijos
            NodoDoble<Parada> izq = crearRama(sc);
            NodoDoble<Parada> der = crearRama(sc);
            
            // Después de los dos hijos, debe venir un ')'
            if (!sc.hasNext(")")) {
                throw new IllegalStateException("Error de sintaxis: se esperaba ')' después de los dos hijos.");
            }
            sc.next(); // Consumir el ')'
            
            NodoDoble<Parada> nodo = new NodoDoble<>(null);
            nodo.setIzquierdo(izq);
            nodo.setDerecho(der);
            return nodo;
            
        } else if (token.equals(")")) {
            throw new IllegalStateException("Error de sintaxis: ')' inesperado.");
        } else {
            // No es '(', debe ser un número (una hoja)
            try {
                int peso = Integer.parseInt(token);
                return new NodoDoble<>(new Parada(peso));
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Token inesperado: '" + token + "'. Se esperaba un número o '('.");
            }
        }
    }

    // --- SECCIÓN DE RECORRIDOS ---

    public String recorridoRecursivo() {
        if (estaVacio()) {
            return "Ruta: , Número de calles visitadas: 0, Peso total: 0.0";
        }
        double[] resultados = new double[2]; // [0] = pesoTotal, [1] = callesVisitadas
        String ruta = generarRutaRecursiva(raiz, resultados);
        return "Ruta seguida: " + ruta + ", Número de calles visitadas: " + (int)resultados[1] + ", Peso total: " + resultados[0];
    }

    private String generarRutaRecursiva(NodoDoble<Parada> nodo, double[] resultados) {
        if (nodo.getIzquierdo() == null) {
            resultados[0] += nodo.getDato().getPeso();
            return "";
        }
        String rutaIzquierda, rutaDerecha;
        resultados[1] += 2;
        rutaIzquierda = "i" + generarRutaRecursiva(nodo.getIzquierdo(), resultados) + "r";
        resultados[1] += 2;
        rutaDerecha = "d" + generarRutaRecursiva(nodo.getDerecho(), resultados) + "r";
        return rutaIzquierda + rutaDerecha;
    }

    public String recorridoIterativoConPila() {
        if (estaVacio()) {
            return "Ruta: , Número de calles visitadas: 0, Peso total: 0.0";
        }
        double pesoTotal = 0;
        int callesVisitadas = 0;

        Pila<NodoDoble<Parada>> pila = new Pila<>();
        pila.push(raiz);
        while (!pila.isEmpty()) {
            NodoDoble<Parada> nodo = pila.pop();
            if (nodo.getIzquierdo() != null) { // Es un nodo interno
                callesVisitadas += 4; // 2 de ida, 2 de vuelta
                pila.push(nodo.getDerecho());
                pila.push(nodo.getIzquierdo());
            } else { // Es una hoja
                pesoTotal += nodo.getDato().getPeso();
            }
        }
        return "Ruta seguida: (Recorrido Pre-Orden Iterativo), Número de calles visitadas: " + callesVisitadas + ", Peso total: " + pesoTotal;
    }
    // Si no se regresara a la raiz:

    /*    private int cuentaPasos(NodoDoble<Parada> inicio){

        int peso=0;

        if(inicio!=null){

            if(inicio.getIzquierdo()!=null){ // rama

                peso++;

                peso+=cuentaPasos(inicio.getDerecho())+cuentaPasos(inicio.getIzquierdo());



            }

            else{

                return 0;

            }



        }

        return peso;

    }

    public String moverOptimo(){

        String res="";

        if(!estaVacio()){

            if(cuentaPasos(raiz.getIzquierdo())<cuentaPasos(raiz.getDerecho())){

                res+="i";

                res+=moverOptimo(raiz.getIzquierdo());

                res+="d";

                res+=moverOptimo(raiz.getDerecho());

            }

            else{

                res+="d";

                res+=moverOptimo(raiz.getDerecho());

                res+="i";

                res+=moverOptimo(raiz.getIzquierdo());

            }

        }

        return res;

    }

    public String moverOptimo(NodoDoble<Parada> inicio){

        String res="";

        if(!estaVacio()){

            if(inicio.getDerecho()!=null){ // si es hoja no se mueve mas y regresa con nada

                if(cuentaPasos(inicio.getIzquierdo())<cuentaPasos(inicio.getDerecho())){

                    res+="i";

                    res+=moverOptimo(inicio.getIzquierdo());

                    res+="d";

                    res+=moverOptimo(inicio.getDerecho());

                    res+="r";

                }

                else{

                    res+="d";

                    res+=moverOptimo(inicio.getDerecho());

                    res+="i";   

                    res+=moverOptimo(inicio.getIzquierdo());

                    res+="r";

                }

            }

        }

    

    }

     * 

     */


}