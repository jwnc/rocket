package reentrant;

import custom.BaseRunTimeTest;
import org.junit.Test;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 结合Condition, 控制等待/执行的时机
 * // 让当前线程等待，并释放锁
 * void await() throws InterruptedException;
 * void awaitUninterruptibly();//// 和await类似，但在等待过程中不会响应中断
 * long awaitNanos(long nanosTimeout) throws InterruptedException;
 * boolean await(long time, TimeUnit unit) throws InterruptedException;
 * boolean awaitUntil(Date deadline) throws InterruptedException;
 * // 唤醒等待中的线程, 必须配合lock, unlock方法使用
 * void signal();
 * // 唤醒等待中的所有线程
 * void signalAll();
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/27 16:11
 */
public class ReenterLockCondition extends BaseRunTimeTest {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    private static abstract class RunJob implements Runnable {
        @Override
        public void run() {
            String tname = Thread.currentThread().getName();

            try {
                lock.lock();
                System.out.println(tname + "进入等待。。");
                doJob();
                System.out.println(tname + "继续执行.");
                Thread.sleep(new Random().nextInt(1000) + 1000);
            } catch (InterruptedException e) {
                System.out.println(tname + "运行被中断.");
            } catch (Exception e) {
            } finally {
                lock.unlock();
                System.out.println(tname + " finally.");

            }
        }

        public abstract void doJob() throws Exception;
    }

    private static Runnable runnable1 = new RunJob() {
        @Override
        public void doJob() throws Exception {
            condition.await();
        }
    };

    private static Runnable runnable2 = new RunJob() {
        @Override
        public void doJob() throws Exception {
            condition.awaitUninterruptibly();
        }
    };

    private static Runnable runnable3 = new RunJob() {
        @Override
        public void doJob() throws Exception {
            condition.awaitNanos(5000 * 1000 * 1000L);
        }
    };

    private static Runnable runnable4 = new RunJob() {
        @Override
        public void doJob() throws Exception {
            condition.await(3, TimeUnit.SECONDS);
        }
    };

    private static Runnable runnable5 = new RunJob() {
        @Override
        public void doJob() throws Exception {
            condition.awaitUntil(new Date(System.currentTimeMillis() + new Random().nextInt(2000) + 200));
        }
    };

    private static Runnable runnable6 = new RunJob() {
        @Override
        public void doJob() throws Exception {
            Thread.sleep(1000);
            condition.signalAll();
        }
    };

    /***
     * 可以通过signal来通知运行, 也可以用interrupt来中断运行
     * @since 2018/9/27 16:15
     * @param
     * @return void
     */
    @Test
    public void signal() throws InterruptedException {
        Thread thread = new Thread(runnable1, "thread--1");
        thread.start();
        Thread.sleep(2000);
        lock.lock();
        System.out.println("主线程发出信号");
        condition.signal();
        lock.unlock();
    }

    /***
     * 可以通过signal来通知运行, 也可以用interrupt来中断运行
     * @since 2018/9/27 16:15
     * @param
     * @return void
     */
    @Test
    public void await() throws InterruptedException {
        Thread thread = new Thread(runnable1, "thread--1");
        thread.start();
        Thread.sleep(2000);
        lock.lock();
        System.out.println("主线程发出信号");
        condition.signal();
        Thread.sleep(10);
        thread.interrupt(); // 会中断线程运行
        lock.unlock();
    }

    /***
     * 类似await, 但不能响应中断
     * @since 2018/9/27 16:47
     * @param
     * @return void
     */
    @Test(timeout = 3000)
    public void awaitUninterruptibly() throws InterruptedException {
        try {
            Thread thread = new Thread(runnable2, "thread--2");
            thread.start();
            lock.lock();
            thread.interrupt();// 无法中断, 线程会一直阻塞
            thread.join();
        } finally {
            lock.unlock();
        }
    }

    /***
     * awaitNanos, 指定纳秒数, 然后自动执行 1ms = 1000 * 1000ns;
     * @since 2018/9/27 16:47
     * @param
     * @return void
     */
    @Test
    public void awaitNanos() throws InterruptedException {
        Thread thread = new Thread(runnable3, "thread--3");
        thread.start();
        thread.join();
    }

    /***
     * await(long, TimeUnit), 指定时间单位以及数值
     * @since 2018/9/27 16:47
     * @param
     * @return void
     */
    @Test
    public void awaitTime() throws InterruptedException {
        Thread thread = new Thread(runnable4, "thread--4");
        thread.start();
        thread.join();
    }

    /***
     * awaitUntil(Date), 指定截至时间
     * @since 2018/9/27 16:47
     * @param
     * @return void
     */
    @Test
    public void awaitUntil() throws InterruptedException {
        Thread thread = new Thread(runnable5, "thread--5");
        thread.start();
        thread.join();
    }

    /***
     * signalAll 交给子线程控制
     * @since 2018/9/27 16:47
     * @param
     * @return void
     */
    @Test
    public void signalAll() throws InterruptedException {
        Thread thread1 = new Thread(runnable1, "thread--1");
        thread1.start();

        Thread thread6 = new Thread(runnable6, "thread--6");
        thread6.start();

        thread1.join();
        thread6.join();
    }

    /***
     * signalAll 交给主线程控制, 有小bug
     * @since 2018/9/27 16:47
     * @param
     * @return void
     */
    @Test
    public void signalAll2() throws InterruptedException {
        Thread thread1 = new Thread(runnable1, "thread--1");
        thread1.start();

        Thread thread4 = new Thread(runnable4, "thread--4");
        thread4.start();

        Thread.sleep(1000);//不加这个会出现死锁, 应该是为了让线程进入状态. 所以signalAll最好是放到某个子线程中通过业务逻辑来控制
        lock.lock(); // 使用signal前,必须在执行线程上lock
        condition.signalAll();
        lock.unlock();
        System.out.println("Main unlock");
        thread1.join();
        thread4.join();
    }
}