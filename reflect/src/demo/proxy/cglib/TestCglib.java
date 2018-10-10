package demo.proxy.cglib;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 被代理类的方法中, final方法不被代理
 * 详细解析过程可参考https://www.cnblogs.com/monkey0307/p/8328821.html
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/10/10 15:55
 */
public class TestCglib {

    public static void main(String[] args) {
        // 生成动态代理的class文件, 到C:\TEMP\demo\proxy\cglib目录下.文件名为Object$$FastClassByCGLIB$$3f697993.class
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\TEMP");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BookFacadeImpl1.class);
        enhancer.setCallback(new BookFacadeCglib());
        BookFacadeImpl1 bookCglib = (BookFacadeImpl1) enhancer.create();
        bookCglib.addBook();
        bookCglib.finalMethod();
        bookCglib.getClass(); // 这也是final方法

        System.out.println(bookCglib.hashCode());
        new BookFacadeImpl2().finalMethod();

        System.out.println("注意toString方法输出..先调用toString, 再调用里面的hashCode");
        System.out.println(bookCglib.toString());
    }
}