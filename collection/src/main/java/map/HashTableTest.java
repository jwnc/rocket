package map;

import java.util.HashMap;
import java.util.Random;

public class HashTableTest {
    class A {
        int a;

        public A(int a) {
            System.out.println(HashTableTest.this);
            this.a = a;
        }

        @Override
        public boolean equals(Object obj) {
//            return super.equals(obj); //此时找到的value为null, 因为新new出来的对象在map中永远找不到equals相等的
            return obj != null && ((A)obj).a == this.a; // map.get的时候既要比较hash, 也要比较equals
        }

        @Override
        public int hashCode() {
            return a % 10;
        }
    }

    public static void main(String[] args) {
        HashTableTest hashTableTest = new HashTableTest();
        A a = hashTableTest.new A(111);
        System.out.println(a.hashCode());

        HashMap map = new HashMap();
        for (int i = 0; i < 100; i++) {
            a = hashTableTest.new A(i);
            map.put(a, a.a);
        }
        for (int i = 100; i < 200; i++) {
            a = hashTableTest.new A(i);
            map.put(a, a.a);
        }
        //此时的map中
        System.out.println(map.size()); // map.size() == 200 调试模式下 map.table.length = 512,
        // 两者为什么差两倍多? 因为在table大小为256时,threhold为256*0.75 = 192, 当table大小超过192时会扩容

        map.put(a, a.a); //这个时候a是放不进去的, 因为hashmap中已经存在hashcode和equals相同的key
        System.out.println(map.size()); //此时的map.size()还是200

        Object o = map.get(hashTableTest.new A(111));
        System.out.println(o); // 结果111
    }
}
