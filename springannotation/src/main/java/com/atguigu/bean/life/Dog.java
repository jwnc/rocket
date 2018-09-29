package com.atguigu.bean.life;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * ���ٵ���setApplicationContext����, �ᷢ���и�BeanPostProcessor(ApplicationContextAwareProcessor)������ApplicationContextAware�ӿڵ���Ϊ
 * PostConstruct��PreDestroyע�����ʵ��������InitDestroyAnnotationBeanPostProcessor�����������õ�
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/28 21:43
 */
@Component
public class Dog implements ApplicationContextAware {
    //ʹ��ע��Ҳ����ֱ�ӻ�ȡ, ԭ����AutowiredAnnotationBeanPostProcessor
//	@Autowired
    ApplicationContext applicationContext;

    public Dog() {
        System.out.println("Dog constructor...");
    }

    //���󴴽�����ֵ֮�����
    @PostConstruct
    public void init() {
        System.out.println("Dog....@PostConstruct...");
    }

    //�����Ƴ�����֮ǰ
    @PreDestroy
    public void detory() {
        System.out.println("Dog....@PreDestroy... applicationContext=" + applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
