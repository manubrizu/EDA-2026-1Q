package timer;

public class Main {
    public static void main(String[] args) {
        MyTimer t1 = new MyTimer(0);

        t1.stop(93623040);

        System.out.println(t1);

        MyTimer t2 = new MyTimer(10);

        try{
            t2.stop(5);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}