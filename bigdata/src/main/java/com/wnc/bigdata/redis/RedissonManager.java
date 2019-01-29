package com.wnc.bigdata.redis;

import org.redisson.Redisson;
import org.redisson.config.Config;

public class RedissonManager {

    private Redisson redisson = null;
    private Config config = new Config();

    private static String host1 = "47.93.99.106:6379";

    public RedissonManager() {
        try {
            config.useSingleServer().setAddress(host1);
            redisson = (Redisson) Redisson.create(config);
            System.out.println("Redisson 初始化完成");
        } catch (Exception e) {
            System.out.println("init Redisson error: " + e.getMessage());
        }
    }

    public Redisson getRedisson() {
        return redisson;
    }
}