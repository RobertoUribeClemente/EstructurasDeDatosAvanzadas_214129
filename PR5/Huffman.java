public class Huffman {
    private char[] alfabeto;
    private int[] frecuencia;
    private int n;

    private char[] L, R, P;
    private int[] F;
    public Huffman(int[]frecuencia, char[]alfabeto, int tot){
        this.alfabeto=alfabeto;
        this.frecuencia=frecuencia;
        this.n=tot;
    }
    public void buildHuffman(){
        int[]F=frecuencia;
        for (int i = 0; i < n; i++) {
            L[i]=0; R[i]=0;
            
        }
        for (int i = n+1; i < 2n-1; i++) {
            char x=abecedario[extractMin(F)],
                 y=abecedario[extractMin(F)];
            
        }        
    }

    public char extractMin(int[]F){ //Extrae el numero con menor frecuencua
        int imin=-1;
        int min;
        if(!F.isEmpty()){
            min=F[0];
            imin=-1;
            for(int i=1; i<nF; i++){
                if(F[i]<min&&F[i]!=-1){
                    min=F[i];
                    imin=i;
                }
            } 
            F[imin]=-1;

        }
        return abecedario[imin];
    }
}
