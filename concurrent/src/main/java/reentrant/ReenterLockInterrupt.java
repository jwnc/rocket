package reentrant;

import custom.BaseRunTimeTest;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可响应中断的锁, 可以解决死锁问题
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/27 14:52
 */
public class ReenterLockInterrupt extends BaseRunTimeTest {
    private static class IntLock implements Runnable {
        public static ReentrantLock lock1 = new ReentrantLock();
        public static ReentrantLock lock2 = new ReentrantLock();
        int type;

        /**
         * 控制加锁顺序，产生死锁
         */
        public IntLock(int type) {
            this.type = type;
        }

        public void run() {
            String tname = Thread.currentThread().getName();
            try {
                if (type == 1) {
                    lock1.lockInterruptibly(); // 如果当前线程未被 中断，则获取锁。
                    Thread.sleep(500);
                    lock2.lockInterruptibly();
                    System.out.println(tname + "，执行完毕！");
                } else {
                    lock2.lockInterruptibly();
                    Thread.sleep(500);
                    lock1.lockInterruptibly();
                    System.out.println(tname + "，执行完毕！");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 查询当前线程是否保持此锁。
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
                System.out.println(tname + "，退出。");
            }
        }
    }

    @Test
    public void t() throws InterruptedException {
        IntLock intLock1 = new IntLock(1);
        IntLock intLock2 = new IntLock(2);
        Thread thread1 = new Thread(intLock1, "线程1");
        Thread thread2 = new Thread(intLock2, "线程2");
        thread1.start();
        thread2.start(); // 两线程会形成死锁
        Thread.sleep(3333);
        thread2.interrupt(); // 中断线程2
    }
}