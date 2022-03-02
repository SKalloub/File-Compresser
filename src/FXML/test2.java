package FXML;

public class test2 implements Runnable{
    public static void main(String[] args) throws InterruptedException {
        //. ....
        System.out.println(".....");
        test2 test = new test2();
        test.run();
        Thread.sleep(1000);
    }

    @Override
    public void run() {
        System.out.println("running in the same time");
    }
}
