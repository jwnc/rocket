package com.atguigu.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

//自定义逻辑返回需要导入的组件
public class MyImportSelector implements ImportSelector {
    /***
     * 把类的全类名放入数组
     * @since 2018/9/28 16:04
     * @param importingClassMetadata
     * @return java.lang.String[]
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.atguigu.bean.Blue", "com.atguigu.bean.Yellow"};
    }
}
