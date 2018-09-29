package cyclic;

import java.util.concurrent.CyclicBarrier;

/**
 * 子线程内部完成所有任务结束监听, cyclicBarrier可重复利用
 * 阻塞线程数到达设定值后
 * barrierAction运行, 另外执行阻塞位置后面的代码
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/25 16:16
 */
public class CyclicBarrierTest {

    static CyclicBarrier cyclicBarrier;

    public static void main(String[] args) {
        int count = 10;
        test1(count);
        System.out.println("Main Over");
    }

    private static void test1(int count) {
        //当所有子任务都执行完毕时,barrierAction的run方法会被调用
        cyclicBarrier = new CyclicBarrier(count, () ->
                System.out.println("cyclicBarrier阻塞线程数到" + count + "个,执行barrierAction操作!"));
        //开启多个线程执行子任务
        for (int i = 0; i < count; i++) {
            new Thread(new CyclicBarrierThread(cyclicBarrier, i, "资源1")).start();
        }
        for (int i = count; i < count * 2; i++) {
            new Thread(new CyclicBarrierThread(cyclicBarrier, i, "资源2")).start();
        }
    }

    private static class CyclicBarrierThread implements Runnable {
        private CyclicBarrier cyclicBarrier;
        //任务序号
        private int taskNum;
        private String commonSource;

        public CyclicBarrierThread(CyclicBarrier cyclicBarrier, int taskNum, String commonSource) {
            this.cyclicBarrier = cyclicBarrier;
            this.taskNum = taskNum;
            this.commonSource = commonSource;
        }

        @Override
        public void run() {
            //执行子任务
            System.out.println("子任务：" + taskNum + " 执行完毕!");
            try {
                //等待所有子任务执行完成
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //释放资源
                System.out.println("子任务：" + taskNum + " 释放资源!" + commonSource);
            }

        }
    }
}