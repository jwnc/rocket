package com.atguigu.test.config;

import com.atguigu.config.ImportConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ImportConfigTest {
    static ApplicationContext applicationContext;

    @BeforeClass
    public static void load() {
        applicationContext = new AnnotationConfigApplicationContext(ImportConfig.class);
        System.out.println("context load over..");
    }

    @Test
    public void imp() {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
        System.out.println(applicationContext.getBean("colorFactoryBean").getClass());
        System.out.println(applicationContext.getBean("&colorFactoryBean").getClass());
    }
}
