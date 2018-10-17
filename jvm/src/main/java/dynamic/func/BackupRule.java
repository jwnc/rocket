package dynamic.func;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class BackupRule implements Rule {
    @Override
    public File rename(File file) throws IOException {
        File file1 = new File(file.getAbsolutePath() + ".bak");
        FileUtils.moveFile(file, file1);
        return file1;
    }
}
