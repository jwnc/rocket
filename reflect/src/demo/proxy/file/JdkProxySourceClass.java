package demo.proxy.file;

import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class JdkProxySourceClass {

    public static void writeClassToDisk(String path) {

        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy4", new Class[]{PersonInter.class});
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(classFile);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testGenerateProxyClass() {
        JdkProxySourceClass.writeClassToDisk("reflect/target/classes/$Proxy4.class");
    }
}
