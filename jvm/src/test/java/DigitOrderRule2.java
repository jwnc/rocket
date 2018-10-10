import func.Rule;
import org.apache.commons.io.FileUtils;
import pkg1.Utils;

import java.io.File;
import java.io.IOException;

public class DigitOrderRule2 implements Rule {

    public File rename(File file) throws IOException {
        String absolutePath = file.getAbsolutePath();
        // 零宽断言并不计入分组$
        String s = absolutePath.replaceFirst("(?<=.+\\\\)(\\d)(?=[^\\d])", "0$1").replaceFirst("(?<=.+\\\\\\d{2}\\-)(\\d)(?=[^\\d])", "0$1");
        File file1 = new File(s);
        if (!absolutePath.equals(s)) {
            FileUtils.moveFile(file, file1);
        }
        System.out.println("DigitOrderRule done:" + Utils.getCurrentMillisTime());
        return file1;
    }
}
