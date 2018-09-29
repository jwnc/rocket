package demo.type;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

public class InheritedTypeAnnoTest {
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Repeatable(FilterPaths.class)
    public @interface FilterPath {
        String value();
    }


    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface FilterPaths {
        FilterPath[] value();
    }

    @FilterPath("/a1")
    @FilterPath("/a2")
    private static class A {
    }

    private static class B extends A {
    }

    @Test
    public void test() {
        A a = new A();
        System.out.println(Arrays.toString(a.getClass().getAnnotations()));
        System.out.println(Arrays.toString(a.getClass().getAnnotationsByType(FilterPath.class)));

        //在添加了@Inherited时, 若class B上面没有FilterPath注解, 则会继承class A的所有FilterPath注解, 所以下面输出与上面完全相同
        //否则,没有@Inherited时, 始终以class上面的注解为准
        B b = new B();
        System.out.println(Arrays.toString(b.getClass().getAnnotations()));
        System.out.println(Arrays.toString(b.getClass().getAnnotationsByType(FilterPath.class)));


    }
}
