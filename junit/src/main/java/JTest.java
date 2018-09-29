import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * FixMethodOrder指定排名(默认按方法名的hashcode), BeforeClass和Before排名靠后的先执行, 其他的顺序执行
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/20 18:04
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JTest {
    @BeforeClass
    public static void sysinit1() {
        System.out.println("BeforeClass...sysinit1.......");
    }

    @BeforeClass
    public static void sysinit2() {
        System.out.println("BeforeClass...sysinit2.......");
    }

    @Before
    public void b1() {
        System.out.println("Before...b1.......");
    }

    @Before
    public void b2() {
        System.out.println("Before...b2.......");
    }

    @After
    public void a2() {
        System.out.println("After...a2.......");
    }

    @After
    public void a1() {
        System.out.println("After...a1.......");
    }

    @AfterClass
    public static void end2() {
        System.out.println("AfterClass...end2.......");
    }

    @AfterClass
    public static void end1() {
        System.out.println("AfterClass...end1.......");
    }

    @Test
    public void t1() {
        System.out.println("TEST...t1.......");
    }

    @Test
    public void t3() {
        System.out.println("TEST...t3.......");
    }

    @Test
    public void t2() {
        System.out.println("TEST...t2.......");
    }
}
