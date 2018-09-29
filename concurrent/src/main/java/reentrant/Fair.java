package reentrant;

import custom.BaseRunTimeTest;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/27 15:22
 */
public class Fair extends BaseRunTimeTest implements Runnable {
    public ReentrantLock fairLock = new ReentrantLock(true);
    int max = 10;

    public void run() {
        int i = 0;
        while (i++ < max) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + "，获得锁!");
            } finally {
                fairLock.unlock();
            }
        }
    }

    @Test
    public void fair() throws InterruptedException {
        Fair fairLock = new Fair();
        Thread t1 = new Thread(fairLock, "线程1");
        Thread t2 = new Thread(fairLock, "线程2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}