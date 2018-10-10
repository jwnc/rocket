package demo.proxy.jdk;

/**
 * 被代理类的所有方法都会被代理
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/10/10 15:55
 */
public class TestProxy {
    public static void main(String[] args) throws ClassNotFoundException {
        //该设置用于输出jdk动态代理产生的类, 输出到项目路径的\com\sun\proxy文件夹下
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        BookFacadeProxy proxy = new BookFacadeProxy();
        BookFacade bookProxy = (BookFacade) proxy.bind(new BookFacadeImpl());
        bookProxy.addBook();
        bookProxy.finalMethod();
        Class<?> aClass = Class.forName("com.sun.proxy.$Proxy0");
    }
}