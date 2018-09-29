package readwrite;

import javax.xml.crypto.Data;

public class RWClient {
    public static void main(String[] args) throws InterruptedException {
//        testSize();
        testClear();
    }

    private static void testClear() throws InterruptedException {
        RWDictionary dictionary = new RWDictionary();
        for (int i = 1; i < 1000000; i++) {
            dictionary.put(String.valueOf(i), new Data() {
            });
        }
        System.out.println("2222");
        /** map动态添加 **/
        new Thread(new Runnable() {
            @Override
            public void run() {
                dictionary.addMore();
            }
        }).start();
        System.out.println("3333");
        /** 获取size **/
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Get size2:" + dictionary.size2());// 不带锁
                System.out.println("Get size3:" + dictionary.size3()); // 读写互斥
            }
        }).start();
        Thread.sleep(200);
        System.out.println("Get size1:" + dictionary.size1());//写写互斥
    }

    private static void testSize() throws InterruptedException {
        RWDictionary dictionary = new RWDictionary();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 1000000; i++) {
                    dictionary.put(String.valueOf(i), new Data() {
                    });
                }
                System.out.println(dictionary.size1());
            }
        }).start();

        Thread.sleep(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Get size:" + dictionary.size1());
            }
        }).start();
    }
}
