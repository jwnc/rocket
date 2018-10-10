package func;

import java.io.File;
import java.io.IOException;

public interface Rule {
    public File rename(File file) throws IOException;
}
