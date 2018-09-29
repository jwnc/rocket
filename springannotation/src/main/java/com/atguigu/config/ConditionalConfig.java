package com.atguigu.config;

import com.atguigu.bean.Person;
import com.atguigu.condition.LinuxCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

//类中组件统一设置。满足当前条件，这个类中配置的所有bean注册才能生效；
//@Conditional(WindowsCondition.class)
@Configuration
public class ConditionalConfig {

    /**
     * @Conditional({Condition}) ： 按照一定的条件进行判断，满足条件给容器中注册bean
     * <p>
     * 如果系统是windows，给容器中注册("bill")
     * 如果是linux系统，给容器中注册("linus")
     */
//    @Conditional(WindowsCondition.class)
    @Bean("bill")
    public Person person01() {
        System.out.println("bill...init");
        return new Person("Bill Gates", 62);
    }

    @Conditional(LinuxCondition.class)
    @Bean("linus")
    public Person person02() {
        return new Person("linus", 48);
    }
}