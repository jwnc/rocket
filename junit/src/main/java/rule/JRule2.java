package rule;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runners.MethodSorters;

import java.io.IOException;

/**
 * Rule是方法级别,每个方法都要执行一遍
 * Rule 既可加在字段上, 也可加载方法上(此时, 需要返回TestRule子类型的对象)
 *
 * @author nengcai.wang
 * @version: 1.0
 * @since 2018/9/25 10:21
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JRule2 {
//    @Rule
//    public TemporaryFolder tempFolder = new TemporaryFolder();

    public TemporaryFolder tempFolder;

    @Rule
    public TemporaryFolder g() {
        tempFolder = new TemporaryFolder();
        return tempFolder;
    }

    @Test
    public void testTempFolderRule1() throws IOException {
        //在系统的临时目录下创建文件或者目录，当测试方法执行完毕自动删除
        tempFolder.newFile("test.txt");
        tempFolder.newFolder("test");
        System.out.println("testTempFolderRule1:" + tempFolder.getRoot());
    }

    @Test
    public void testTempFolderRule2() throws IOException {
        //在系统的临时目录下创建文件或者目录，当测试方法执行完毕自动删除
        tempFolder.newFile("test.txt");
        tempFolder.newFolder("test");
        System.out.println("testTempFolderRule2:" + tempFolder.getRoot());
    }

}
