package com.atguigu.bean.pro;

public class ProtoBean {
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