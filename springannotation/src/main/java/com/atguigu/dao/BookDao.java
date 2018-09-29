package com.atguigu.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BookDao {

    private String label = "1";

    @Override
    public String toString() {
        return "BookDao{" +
                "label='" + label + '\'' +
                '}';
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
