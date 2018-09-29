package rule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

/**
 * MethodRule接口只有一个实现类(TestWatchnan),该实现类的主要方法还是空的
 * 新版本已经废弃MethodRule
 * Note that {@link MethodRule} has been replaced by {@link TestRule},
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/25 10:30
 */
public class MethodRuleTest {
    @Rule
    public TestWatchman testWatchman = new TestWatchman() {
        @Override
        public void starting(FrameworkMethod method) {
            super.starting(method);
            System.out.println(method.getName() + " start...");
        }
    };

    @Test
    public void a() {
        System.out.println("Test a.");
    }
}
