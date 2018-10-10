package result;

import org.junit.runner.JUnitCore;

/**
 * 自定义Result类记录执行结果
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/10/10 18:09
 */
public class Execute {

    public static void main(String[] args) {
        run(JunitDemo.class, JunitDemo2.class);
    }

    private static void run(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            JUnitCore runner = new JUnitCore();
            ExecutionListener listener = new ExecutionListener();
            runner.addListener(listener);
            runner.run(clazz);
            MyResultRecorder recorder = listener.recorder;
            System.out.println(recorder);
        }
    }
}