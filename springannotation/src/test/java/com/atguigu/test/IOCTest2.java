package com.atguigu.test;

import com.atguigu.bean.pro.Proto;
import com.atguigu.bean.pro.ProtoBean;
import com.atguigu.config.PrototypeConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest2 {
    AnnotationConfigApplicationContext applicationContext2 = new AnnotationConfigApplicationContext(PrototypeConfig.class);
    int i = 0;

    @Test
    public void d() throws InterruptedException {
        for (i = 0; i < 10; i++) {
            Proto proto = applicationContext2.getBean(Proto.class);
            new Thread(() -> {
                proto.job(i);
            }).start();
            Thread.sleep(333);
        }
        Thread.sleep(1000);// 等待线程执行完
    }

    @Test
    public void d2() throws InterruptedException {
        for (i = 0; i < 10; i++) {
            ProtoBean proto = applicationContext2.getBean(ProtoBean.class);
            new Thread(() -> {
                proto.job(i);
            }).start();
            Thread.sleep(333);
        }
        Thread.sleep(1000);// 等待线程执行完
    }

    public void d3() throws InterruptedException {
        for (i = 0; i < 10; i++) {
            ProtoBean proto = applicationContext2.getBean(ProtoBean.class);
            new Thread(() -> {
                proto.job(i);
            }).start();
            Thread.sleep(333);
        }
        Thread.sleep(1000);// 等待线程执行完
    }


}
