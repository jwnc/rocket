package result;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.ArrayList;
import java.util.List;

public class ExecutionListener extends RunListener {
    MyResultRecorder recorder;
    MethodInfo methodInfo;
    List<MethodInfo> list;

    public ExecutionListener() {
        this.list = new ArrayList<MethodInfo>();
    }

    public void testRunStarted(Description description) throws Exception {
        System.out.println("--------- START ----------");
        recorder = new MyResultRecorder();
    }

    public void testRunFinished(Result result) throws Exception {
        recorder.setResult(result.wasSuccessful());
        recorder.setList(list);
        System.out.println("--------- END ----------");
        System.out.println("执行结果 : " + result.wasSuccessful());
        System.out.println("执行时间 : " + result.getRunTime());
        System.out.println("执行数量 : " + result.getRunCount());
        System.out.println("失败数量 : " + result.getFailureCount());
        System.out.println("忽略数量 : " + result.getIgnoreCount());
    }

    public void testStarted(Description description) throws Exception {
        recorder.setScript_name(description.getClassName());
        System.out.println(description.getMethodName() + " begin");
        methodInfo = new MethodInfo();
        methodInfo.setMethod_name(description.getMethodName());
    }

    public void testFinished(Description description) throws Exception {
        System.out.println(description.getMethodName() + " end");
        if (methodInfo.getError_msg() == null)
            methodInfo.setResult(true);
        list.add(methodInfo);
    }

    public void testFailure(Failure failure) throws Exception {
        System.out.println("Execution of test case failed : " + failure.getMessage());
        methodInfo.setResult(false);
        methodInfo.setError_msg(failure.getMessage());
    }

    public void testIgnored(Description description) throws Exception {

    }
}