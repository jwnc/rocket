import com.wnc.basic.BasicFileUtil;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author nengcai.wang
 * @Description 基于源文件来动态加载class, 只有单个文件
 * @date 2018/10/9
 */
public class App2 {
    static String targetDir = "C:\\projects\\ucar\\rocket\\jvm\\classes\\test\\";

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\projects\\ucar\\rocket\\jvm\\src\\test\\java";
        String sourceDir = "C:\\projects\\ucar\\rocket\\jvm\\src\\test\\java";
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        boolean compilerResult = Tools.compiler(filePath, sourceDir, targetDir, diagnostics);

        if (compilerResult) {
            System.out.println("编译成功");
        } else {
            System.out.println("编译失败");
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                System.out.println(diagnostic.getMessage(null));
            }
        }

        String classPath = targetDir + "pkg1\\DigitOrderRule4Pkg.class";
        invoke(classPath);
    }

    private static void invoke(String classPath) {
        ClassLoader classloader = new MyClassLoader();

        Class<?> clazz = null;
        try {
            clazz = classloader.loadClass(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Method method = null;
        try {
            method = clazz.getMethod("rename", File.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(clazz.newInstance(), new File("D:\\downloads\\02-2 常见的中间件服务.wmv"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static class MyClassLoader extends ClassLoader {

        @Override
        protected Class<?> findClass(String path) throws ClassNotFoundException {
            // 先尝试自己加载
            String className = path.replace(targetDir, "").replaceAll("\\\\", ".").replaceAll(".class", "");
            if (new File(path).exists()) {
                byte[] bytes = BasicFileUtil.readFileByte(path);
                return defineClass(className, bytes, 0, bytes.length);
            }
            // 再交给系统加载
            try {
                return ClassLoader.getSystemClassLoader().loadClass(className);
            } catch (Exception e) {
                return super.findClass(className);
            }
        }
    }

}