package interrupt;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                }
            }
        });
        thread.setDaemon(true);// 子线程会随着主线程的结束而结束
        thread.start();
        Thread.sleep(3000L);
        System.out.println("3000L.........");
        thread.interrupt();
        System.out.println(thread.isInterrupted());
        for (int i = 0; i < 10000; i++) {

        }
        System.out.println(thread.isInterrupted());
    }
}
// 子线程中没有捕获任何interrupt异常的情况
//  返回结果
//  3000L.........
//  true
//  true
