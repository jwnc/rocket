package com.atguigu.config;

import com.atguigu.bean.source.SourcePerson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:person.properties")
@Configuration
public class SourceConfig {

    @Bean
    public SourcePerson sourcePerson() {
        SourcePerson p = new SourcePerson("sourceTest", 11);
        return p;
    }
}