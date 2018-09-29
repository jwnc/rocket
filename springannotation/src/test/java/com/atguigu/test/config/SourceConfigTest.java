package com.atguigu.test.config;

import com.atguigu.config.SourceConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SourceConfigTest {

    @Test
    public void source() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SourceConfig.class);
        System.out.println("context load over..");
        System.out.println(applicationContext.getBean("sourcePerson"));
    }
}
