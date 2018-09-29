package semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 使用Semaphore进行最大并发数的控制
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/26 10:55
 */
public class SemaphoreTest {

    public static void main(String[] args) throws InterruptedException {

        //信号量控制并发数最多为3
        Semaphore semaphore = new Semaphore(3);

        //同时开启10个线程
        for (int i = 0; i < 10; i++) {
            new Thread(new ReaderThread(semaphore, i)).start();
        }

    }


    static class ReaderThread implements Runnable {

        Semaphore semaphore;

        //用户序号
        int userIndex;

        public ReaderThread(Semaphore semaphore, int userIndex) {
            this.semaphore = semaphore;
            this.userIndex = userIndex;
        }

        @Override
        public void run() {

            try {
                //获取许可
                semaphore.acquire(1);
                //模拟访问资源所用的时间
                TimeUnit.SECONDS.sleep(1);

                System.out.println("用户 " + userIndex + " 访问资源,时间:" + System.currentTimeMillis());

                //释放许可
                semaphore.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}