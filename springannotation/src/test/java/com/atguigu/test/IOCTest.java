package com.atguigu.test;

import com.atguigu.bean.Person;
import com.atguigu.config.MainConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

public class IOCTest {
    AnnotationConfigApplicationContext applicationContext1 = new AnnotationConfigApplicationContext(MainConfig.class);

    @Test
    public void testImport() {
    }

    @Test
    public void printBeans() {
        String[] definitionNames = applicationContext1.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
        System.out.println(applicationContext1.getBean("dog"));
    }

    @Test
    public void test03() {
        String[] namesForType = applicationContext1.getBeanNamesForType(Person.class);
        ConfigurableEnvironment environment = applicationContext1.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println(property);
        for (String name : namesForType) {
            System.out.println(name);
        }

        Map<String, Person> persons = applicationContext1.getBeansOfType(Person.class);
        System.out.println(persons);
    }

}
