package com.atguigu.test.config;

import com.atguigu.config.ScopeConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScopeConfigTest {

    @Test
    public void scope() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ScopeConfig.class);
        System.out.println("context load over..");
        System.out.println(applicationContext.getBean("person").hashCode());
        System.out.println(applicationContext.getBean("person").hashCode());
    }
}
