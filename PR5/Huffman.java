import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase implementa la compresión Huffman.
 * Utiliza la clase 'Cola' (una lista enlazada ordenada) para
 * actuar como una cola de prioridad.
 */
public class Huffman {

    private char[] alfabeto;
    private int[] f, L, R, P;
    private int n;
    private StringBuilder bitBuffer;
    private Map<Character, Integer> mapaCharHoja;

    public Huffman(int[] frecuencia, char[] alfabeto, int tot) {
        this.alfabeto = alfabeto;
        this.n = tot;
        int totalNodes = 2 * n; 
        
        this.f = new int[totalNodes];
        this.L = new int[totalNodes];
        this.R = new int[totalNodes];
        this.P = new int[totalNodes];
        
        if (n > 0) {
            System.arraycopy(frecuencia, 0, this.f, 1, n);
        }

        this.bitBuffer = new StringBuilder();
        this.mapaCharHoja = new HashMap<>();

        for (int i = 1; i <= n; i++) {
            mapaCharHoja.put(alfabeto[i - 1], i);
        }
    }

    private void buildHuffman() {
        if (n <= 1) {
            return; 
        }
        
        Cola prioridad = new Cola();

        for (int i = 1; i <= n; i++) {
            L[i] = 0;
            R[i] = 0;
            prioridad.insert(i, f[i]);
        }
        
        for (int i = n + 1; i < 2 * n; i++) {
            int x = prioridad.extractMin();
            int y = prioridad.extractMin(); 
            
            f[i] = f[x] + f[y];
            prioridad.insert(i, f[i]); 
            
            L[i] = x;
            R[i] = y;
            P[x] = i;
            P[y] = i;
        }

        P[2 * n - 1] = 0;
    }

    public String huffmanEncode(String A) {
        if (n == 0) {
            return "";
        }
        buildHuffman(); // Construye el árbol
        
        this.bitBuffer.setLength(0);
        int k = A.length();
        
        for (int i = 0; i < k; i++) {
            Integer x = mapaCharHoja.get(A.charAt(i));
            if (x == null) {
                throw new IllegalArgumentException("El caracter '" + A.charAt(i) + "' no está en el alfabeto.");
            }
            huffmanEncodeOne(x);
        }
        return bitBuffer.toString();
    }

    private void huffmanEncodeOne(int x) {

        if (n == 1 && P[x] == 0) {
             this.bitBuffer.append('0');
             return;
        }
        // Caso general n > 1
        if (P[x] != 0) { 
            huffmanEncodeOne(P[x]);
            
            int padre = P[x];
            if (x == L[padre]) { 
                this.bitBuffer.append('0');
            } else { 
                this.bitBuffer.append('1');
            }
        }
    }
    
    public String huffmanDecode(String encodedString) {
        StringBuilder decodedText = new StringBuilder();
        if (n == 0) return "";

        int k = encodedString.length();
        // La raíz es 1 si n=1, o 2n-1 si n>1
        int root;
        if(n==1){
            root=1;
        }
        else{
            root=2*n+1;
        }

        // Caso n=1
        if (n == 1) {
             for(int i=0; i < k; i++) {
                 if (encodedString.charAt(i) == '0') {
                    decodedText.append(alfabeto[0]); 
                 }
             }
             return decodedText.toString();
        }

        // Caso n > 1
        int currentNode = root;
        for (int i = 0; i < k; i++) {
            char bit = encodedString.charAt(i);

            if(bit == '0'){
                currentNode=L[currentNode];
            }
            else{
                currentNode= R[currentNode];
            }

            // Si es una hoja (L[nodo] == 0)
            if (L[currentNode] == 0) {
                decodedText.append(alfabeto[currentNode - 1]); 
                currentNode = root; // Volver a la raíz
            }
        }
        
        if (currentNode != root) {
             System.err.println("Advertencia: La cadena de bits de entrada parece estar incompleta.");
        }
        return decodedText.toString();
    }
    
    public String getCodeForChar(char c) {
        Integer x = mapaCharHoja.get(c);
        if (x == null) return "NO_ENCONTRADO";
        
        this.bitBuffer.setLength(0); 
        huffmanEncodeOne(x);
        return this.bitBuffer.toString();
    }
}