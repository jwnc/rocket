package demo;

import org.junit.Test;

import java.util.Collection;

public class Demo01 {
    /***
     * super用于写
     * @since 2018/9/19 17:38
     * @param arr
     * @param collection
     * @return void
     */
    public static <T> void add(T[] arr, Collection<? super T> collection) {
        for (T t : arr) {
            collection.add(t);
        }
    }

    /**
     * extends 用于读取
     *
     * @param arr
     * @param collection
     * @return void
     * @since 2018/9/19 17:37
     */
    public static <T> void read(T[] arr, Collection<? extends T> collection) {
        for (T t : arr) {
            System.out.println(t);
        }
    }

    @Test
    public void testAdd() {

    }
}
