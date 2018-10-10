package demo.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 使用cglib动态代理
 */
public class BookFacadeCglib implements MethodInterceptor {
    @Override
    // 回调方法
    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        System.out.println("事物开始");
        Object o = proxy.invokeSuper(obj, args);
        System.out.println("事物结束");
        return o;
    }
}