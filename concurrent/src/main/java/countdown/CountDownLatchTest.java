package countdown;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new A(countDownLatch).start();
        new A(countDownLatch).start();
        new A(countDownLatch).start();
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " is over!");
    }

    public static class A extends Thread {
        private CountDownLatch countDownLatch;

        public A(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            super.run();
            System.out.println(Thread.currentThread().getName() + " start!");
            try {
                Thread.sleep(new Random().nextInt(9000) + 3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + " is over!");
        }
    }
}
