package demo.field;

import org.junit.Test;
import util.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

public class FiledTest {
    private int a = 10;
    @FilterPath("/b")
    @FilterPath("/a")
    @PageDao(listQuery = "xxx")
    public static volatile Integer b = 10;

    /***
     * 基础功能
     * @since 2018/9/19 22:00
     * @param
     * @return void
     */
    @Test
    public void compareTypeAndClass() throws NoSuchFieldException, IllegalAccessException {
        Field a = getField("a");
        System.out.println(a.getClass()); // Field.class
        System.out.println(a.getType()); // int
        System.out.println(a.getDeclaringClass()); // class demo.FiledTest

        a.setAccessible(true);
        Object x = a.get(this);
        System.out.println("获取a字段的值:" + x); //获取对象的值
        Integer cast = Integer.class.cast(x);
        System.out.println(cast);
        a.set(this, 11);
        System.out.println("修改a字段的值为11:" + a.get(this)); //获取对象的值

        Field b = getField("b");
        System.out.println(b.getType()); // 字段本身class  class java.lang.Integer
        System.out.println(b.getDeclaringClass()); //字段所在类class  class demo.FiledTest
    }

    /***
     * 注解相关
     * @since 2018/9/19 22:00
     * @param
     * @return void
     */
    @Test
    public void annotion() throws NoSuchFieldException {
        Field b = getField("b");
        System.out.println("getAnnotations:" + b.getAnnotations()); // 封装重复注解,@demo.FilterPaths(value=[@demo.FilterPath(value=/b), @demo.FilterPath(value=/a)]), @demo.PageDao(countQuery=queryPageCount, listQuery=xxx)
        System.out.println("getAnnotation:\t\t" + b.getAnnotation(PageDao.class)); // 获取某个指定类型注解
        System.out.println("getAnnotation:\t\t" + b.getAnnotation(FilterPath.class)); // null, 已经被自动封装到FilterPaths里去了
        System.out.println("getAnnotation:\t\t" + b.getAnnotation(FilterPaths.class)); // @demo.FilterPaths(value=[@demo.FilterPath(value=/b), @demo.FilterPath(value=/a)])
        System.out.println("getAnnotatedType:" + b.getAnnotatedType());
        System.out.println("getAnnotationsByType:" + b.getAnnotationsByType(FilterPath.class)); // since1.8 获取某个指定类型注解数组, @demo.FilterPath(value=/b), @demo.FilterPath(value=/a)
    }

    /***修饰符相关
     * 类型                 int        二进制

     PUBLIC:           1             1
     PRIVATE:         2             10
     PROTECTED: 4              100
     STATIC:           8              1000
     FINAL:           16             10000
     SYNCHRONIZED: 32     100000
     VOLATILE: 64                 1000000
     TRANSIENT: 128            10000000
     NATIVE: 256                    100000000
     INTERFACE: 512             1000000000
     ABSTRACT: 1024            10000000000
     STRICT: 2048                  100000000000
     * @since 2018/9/19 21:59
     * @param
     * @return void
     */
    @Test
    public void modifiers() throws NoSuchFieldException {
        Field b = getField("b");
        Integer m = b.getModifiers();
        System.out.println(java.lang.Integer.toBinaryString(m)); //输出修饰符的二进制表示 1001001
        System.out.println(Integer.toHexString(m)); // 十六进制 49
        System.out.println(Modifier.toString(m)); // 输出修饰符名称 public static volatile
    }

    List<String> list1;
    List<?> list2;
    List<? super String> list3;
    List<? extends Number> list4;

    /***
     * 测试泛型
     * @since 2018/9/20 10:05
     * @param
     * @return void
     */
    @Test
    public void genericType() throws NoSuchFieldException {
        Type genericType = null;
        for (int i = 1; i <= 4; i++) {
            genericType = getField("list" + i).getGenericType();
            System.out.println(genericType);
            ReflectUtils.checkType(genericType);
            System.out.println(getField("list" + i));
        }
    }

    /***
     * 获取Field对象
     * @since 2018/9/20 10:04
     * @param fieldName
     * @return java.lang.reflect.Field
     */
    private Field getField(String fieldName) throws NoSuchFieldException {
        return this.getClass().getDeclaredField(fieldName);
    }
}
