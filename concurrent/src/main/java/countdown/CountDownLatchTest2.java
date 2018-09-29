package countdown;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 配合线程池测试
 * 可以配合线程池测试, 结果如下
 * pool-1-thread-2 start!
 * pool-1-thread-1 start!
 * pool-1-thread-1 is over!
 * pool-1-thread-1 start!
 * pool-1-thread-1 is over!
 * pool-1-thread-2 is over!
 * main is over!
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/25 16:40
 */
public class CountDownLatchTest2 {
    static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        threadPoolExecutor.execute(new A(countDownLatch));
        threadPoolExecutor.execute(new A(countDownLatch));
        threadPoolExecutor.execute(new A(countDownLatch));
        countDownLatch.await();
        threadPoolExecutor.shutdown();// 关闭线程池
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
