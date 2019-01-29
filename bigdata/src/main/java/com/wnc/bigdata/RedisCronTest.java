package com.wnc.bigdata;

import com.wnc.bigdata.redis.RedissonManager;
import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

public class RedisCronTest {
    public static void main(String[] args) {
        RedisCronTest redisCronTest = new RedisCronTest();
        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    redisCronTest.execute();
                }
            }).start();
        }
    }

    public void execute() {
        System.out.println("定时任务启动");
        RedissonManager redissonManager = new RedissonManager();
        RLock lock = redissonManager.getRedisson().getLock("CRON_LOCK");
        boolean getLock = false;
        try {
            //todo 若任务执行时间过短，则有可能在等锁的过程中2个服务任务都会获取到锁，这与实际需要的功能不一致，故需要将waitTime设置为0
            if (getLock = lock.tryLock(0, 1, TimeUnit.SECONDS)) {
                Thread.sleep(2222);
            } else {
                System.out.println("Redisson分布式锁没有获取到锁:,ThreadName :" + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            System.out.println("Redisson 获取分布式锁异常:" + e.getMessage());
        } finally {
            if (!getLock) {
                return;
            }
            lock.unlock();
            System.out.println("Redisson分布式锁释放锁,ThreadName :" + Thread.currentThread().getName());
        }
    }
}
