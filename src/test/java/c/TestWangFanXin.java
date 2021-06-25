package c;

/**
 * @author zuojingang
 * @Title: TestWangFanXin
 * @Description: Todo
 * @date 3/17/21 8:14 PM
 */
public class TestWangFanXin {

    public static void main(String[] args) {

        for (int i=0;i<7;i++){

            try {
                System.out.println(sumNLine(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int sumNLine(int n){
        if (1>n){
            throw new IllegalArgumentException("illegal argument.");
        }
       if (1==n){
           return 1;
       }
        int preLine = n - 1;
        return 2*sumNLine(preLine)-preLine+2*n;
    }
}
