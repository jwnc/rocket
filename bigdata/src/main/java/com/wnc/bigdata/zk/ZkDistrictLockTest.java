package com.wnc.bigdata.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用zk3.5以上版本才行
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2019/1/24 18:32
 */
public class ZkDistrictLockTest {
    //这里是服务器里安装的ZK，conf -> zoo.cfg 里自己配的 server
    private static final String connectString = "127.0.0.1:2181";
    private static final int sessionTimeout = 2000;
    private CuratorFramework client;
    private int a = 0;

    public ZkDistrictLockTest() {
    }

    public void connect() {
        //创建zookeeper的客户端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
    }

    private void startClient() {
        client.start();
    }

    private void closeClient() {
        client.close();
    }

    public void testAdd() {
        //创建分布式锁, 锁空间的根节点路径为/curator/lock
        InterProcessMutex mutex = null;
        try {
            mutex = new InterProcessMutex(client, "/curator/lock");
            mutex.acquire();
            //获得了锁, 进行业务流程
            System.out.println(Thread.currentThread() + " Enter mutex");
            //完成业务流程
            a++;
            Thread.sleep(500 + new SecureRandom().nextInt(300));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (mutex != null) {
                //释放锁
                try {
                    System.out.println(Thread.currentThread() + " Release mutex");
                    mutex.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        final ZkDistrictLockTest zkDistrictLockTest = new ZkDistrictLockTest();
        zkDistrictLockTest.connect();
        zkDistrictLockTest.startClient();
        final CountDownLatch countDownLatch = new CountDownLatch(100);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    zkDistrictLockTest.testAdd();
                    countDownLatch.countDown();
                }
            });
        }
        executorService.shutdown();
        countDownLatch.await();
        System.out.println(zkDistrictLockTest.a);
    }


}
