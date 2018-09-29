package rule;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * ClassRule注解是类级别的, 必须用static限定, ClassRule比BeforeClass优先级更高
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/25 10:30
 */
public class ClassRuleTest {
    @ClassRule
    public static TemporaryFolder tempFolder = new TemporaryFolder();

    @BeforeClass
    public static void bc() {
        System.out.println(tempFolder.getRoot());
        System.out.println("Before class...");
    }

    @Test
    public void a() {
        System.out.println("Test a.");
        System.out.println(tempFolder.getRoot());
    }

    @Test
    public void b() {
        System.out.println("Test b.");
        System.out.println(tempFolder.getRoot());
    }
}
