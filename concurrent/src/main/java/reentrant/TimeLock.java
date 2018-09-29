package reentrant;

import custom.BaseRunTimeTest;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TimeLock extends BaseRunTimeTest implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    public void run() {
        String tname = Thread.currentThread().getName();
        try {
            int times = 6;
            int waitSeconds = 5;
            int i = 0;
            if (lock.tryLock(waitSeconds, TimeUnit.SECONDS)) {
                System.out.println(tname + " get lock succeed! will lock for " + times + " seconds");
                while (i < times) {
                    i++;
                    Thread.sleep(1 * 1000);
                    System.out.println(tname + ", " + i + " seconds passed.");
                }
            } else {
                System.out.println(waitSeconds + " seconds passed, " + tname + " get Lock Failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 查询当前线程是否保持此锁。
            if (lock.isHeldByCurrentThread()) {
                System.out.println(tname + " release lock");
                lock.unlock();
            }
        }
    }

    /**
     * 在本例中，由于占用锁的线程会持有锁长达6秒，故另一个线程无法再5秒的等待时间内获得锁，因此请求锁会失败。
     */
    @Test
    public void timelock() throws InterruptedException {
        TimeLock timeLock = new TimeLock();
        Thread t1 = new Thread(timeLock, "线程1");
        Thread t2 = new Thread(timeLock, "线程2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
