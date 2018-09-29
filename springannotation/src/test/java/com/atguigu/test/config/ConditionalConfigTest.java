package com.atguigu.test.config;

import com.atguigu.bean.Person;
import com.atguigu.config.ConditionalConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

public class ConditionalConfigTest {
    static ApplicationContext applicationContext;

    @BeforeClass
    public static void load() {
        applicationContext = new AnnotationConfigApplicationContext(ConditionalConfig.class);
        System.out.println("context load over..");
    }

    @Test
    public void conditional() {
        Environment environment = applicationContext.getEnvironment();
        System.out.println("system:" + environment.getProperty("os.name"));

        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }

        System.out.println(applicationContext.getBeansOfType(Person.class));
    }
}
