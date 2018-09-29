package readwrite;

import javax.xml.crypto.Data;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @author amber2012
 * <p>
 * jdk文档中关于ReentrantReadWriteLock类使用的一个很好的例子，以下是具体的介绍：
 * <p>
 * 在使用某些种类的 Collection 时，可以使用 ReentrantReadWriteLock 来提高并发性。通常，在预期 collection
 * 很大，读取者线程访问它的次数多于写入者线程，并且 entail 操作的开销高于同步开销时，这很值得一试。例如，以下
 * 是一个使用 TreeMap 的类，预期它很大，并且能被同时访问。
 */
public class RWDictionary {
    private final Map<String, Data> map = new TreeMap<String, Data>();
    private final ReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock readLock = rwl.readLock();
    private final Lock writeLock = rwl.writeLock();

    public Data get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 带写锁
     **/
    public int size1() {
        writeLock.lock();
        try {
            System.out.println(" write lock for size1..");
            return map.size();
        } finally {
            try {
                Thread.sleep(3222);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeLock.unlock();
            System.out.println(" write lock for size1 over..");
        }

    }

    /**
     * 带读锁
     **/
    public int size3() {
        readLock.lock();
        try {
            System.out.println(" read lock for size3..");
            return map.size();
        } finally {
            try {
                Thread.sleep(3222);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readLock.unlock();
            System.out.println(" read lock for size3 over..");
        }

    }

    /**
     * 不带锁
     **/
    public int size2() {
        return map.size();
    }

    public String[] allKeys() {
        readLock.lock();
        try {
            return (String[]) map.keySet().toArray();
        } finally {
            readLock.unlock();
        }
    }

    public Data put(String key, Data value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * TreeMap原生clear很快, 直接size置零
     **/
    public void clear() {
        writeLock.lock();
        try {
            map.clear();
        } finally {
            writeLock.unlock();
        }
    }

    public void addMore() {
        writeLock.lock();
        try {
            System.out.println("addMore...");
            for (int i = 0; i < 2000000; i++) {
                map.put("a" + i, new Data() {
                });
            }
        } finally {
            writeLock.unlock();
            System.out.println("addMore over...");
        }
    }
}