package demo.type;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

public class RepeatableTypeAnnoTest {
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(FilterPaths.class)
    public @interface FilterPath {
        String value();
    }


    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FilterPaths {
        FilterPath[] value();
    }

    @Logger
    @FilterPath("/a1")
    @FilterPath("/a2")
    private static class A {
    }

    private static class B extends A {
    }

    @Test
    public void test() {
        A a = new A();
        //多个相同注解,会自动封装到Repeatable指定的枚举中
        // [@jdk.nashorn.internal.runtime.logging.Logger(name=),
        //  @demo.type.RepeatableTypeAnnoTest$FilterPaths(value=[@demo.type.RepeatableTypeAnnoTest$FilterPath(value=/a1), @demo.type.RepeatableTypeAnnoTest$FilterPath(value=/a2)])]
        System.out.println(Arrays.toString(a.getClass().getAnnotations()));
        // 多个相同注释,独立放到一个数组中
        // [@demo.type.RepeatableTypeAnnoTest$FilterPath(value=/a1), @demo.type.RepeatableTypeAnnoTest$FilterPath(value=/a2)]
        System.out.println(Arrays.toString(a.getClass().getAnnotationsByType(FilterPath.class)));

        B b = new B();
        System.out.println(Arrays.toString(b.getClass().getAnnotations()));
        System.out.println(Arrays.toString(b.getClass().getAnnotationsByType(FilterPath.class)));


    }
}
