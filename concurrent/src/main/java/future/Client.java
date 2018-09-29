package future;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Client {
    private static long timeout = 99L;
    private static TimeUnit timeUnit = TimeUnit.SECONDS;

    public static void main(String[] args) throws Throwable {
        evaluate();
    }

    public static void evaluate() throws Throwable {
        CallableStatement callable = new CallableStatement();
        final FutureTask<Throwable> task = new FutureTask<Throwable>(callable);
        ThreadGroup threadGroup = new ThreadGroup("FailOnTimeoutGroup");
        Thread thread = new Thread(threadGroup, task, "Time-limited test");
        thread.setDaemon(true);
        thread.start();
        callable.awaitStarted();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.cancel(true);
            }
        }).start();
        Throwable throwable = getResult(task, thread);
        if (throwable != null) {
            throw throwable;
        }
        System.out.println("evaluate is over");
    }

    private static Throwable getResult(FutureTask<Throwable> task, Thread thread) {
        try {
            if (timeout > 0) {
                return task.get(timeout, timeUnit);
            } else {
                return task.get();
            }
        } catch (InterruptedException e) {
            return e; // caller will re-throw; no need to call Thread.interrupt()
        } catch (ExecutionException e) {
            // test failed; have caller re-throw the exception thrown by the test
            return e.getCause();
        } catch (TimeoutException e) {
            return e;
        }
    }

    static class CallableStatement implements Callable<Throwable> {
        private final CountDownLatch startLatch = new CountDownLatch(1);

        @Override
        public Throwable call() throws Exception {
            startLatch.countDown();
            int i = 0;
            while (i++ < 100) {
                Thread.sleep(1000L);
            }
            return null;
        }

        public void awaitStarted() throws InterruptedException {
            startLatch.await();
        }
    }
}
