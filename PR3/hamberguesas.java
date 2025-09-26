public class hamberguesas {
    public String calculaMejor(int n, int m, int t){
       int a=t/n;
       int b=t/m;
       int da=a*n-t;
       int db=b*n-t;
       if(a>b){
            if(da+a==b){
                a--;
                b=1;
            }
       }
       else{
            if(db+b==a){
                b--;
                a=1;
            }
       }
       String res= "a= "+a+ "b= "+b;
       return res;
    }
}
