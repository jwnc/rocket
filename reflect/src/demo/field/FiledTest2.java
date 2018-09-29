package demo.field;

import org.junit.Test;
import util.ReflectUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Field与继承
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/20 11:08
 */
public class FiledTest2 {
    class A {
        public int a1 = 11;
        protected int a2 = 12;
        private int a3 = 13;
    }

    class B extends A {
        public int b1 = 21;
        protected int b2 = 22;
        private int b3 = 23;
    }

    @Test
    public void b() throws NoSuchFieldException, IllegalAccessException {
        B b = new B();
        System.out.println(Arrays.toString(b.getClass().getFields())); // 获取本身和从所有父类继承的public字段 [public int demo.field.FiledTest2$B.b1, public int demo.field.FiledTest2$A.a1]
        Field[] declaredFields = b.getClass().getDeclaredFields(); // 获取本身的所有字段
        System.out.println(Arrays.toString(declaredFields)); // [public int demo.field.FiledTest2$B.b1, protected int demo.field.FiledTest2$B.b2, private int demo.field.FiledTest2$B.b3, final demo.field.FiledTest2 demo.field.FiledTest2$B.this$0]
        System.out.println(b);

        // 以下三个输出是相同的
        System.out.println(declaredFields[declaredFields.length - 1].get(b));
        System.out.println(this);
        System.out.println(b.getClass().getDeclaredField("this$0").get(b));

        // 循环父类获取字段
        Field b3 = ReflectUtils.findFieldRecurse(b.getClass(), "b3");
        if (!b3.isAccessible()) {
            b3.setAccessible(true);
        }
        System.out.println(b3.get(b));

        Field a3 = ReflectUtils.findFieldRecurse(b.getClass(), "a3");
        if (!a3.isAccessible()) {
            a3.setAccessible(true);
        }
        System.out.println(a3.get(b));
    }
}
