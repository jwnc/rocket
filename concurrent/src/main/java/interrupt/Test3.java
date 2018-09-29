package interrupt;

/**
 * interrupt, interrupted, isInterrupted比较
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/21 15:28
 */
public class Test3 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println();
                System.out.println("子线程:isInterrupted:" + Thread.currentThread().isInterrupted());
                System.out.println("子线程interrupted:" + Thread.currentThread().interrupted());
                System.out.println("子线程interrupted:" + Thread.currentThread().interrupted());
            }
        });
        thread.start();
        System.out.println("\n子线程中断");
        thread.interrupt();
        System.out.println("查看thread.interrupted源码实际调用的其实是主线程(currentThread)的方法,返回:" + thread.interrupted());

        //主线程中断
        System.out.println("\n主线程首次中断");
        Thread.currentThread().interrupt();
        System.out.println("M:interrupted:" + Thread.currentThread().interrupted());
        System.out.println("M:interrupted:" + Thread.currentThread().interrupted());

        //主线程二次中断
        Thread.currentThread().interrupt();
        //isInterrupted始终取得当前中断状态, 注意源码的话, 发现它调用的方法里用的false, 而interrupted用的true
        System.out.println("\n主线程二次中断");
        System.out.println("M:isInterrupted:" + Thread.currentThread().isInterrupted());
        System.out.println("M:isInterrupted:" + Thread.currentThread().isInterrupted());
        //interrupted 调用的是currentThread的中断判断.是静态方法,所以用对象的方式去调没有任何意义, 也很可能会出错
        System.out.println("M:interrupted:" + thread.interrupted());//中断后,第一次调用,true,然后自动清空
        System.out.println("M:interrupted:" + thread.interrupted());//第一次后调用,false
    }
}
