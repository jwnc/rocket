package demo.field;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分页配置注解
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/19 15:34
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageDao {
    /**
     * 查询list的方法
     */
    String listQuery() default "queryPageList";

    /**
     * 查询count的方法
     */
    String countQuery() default "queryPageCount";
}
