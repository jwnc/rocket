package custom;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * 运行时间计算
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/27 11:44
 */
public class BaseRunTimeTest {
    @Rule
    public RunTimeRule timeCount = new RunTimeRule();

    @ClassRule
    public static RunTimeRule timeCount2 = new RunTimeRule();

}
