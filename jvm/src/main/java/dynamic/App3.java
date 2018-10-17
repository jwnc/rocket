package dynamic;

import utils.Tools;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * @author nengcai.wang
 * @Description 基于源文件来动态加载class, 并且外部源代码之间有相互依赖时,需要手动全部加载这些类
 * @date 2018/10/9
 */
public class App3 {
    static String filePath = "C:\\projects\\ucar\\rocket\\jvm\\src\\main\\resources\\dynamic";
    static String sourceDir = "C:\\projects\\ucar\\rocket\\jvm\\src\\main\\resources\\dynamic";
    static String targetDir = "C:\\projects\\ucar\\rocket\\jvm\\classes\\test\\";

    public static void main(String[] args) throws Exception {
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

        String classPath = targetDir + "DigitOrderRule2.class";
        invoke(classPath);
    }


    private static void invoke(String classPath) throws Exception {
        Map<String, Class> map = new HashMap<String, Class>();

        loadAll(map);

        Class clazz = map.get(classPath);
        Method method = null;
        try {
            method = clazz.getMethod("rename", File.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(clazz.newInstance(), new File("02-2 第2个视频文件.wmv"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void loadAll(Map<String, Class> map) throws Exception {
        List<File> clazzFileList = new ArrayList<File>();
        Tools.getSourceFiles(new File(targetDir), clazzFileList, ".class");

        URL url = new File(targetDir).toURI().toURL();
        URL[] urls = new URL[]{url};
        URLClassLoader classLoader = new URLClassLoader(urls, getSystemClassLoader());

        for (File f : clazzFileList) {
            Class<?> clazz = null;
            String name = f.getAbsolutePath().replace(targetDir, "").replaceAll("\\\\", ".").replaceAll(".class", "");
            clazz = classLoader.loadClass(name);
            map.put(f.getAbsolutePath(), clazz);
        }
    }

}