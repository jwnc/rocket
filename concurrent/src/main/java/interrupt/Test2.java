package interrupt;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    System.out.println("interrupted:" + Thread.interrupted() );
                    try {
                        Thread.sleep(10000000L);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException");
                    }
                }
            }
        });
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
// 如果thread中有InterruptedException异常,
// 而且两次isInterrupted等待时间比较长, 那么InterruptedException异常会重置中断状态为false
//        3000L.........
//        true
//        InterruptedException
//        false

// 如果thread中没有InterruptedException异常,
// 或者InterruptedException在两次isInterrupted之后执行,则两次输出为true
// 总之, 一旦InterruptedException产生,则isInterrupted被重置为flase
