public class temp{
    public static int fib(int n, int[] a){
    if(a[n]!=0){
        return a[n];
    }
    if(n<=1){
        a[n]=n;
        return n;
    }
    else{
        a[n]=fib(n-1,a)+fib(n-2,a);
        return a[n];
    }
}
public static void main(String[] args) {
    int[] a=new int[100];
    System.out.println(fib(4,a));
}
}

