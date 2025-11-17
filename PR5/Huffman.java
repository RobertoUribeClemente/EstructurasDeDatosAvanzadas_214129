
import java.util.HashMap;
import java.util.Map;

public class Huffman {

    private char[] alfabeto;
    private int[] f, L, R, P;
    private int n, m;
    private StringBuilder bitBuffer;
    private Map<Character, Integer> mapaCharHoja;


    public Huffman(int[]frecuencia, char[]alfabeto, int tot){
        this.alfabeto=alfabeto;
        this.n=tot;
        int totalNodes = 2 * n; 
        this.f = new int[totalNodes]; // frecuencia del nodo i
        System.arraycopy(frecuencia, 0, this.f, 0, frecuencia.length);
        this.L = new int[totalNodes]; // hijo izquierdo del nodo i
        this.R = new int[totalNodes]; // hijo derecho del nodo i
        this.P = new int[totalNodes]; // padre del nodo i  
        this.m=1;
        this.bitBuffer = new StringBuilder();
        this.mapaCharHoja = new HashMap<>();

        for (int i = 0; i <= n; i++) {
            mapaCharHoja.put(alfabeto[i], i);
        }
    }

    private void buildHuffman(){
        Cola prioridad= new Cola();

        for(int i=0; i<n; i++){
            L[i]=0;
            R[i]=0;
            prioridad.insert(i, f[i]);
        }
        for (int i=n+1; i<2*n+1; i++) {
            int x=prioridad.extractMin();
            int y=prioridad.extractMin();
            f[i]=f[x]+f[y];
            prioridad.insert(i, f[i]);
            L[i]=x;
            R[i]=y;
            P[x]=i;
            P[y]=i;
        }
        P[2*n-1]=0;
    }

    public String huffmanEncode(String A){
        buildHuffman();
        this.bitBuffer.setLength(0);
        int k=A.length();
        for (int i = 1; i < k; i++) {
            Integer x= mapaCharHoja.get(A.charAt(i)); //uso Integer por si es null (no esta en el mapa)
            if (x == null) {
                throw new IllegalArgumentException("El caracter '" + A.charAt(i) + "' no está en el alfabeto.");
            }
            huffmanEncodeOne(x);
        }
        return bitBuffer.toString();
    }
    private void huffmanEncodeOne(int x){
        if (P[x] != 0) { 

            huffmanEncodeOne(P[x]);
            int padre = P[x];
            // Comprobar si 'x' es el hijo IZQUIERDO de su padre
            if (x == L[padre]) {
                this.bitBuffer.append('0');
            } 
            // 3. Si no, 'x' es el hijo DERECHO
            else {
                this.bitBuffer.append('1');
            }
        }
    }
    public String huffmanDecode(String encodedString) {
        StringBuilder decodedText = new StringBuilder();
        int k = encodedString.length();

        int root = 2 * n - 1;
        
        if (root < 1) {
            return ""; 
        }

        int currentNode = root;

        // Recorrer la cadena de bits
        for (int i = 0; i < k; i++) {
            char bit = encodedString.charAt(i);


            if (bit == '0') {
                currentNode = L[currentNode]; // Mover a la izquierda
            } else { 
                currentNode = R[currentNode]; // Mover a la derecha
            }

            // Comprobar si hemos llegado a una hoja, osea L[i] == 0 y R[i] == 0.
            if (L[currentNode] == 0) {
                
                decodedText.append(alfabeto[currentNode - 1]);
                currentNode = root;
            } 
        }
        if (currentNode != root) {
            System.err.println("Advertencia: La cadena de bits de entrada parece estar incompleta o corrupta.");
        }

        return decodedText.toString();
    }
}
