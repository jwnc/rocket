package pkg1;

import func.Rule;

import java.io.File;
import java.io.IOException;

public class DigitOrderRule4Pkg implements Rule {

    public File rename(File file) throws IOException {
        System.out.println("DigitOrderRule4Pkg:" + file.getName());
        return file;
    }
}
