package demo.clazz;

import org.junit.Test;

public class PrimitiveClass {
    /***
     * int.class 是原始class,无任何成员变量和方法. 与Integer.TYPE相同
     * 查看Integer源码
     *     public static final Class<Integer>  TYPE = (Class<Integer>) Class.getPrimitiveClass("int");
     * @since 2018/9/19 17:35
     * @param
     * @return void
     */
    @Test
    public void t() {
        Integer a = 1;
        Class<Integer> intClass = int.class;
        System.out.println(intClass == Integer.TYPE); // true
        System.out.println(intClass == Integer.class); // false
        System.out.println(Integer.TYPE.isPrimitive()); //true
        System.out.println(intClass.getMethods()); // 没有方法, 返回空
    }
}
