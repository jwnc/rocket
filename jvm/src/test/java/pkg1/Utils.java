package pkg1;

import java.util.Date;

public final class Utils {
    public static long getCurrentMillisTime() {
        return new Date().getTime();
    }
}
