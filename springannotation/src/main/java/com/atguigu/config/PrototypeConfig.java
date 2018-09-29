package com.atguigu.config;

import com.atguigu.bean.pro.ProtoBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan({"com.atguigu.bean.pro"})
public class PrototypeConfig {
    @Scope("prototype")
    @Bean
    public ProtoBean protoBean() {
        return new ProtoBean();
    }
}
