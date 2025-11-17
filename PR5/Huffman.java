public class Huffman {
    private char[] alfabeto;
    private int[] frecuencia;
    private int n;


    public Huffman(int[]frecuencia, char[]alfabeto, int tot){
        this.alfabeto=alfabeto;
        this.frecuencia=frecuencia;
        this.n=tot;
    }
    public void buildHuffman(){
        int totalNodes = 2 * n; 
        Cola prioridad= new Cola();
        int[] f = new int[totalNodes]; // frecuencia del nodo i
        int[] L = new int[totalNodes]; // hijo izquierdo del nodo i
        int[] R = new int[totalNodes]; // hijo derecho del nodo i
        int[] P = new int[totalNodes]; // padre del nodo i  

        for(int i=1; i<n; i++){
            L[i]=0;
            R[i]=0;

        }
    }

    public char extractMin(int[]F){ //Extrae el numero con menor frecuencua
        int imin=-1;
        int min;
        int nF=F.length;
        if(F!=null){
            min=F[0];
            imin=0;
            for(int i=1; i<nF; i++){
                if(F[i]<min&&F[i]!=-1){
                    min=F[i];
                    imin=i;
                }
            } 
            F[imin]=-1;
        }
        return alfabeto[imin];
    }
}
