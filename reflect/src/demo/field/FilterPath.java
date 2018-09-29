package demo.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 可重复的注解, Repeatable注解 since1.8
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/19 18:33
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FilterPaths.class)
public @interface FilterPath {
    String value();
}