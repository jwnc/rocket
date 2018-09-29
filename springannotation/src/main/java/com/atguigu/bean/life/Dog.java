package com.atguigu.bean.life;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 跟踪调试setApplicationContext方法, 会发现有个BeanPostProcessor(ApplicationContextAwareProcessor)来控制ApplicationContextAware接口的行为
 * PostConstruct和PreDestroy注解最后实际上是由InitDestroyAnnotationBeanPostProcessor来解析并调用的
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/28 21:43
 */
@Component
public class Dog implements ApplicationContextAware {
    //使用注解也可以直接获取, 原理是AutowiredAnnotationBeanPostProcessor
//	@Autowired
    ApplicationContext applicationContext;

    public Dog() {
        System.out.println("Dog constructor...");
    }

    //对象创建并赋值之后调用
    @PostConstruct
    public void init() {
        System.out.println("Dog....@PostConstruct...");
    }

    //容器移除对象之前
    @PreDestroy
    public void detory() {
        System.out.println("Dog....@PreDestroy... applicationContext=" + applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
