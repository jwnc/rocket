package demo;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 自动强转返回值类型为变量定义类型
 * @author nengcai.wang
 * @date 2018/12/6
 */
public class ReturnValue {
    static Map<String, Object> map = new HashMap<>();

    public static <K, V> V get(K k) {
        return (V) map.get(k);
    }

    @Test
    public void a() {
        map.put("a", 1);
        map.put("a", 2);
        map.put("a", 3);
        int i = get("a");
        System.out.println(i);
    }
}
