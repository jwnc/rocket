import func.BackupRule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MyTest {
    @Test
    public void a() throws IOException {
        new BackupRule().rename(new File("D:\\downloads\\2-2 常见的中间件服务.wmv"));
    }

    @Test
    public void a2() throws IOException {
//        new DigitOrderRule()rename(new File("D:\\downloads\\2-2 常见的中间件服务.wmv"));
    }
}
