package custom;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * 运行时间计算
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/27 11:44
 */
public class RunTimeRule implements TestRule {
    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            public void evaluate() throws Throwable {
                long t = System.currentTimeMillis();
                try {
                    base.evaluate();
                } finally {
                    System.out.println((description.isSuite() ? "测试类" : "测试方法") + description + "耗时(ms):" + humanReadable(System.currentTimeMillis() - t));
                }
            }
        };
    }

    private String humanReadable(long time) {
        long ms = time % 1000;
        long s = time / 1000 % 60;
        long m = time / 1000 / 60 % 60;
        long h = time / 1000 / 60 / 60 % 24;
        long d = time / 1000 / 60 / 60 / 24;
        return new TimeStrBuilder().build(d, "天").build(h, "小时").build(m, "分钟")
                .build(s, "秒").build(ms, "毫秒").toString();
    }

    public static class TimeStrBuilder {
        private StringBuilder output = new StringBuilder("");

        public TimeStrBuilder build(long i, String s) {
            if (i > 0 || toString().length() > 0) {
                this.output.append(i + s);
            }
            return this;
        }

        public String toString() {
            return output.toString();
        }
    }
}
