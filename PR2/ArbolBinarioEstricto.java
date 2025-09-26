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

    // --- SECCIÓN DE CREACIÓN DEL ÁRBOL (NUEVA Y MEJORADA) ---

    public boolean crearArbol(String nArchivo) {
        this.raiz = null; // Reiniciar el árbol
        String input;
        try {
            input = leerArchivoCompleto(nArchivo);
            if (input.trim().isEmpty()) return true; // Archivo vacío, árbol vacío
        } catch (IOException e) {
            System.err.println("No se encontró/leyó el archivo: " + e.getMessage());
            return false;
        }

        // 1. Convertir el String en una lista de tokens
        Queue<String> tokens = new LinkedList<>();
        // Usamos una expresión regular para encontrar paréntesis o secuencias de dígitos
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[()]|\\d+");
        java.util.regex.Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        // 2. Construir el árbol a partir de la cola de tokens
        try {
            this.raiz = crearRama(tokens);
            if (!tokens.isEmpty()) {
                // Si sobran tokens, la estructura es incorrecta
                throw new IllegalArgumentException("Tokens extra al final del mapa: " + tokens);
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error de sintaxis al crear el árbol: " + e.getMessage());
            this.raiz = null;
            return false;
        }
    }
    
    // El método recursivo ahora usa una Cola, es mucho más limpio
    private NodoDoble<Parada> crearRama(Queue<String> tokens) {
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Mapa incompleto, se esperaban más datos.");
        }
        String token = tokens.poll(); // Saca el primer token de la cola

        if (token.equals("(")) {
            // Es un nodo interno
            NodoDoble<Parada> izq = crearRama(tokens);
            NodoDoble<Parada> der = crearRama(tokens);

            // El siguiente token DEBE ser un ")"
            if (tokens.isEmpty() || !tokens.poll().equals(")")) {
                throw new IllegalArgumentException("Se esperaba un ')' para cerrar la expresión.");
            }
            
            NodoDoble<Parada> nodo = new NodoDoble<>(null);
            nodo.setIzquierdo(izq);
            nodo.setDerecho(der);
            return nodo;
        } else {
            // Es una hoja (un número)
            try {
                int peso = Integer.parseInt(token);
                return new NodoDoble<>(new Parada(peso));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Token inválido. Se esperaba un número pero se encontró: " + token);
            }
        }
    }

    private String leerArchivoCompleto(String ruta) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String line;
            if ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
        }
        return sb.toString();
    }

    // --- SECCIÓN DE RECORRIDOS (SIN CAMBIOS) ---

    public String recorridoRecursivo() {
        if (estaVacio()) {
            return "Ruta: , Número de calles visitadas: 0, Peso total: 0.0";
        }
        double[] resultados = new double[2]; // [0] = pesoTotal, [1] = callesVisitadas
        String ruta = generarRutaRecursiva(raiz, resultados);
        return "Ruta seguida: " + ruta + ", Número de calles visitadas: " + (int)resultados[1] + ", Peso total: " + resultados[0];
    }

    private String generarRutaRecursiva(NodoDoble<Parada> nodo, double[] resultados) {
        if (nodo.getIzquierdo() == null) { // Es una hoja
            resultados[0] += nodo.getDato().getPeso();
            return "";
        }
        // Es una rama
        String rutaIzquierda, rutaDerecha;
        resultados[1] += 2; // ir y volver de la izquierda
        rutaIzquierda = "i" + generarRutaRecursiva(nodo.getIzquierdo(), resultados) + "r";
        resultados[1] += 2; // ir y volver de la derecha
        rutaDerecha = "d" + generarRutaRecursiva(nodo.getDerecho(), resultados) + "r";
        return rutaIzquierda + rutaDerecha;
    }

    public String recorridoIterativoConPila() {
        if (estaVacio()) {
            return "Ruta: , Número de calles visitadas: 0, Peso total: 0.0";
        }
        double pesoTotal = 0;
        int callesVisitadas = 0;
        StringBuilder ruta = new StringBuilder();
        
        Pila<Object> pila = new Pila<>(); // La pila guardará Nodos o Strings ("r", "i", "d")
        pila.push(raiz);

        while(!pila.isEmpty()){
            Object item = pila.pop();

            if (item instanceof NodoDoble){
                @SuppressWarnings("unchecked")
                NodoDoble<Parada> nodo = (NodoDoble<Parada>) item;

                if(nodo.getIzquierdo() != null){ // Es una rama
                    // Empujamos en orden inverso para procesar en orden correcto
                    pila.push("r"); // de regreso de la derecha
                    pila.push(nodo.getDerecho());
                    pila.push("d");
                    pila.push("r"); // de regreso de la izquierda
                    pila.push(nodo.getIzquierdo());
                    pila.push("i");
                } else { // Es una hoja
                    pesoTotal += nodo.getDato().getPeso();
                }
            } else { // Es un string de ruta
                ruta.append(item.toString());
            }
        }
        // El número de calles es el largo de la ruta generada
        callesVisitadas = ruta.length();
        return "Ruta seguida: " + ruta.toString() + ", Número de calles visitadas: " + callesVisitadas + ", Peso total: " + pesoTotal;
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