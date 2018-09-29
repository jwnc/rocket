package rule;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.TemporaryFolder;
import org.junit.runners.MethodSorters;

import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JRule {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void sysinit2() {
        System.out.println("BeforeClass...sysinit2.......");
    }

    @Before
    public void b1() {
        System.out.println("Before...b1.......");
    }

    @After
    public void a2() {
        System.out.println("After...a2.......");
    }

    @Test
    public void testTempFolderRule() throws IOException {
        //在系统的临时目录下创建文件或者目录，当测试方法执行完毕自动删除
        tempFolder.newFile("test.txt");
        tempFolder.newFolder("test");
        System.out.println("testTempFolderRule");
    }

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void testErrorCollector() {
        System.out.println("testErrorCollector");

        errorCollector.addError(new Exception("Test Fail 1"));
        errorCollector.addError(new Throwable("fff"));
    }

    @Test(expected = ArithmeticException.class)
    public void nu() {
        System.out.println("异常捕获测试..");
        System.out.println(1 / 0);
    }
}
