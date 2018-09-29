package com.atguigu.test.config;

import com.atguigu.config.AutowiredConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutowiredConfigTest {
    static ApplicationContext applicationContext;

    @BeforeClass
    public static void load() {
        applicationContext = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        System.out.println("context load over..");
    }

    @Test
    public void autowired() {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
        System.out.println(applicationContext.getBean("bookService"));
        System.out.println(applicationContext.getBean("boss"));
    }
}
