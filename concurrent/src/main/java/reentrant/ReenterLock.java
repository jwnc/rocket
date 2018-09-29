package reentrant;

import custom.BaseRunTimeTest;
import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * 简单应用, 类似于synchronized
 * 时间消耗差不多
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/27 15:02
 */
public class ReenterLock extends BaseRunTimeTest {
    private ReentrantLock lock = new ReentrantLock();
    private int i = 0;
    int endExclusive = 10000000;

    @Test
    public void reent() throws InterruptedException {
        c(runnable1);
    }

    @Test
    public void sync() throws InterruptedException {
        c(runnable2);
    }

    public void c(Runnable runnable) throws InterruptedException {
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        // 利用join，等thread1,thread2结束后，main线程才继续运行，并打印 i
        thread1.join();
        thread2.join();
        // 利用lock保护的 i，最终结果为 2000000，如果不加，则值肯定小于此数值
        System.out.println(i);
    }

    // 循环1000000次
    private Runnable runnable1 = () -> {
        IntStream.range(0, endExclusive).forEach((j) -> {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        });
    };
    // 循环1000000次
    private Runnable runnable2 = () -> IntStream.range(0, endExclusive).forEach((j) -> {
        synchronized (ReenterLock.class) {
            i++;
        }
    });
}