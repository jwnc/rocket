package reentrant;


import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: ConditionDemo
 * @description:
 * @author: LvJianwei
 * @create: 2018-02-11 15:57
 **/
public class ConditionDemo {

    public static void main(String[] args) {
        ConditionDemo demo = new ConditionDemo();
        Runnable rAdd = () -> {
            while (true) {
                try {
                    demo.countAdd();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable rReduce = () -> {
            while (true) {
                try {
                    demo.countReduce();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(rReduce);
            t.start();
        }
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(rAdd);
            t.start();
        }


    }

    private Random random = new Random(System.currentTimeMillis());
    private ReentrantLock locker = new ReentrantLock();
    private Condition enough;
    private int count = 0;
    private int enoughCount = 3;

    public ConditionDemo() {
        enough = locker.newCondition();
    }

    public void caculateWithLock() {
        locker.lock();
        try {
            int randomResult = random.nextInt();
            boolean shouldWait = randomResult % 5 == 0;
            System.out.printf("randomResult:%d%%5==0:%b,%s\n", randomResult, shouldWait, shouldWait ? "await" : "continue");
            while (!shouldWait) {
                countAdd();
                enough.await();
            }
            countReduce();
            printLockStatus();
            longTimeLock();
            System.out.println("final count:" + count);
            enough.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }

    public void countAdd() {
        locker.lock();
        try {
            count++;
            System.out.printf("Add,count:%d\n", count);
            if (count > enoughCount) {
                System.out.println("signAll");
                enough.signalAll();
            }
        } finally {
            locker.unlock();
        }
    }

    public void countReduce() {
        System.out.println("countReduce start,threadID:" + Thread.currentThread().getId());
        locker.lock();
        try {
            while (count < enoughCount) {
                System.out.printf("threadID:%s,await,count:%d\n", Thread.currentThread().getId(), count);
                enough.await();
            }
            count--;
            System.out.printf("threadID:%s,reduce,count:%d\n", Thread.currentThread().getId(), count);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }

    public void longTimeLock() {
        locker.lock();
        try {
            printLockStatus();
            int locktime = 3000;
            System.out.printf("longTimeLock:%d ms\n", locktime);
            Thread.sleep(locktime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }

    private void printLockStatus() {
        System.out.printf("lock count:%d,queueLength:%d\n", locker.getHoldCount(), locker.getQueueLength());
    }
}
