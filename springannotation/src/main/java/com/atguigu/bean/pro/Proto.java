package com.atguigu.bean.pro;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class Proto {
    public synchronized void job(int i) {
        System.out.println(System.currentTimeMillis() + " " + this + " do job " + i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() + " " + this + " do job over " + i);
    }
}