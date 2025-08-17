public class ABinBus<T extends Comparable<T>> {
    private NodoDoble<T> raiz;

    public ABinBus(){
        raiz = null;
    }

    public boolean isEmpty(){
        return raiz == null;
    }

    public String inOrden(){
        StringBuilder sB = new StringBuilder();
        inOrden(raiz,sB);
        return sB.toString();
    }

    private void inOrden(NodoDoble<T> actual, StringBuilder sB){
        if(actual!=null){
            inOrden(actual.getBefore(), sB);
            sB.append(actual.getDato()).append(" ");
            inOrden(actual.getNext(), sB);
        }

    }
    /*
    public String preOrden(){
        StringBuilder sB= new StringBuilder();
        preOrden(raiz, sB);

        return sB.toString();
    }

    private void preOrden(NodoDoble<T> actual, StringBuilder sB){
        if(actual != null){
            sB.append(actual.getDato()).append(" ");
            preOrden(actual.getBefore(), sB);
            preOrden(actual.getNext(), sB);
        }

    }



    public String postOrden(){
        StringBuilder sB =new StringBuilder();
        postOrden(raiz, sB);
        return sB.toString();
    }

    private void postOrden(NodoDoble<T> actual, StringBuilder sB){
        if(actual != null){
            postOrden(actual.getBefore(), sB);
            postOrden(actual.getNext(), sB);
            sB.append(actual.getDato()).append(" ");
        }
    }
    */

    public boolean agrega(T dato){
        boolean resp;
        if(isEmpty()){
            raiz = new NodoDoble<>(dato);
            resp = true;
        }
        else
            resp = agrega(raiz, dato);
        return resp;
    }

    private boolean agrega(NodoDoble<T> actual, T dato){
        if(actual.getDato().equals(dato))
            return false;
        else{
            if(actual.getDato().compareTo(dato)>0){
                if(actual.getBefore() == null){
                    actual.setBefore(new NodoDoble<>(dato));
                    return true;
                }
                else
                    return agrega(actual.getBefore(), dato);
            }
            else{
                if(actual.getNext() == null){
                    actual.setNext(new NodoDoble<>(dato));
                    return true;
                }
                else{
                    return agrega(actual.getNext(), dato);
                }
            }
        }
    }

    public static void main(String[] args) {
        ABinBus<Integer> num = new ABinBus<>();
        num.agrega(600);
        num.agrega(350);
        num.agrega(180);
        num.agrega(420);
        num.agrega(800);
        num.agrega(950);

        System.out.println("\nEn orden inOrden: "+num.inOrden());
        /*
         System.out.println("\nEn orden preOrden: "+num.preOrden());
         System.out.println("\nEn orden postOrden: "+num.postOrden());
         */

    }
}