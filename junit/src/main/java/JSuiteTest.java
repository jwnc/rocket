import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
//配置所有需要执行的测试
@SuiteClasses({
        JTest.class,
        JTimeOutRule.class
})
public class JSuiteTest {

}
