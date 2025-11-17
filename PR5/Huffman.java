
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
            // 1. Llamar recursivamente al PADRE de x
            huffmanEncodeOne(P[x]);
            int padre = P[x];
            // 2. Comprobar si 'x' es el hijo IZQUIERDO de su padre
            if (x == L[padre]) {
                // B[m] <- 0
                this.bitBuffer.append('0');
            } 
            // 3. Si no, 'x' es el hijo DERECHO
            else {
                // B[m] <- 1
                this.bitBuffer.append('1');
            }
        }
    }
}
