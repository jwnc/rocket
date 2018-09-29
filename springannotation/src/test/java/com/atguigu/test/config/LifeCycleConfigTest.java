package com.atguigu.test.config;

import com.atguigu.config.LifeCycleConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LifeCycleConfigTest {
    static AnnotationConfigApplicationContext applicationContext;

    @BeforeClass
    public static void load() {
        applicationContext = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        System.out.println("context load over..");
    }

    /**
     * 关闭容器
     *
     * @author nengcai.wang
     * @version: 1.0
     * @since 2018/9/28 17:47
     */
    @AfterClass
    public static void close() {
        System.out.println("close applicationContext.");
        applicationContext.close();
    }


    //Dog类里面有好多重要注释, 对几种常见BeanPostProcessor做了解释
    @Test
    public void life() {
    }


}
